package edu.plan9.naseemdev.naseem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Test_BO;
import BussinessObjects.Test_Taken_Schedule;
import Model.Constants;
import Model.Student.Custom_Show_Schedule_Test_Adapter;
import Model.Student.Custom_Show_Taken_Test_Adapter;
import Model.JsonParsor;
import Model.ListUtils;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 5/16/2017.
 */

public class Test extends Fragment {
    private ListView testtaken, testschedule;
    private RelativeLayout loadingtesttaken, loadingtestschedule;
    private String URL;
    private int id;
    private TextView notaken, noschedule, test_taken_prompt, test_schedule_prompt;
    private Test_Taken_Schedule test_taken_schedule;
    private LinearLayout reloadLayout;
    private ImageView reload;
    private Session session;

    public Test(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        session = new Session(getActivity().getApplicationContext());
        testtaken = (ListView)rootView.findViewById(R.id.testtakenlist);
        testtaken.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getActivity(), ShowTest.class);
                Bundle bundle = new Bundle();
                Test_BO test = test_taken_schedule.getTest_taken().get(i);
                bundle.putSerializable("test", test);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        testschedule = (ListView)rootView.findViewById(R.id.testschedulelist);
        testschedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Test_BO test = test_taken_schedule.getTest_schedule().get(i);

                if(test.getStart_time().equals("") || test.getStart_time().equals("null") || test.getStart_time().equals(null)) {
                    Intent it = new Intent(getActivity(), TakeTest.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("test", test);
                    it.putExtras(bundle);
                    startActivity(it);
                    getActivity().finish();
                }
                else{
                    String []startDateTime = test.getStart_time().split("T");
                    String[] testdate = startDateTime[0].split("-");
                    String test_year = testdate[0];
                    int test_month = Integer.parseInt(testdate[1]);
                    int test_day = Integer.parseInt(testdate[2]);
                    String [] testTime = startDateTime[1].split(":");
                    int testhour = Integer.parseInt(testTime[0]);
                    int testminute = Integer.parseInt(testTime[1]);


                    String t_Month, t_Day, t_Hour, t_Minute;
                    if(test_month>0 && test_month<10)
                        t_Month = "0" + test_month;
                    else
                        t_Month = "" + test_month;
                    if(test_day>0 && test_day<10)
                        t_Day = "0" + test_day;
                    else
                        t_Day = "" + test_day;
                    if(testhour>0 && testhour<10)
                        t_Hour = "0" + testhour;
                    else
                        t_Hour = "" + testhour;
                    if(testminute>0 && testminute<10)
                        t_Minute = "0" + testminute;
                    else
                        t_Minute = "" + testminute;

                    String str = test_year+t_Month+t_Day+t_Hour+t_Minute;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                    try {
                        Date d = sdf.parse(str);
                        if (new Date().after(d) || new Date().equals(d) ) {
                            Intent it = new Intent(getActivity(), TakeTest.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("test", test);
                            it.putExtras(bundle);
                            startActivity(it);
                            getActivity().finish();
                        }
                        else{
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Start_Test, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    } catch (ParseException ex) {}
                }
            }
        });
        loadingtesttaken = (RelativeLayout)rootView.findViewById(R.id.loadingtakentest);
        loadingtestschedule = (RelativeLayout)rootView.findViewById(R.id.loadingtestschedule);
        notaken = (TextView)rootView.findViewById(R.id.notesttaken);
        noschedule = (TextView)rootView.findViewById(R.id.notestschedule);
        test_schedule_prompt = (TextView)rootView.findViewById(R.id.test_schedule_prompt);
        test_taken_prompt = (TextView)rootView.findViewById(R.id.test_taken_prompt);
        reloadLayout = (LinearLayout)rootView.findViewById(R.id.reload_Test_Layout);
        reload = (ImageView)rootView.findViewById(R.id.reload_test);
        reloadLayout.setVisibility(View.GONE);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingtesttaken.setVisibility(View.VISIBLE);
                loadingtestschedule.setVisibility(View.VISIBLE);
                noschedule.setVisibility(View.GONE);
                notaken.setVisibility(View.GONE);
                testtaken.setVisibility(View.GONE);
                testschedule.setVisibility(View.GONE);
                test_schedule_prompt.setVisibility(View.VISIBLE);
                test_taken_prompt.setVisibility(View.VISIBLE);
                reloadLayout.setVisibility(View.GONE);
                loadTest();
            }
        });
        noschedule.setVisibility(View.GONE);
        notaken.setVisibility(View.GONE);
        reloadLayout.setVisibility(View.GONE);
        loadTest();
        return rootView;
    }


    public void loadTest(){
        URL = Constants.URL_Get_Test + session.getAuthenticationToken();
        id = session.getUserId();
        if (session.getAuthenticationToken().equals("") || session.getAuthenticationToken().equals(null) || session.getAuthenticationToken().equals("null")) {
            loadingtesttaken.setVisibility(View.GONE);
            loadingtestschedule.setVisibility(View.GONE);
            noschedule.setVisibility(View.GONE);
            notaken.setVisibility(View.GONE);
            testtaken.setVisibility(View.GONE);
            testschedule.setVisibility(View.GONE);
            test_schedule_prompt.setVisibility(View.GONE);
            test_taken_prompt.setVisibility(View.GONE);
            reloadLayout.setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent it = new Intent(getActivity(), SignUpSignIn.class);
            startActivity(it);
            getActivity().finish();
        }
        else {
            if(!isConnected()) {
                loadingtesttaken.setVisibility(View.GONE);
                loadingtestschedule.setVisibility(View.GONE);
                noschedule.setVisibility(View.GONE);
                notaken.setVisibility(View.GONE);
                testtaken.setVisibility(View.GONE);
                testschedule.setVisibility(View.GONE);
                test_schedule_prompt.setVisibility(View.GONE);
                test_taken_prompt.setVisibility(View.GONE);
                reloadLayout.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }else {
                test_schedule_prompt.setVisibility(View.VISIBLE);
                test_taken_prompt.setVisibility(View.VISIBLE);
                loadingtestschedule.setVisibility(View.VISIBLE);
                loadingtesttaken.setVisibility(View.VISIBLE);
                reloadLayout.setVisibility(View.GONE);
                testtaken.setVisibility(View.GONE);
                testschedule.setVisibility(View.GONE);
                noschedule.setVisibility(View.GONE);
                notaken.setVisibility(View.GONE);
                getAllTest();
            }
        }
    }

    public void getAllTest(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            reloadLayout.setVisibility(View.GONE);
                            if(s.equals("-1")) {
                                session.destroy_session();
                                Intent it = new Intent(getActivity(), SignUpSignIn.class);
                                startActivity(it);
                                getActivity().finish();
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            if(s.equals("-2")) {
                                loadingtesttaken.setVisibility(View.GONE);
                                loadingtestschedule.setVisibility(View.GONE);
                                noschedule.setVisibility(View.GONE);
                                notaken.setVisibility(View.GONE);
                                testtaken.setVisibility(View.VISIBLE);
                                testschedule.setVisibility(View.VISIBLE);
                                test_schedule_prompt.setVisibility(View.VISIBLE);
                                test_taken_prompt.setVisibility(View.VISIBLE);
                                reloadLayout.setVisibility(View.GONE);
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            else {
                                JsonParsor jsonParsor = new JsonParsor();
                                test_taken_schedule = jsonParsor.parseTest(s.toString());
                                reloadLayout.setVisibility(View.GONE);
                                test_schedule_prompt.setVisibility(View.VISIBLE);
                                test_taken_prompt.setVisibility(View.VISIBLE);
                                if(test_taken_schedule.getTest_schedule().size() > 0) {
                                        Custom_Show_Schedule_Test_Adapter adapter = new Custom_Show_Schedule_Test_Adapter(getActivity().getApplicationContext(), test_taken_schedule.getTest_schedule());
                                        testschedule.setAdapter(adapter);
                                        loadingtestschedule.setVisibility(View.GONE);
                                        testschedule.setVisibility(View.VISIBLE);
                                        noschedule.setVisibility(View.GONE);
                                        ListUtils.setDynamicHeight(testschedule);
                                }
                                else {
                                        loadingtestschedule.setVisibility(View.GONE);
                                        testschedule.setVisibility(View.GONE);
                                        noschedule.setVisibility(View.VISIBLE);
                                }
                                if(test_taken_schedule.getTest_taken().size() > 0) {
                                        Custom_Show_Taken_Test_Adapter adapter = new Custom_Show_Taken_Test_Adapter(getActivity().getApplicationContext(), test_taken_schedule.getTest_taken());
                                        testtaken.setAdapter(adapter);
                                        loadingtesttaken.setVisibility(View.GONE);
                                        testtaken.setVisibility(View.VISIBLE);
                                        notaken.setVisibility(View.GONE);
                                        ListUtils.setDynamicHeight(testtaken);
                                }
                                else {
                                        loadingtesttaken.setVisibility(View.GONE);
                                        testtaken.setVisibility(View.GONE);
                                        notaken.setVisibility(View.VISIBLE);
                                }
                            }
                        }catch (Exception e) {
                            loadingtesttaken.setVisibility(View.GONE);
                            loadingtestschedule.setVisibility(View.GONE);
                            noschedule.setVisibility(View.GONE);
                            notaken.setVisibility(View.GONE);
                            testtaken.setVisibility(View.GONE);
                            testschedule.setVisibility(View.GONE);
                            test_schedule_prompt.setVisibility(View.GONE);
                            test_taken_prompt.setVisibility(View.GONE);
                            reloadLayout.setVisibility(View.VISIBLE);
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                loadingtesttaken.setVisibility(View.GONE);
                loadingtestschedule.setVisibility(View.GONE);
                noschedule.setVisibility(View.GONE);
                notaken.setVisibility(View.GONE);
                testtaken.setVisibility(View.GONE);
                testschedule.setVisibility(View.GONE);
                test_schedule_prompt.setVisibility(View.GONE);
                test_taken_prompt.setVisibility(View.GONE);
                reloadLayout.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(id));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private boolean isConnected()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        else
            connected = false;
        return  connected;
    }
}
