package Model.Principal;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import edu.plan9.naseemdev.naseem.Principal_Teachers;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.User_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 7/27/2017.
 */

public class Principal_Teacher_Paginater {
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView recyclerView;
    private Principal_Student_Teacher_Adapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int page_number, total;
    private Session session;
    private Principal_Teachers teachers;
    private boolean connected;

    public Principal_Teacher_Paginater(Context c, PullToLoadView p, Principal_Teachers pt, boolean con){
        context = c;
        pullToLoadView = p;
        session = new Session(c);
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        adapter = new Principal_Student_Teacher_Adapter(context, new ArrayList<User_BO>(), recyclerView, pt.getActivity());
        recyclerView.setAdapter(adapter);
        page_number = 1;
        total = 1;
        teachers = pt;
        connected = con;
        if(connected)
            initialize_paginater();
    }

    public void initialize_paginater(){
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {load_data(page_number);}
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                page_number = 1;
                load_data(page_number);
            }
            @Override
            public boolean isLoading() {return isLoading;}
            @Override
            public boolean hasLoadedAllItems() {return hasLoadedAll;}
        });
        pullToLoadView.initLoad();
    }

    public void load_data(final int page) {
        isLoading = true;
        teachers.show();
        String URL = Constants.URL_Principal_Teachers + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                isLoading = false;

                if(s.equals("-1")){
                    session.destroy_session();
                    teachers.unAuthorized();
                    return;
                }
                else if(s.equals("-2")){
                    Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    teachers.hide();
                    return;
                }
                else {
                    try {
                        JSONObject js = new JSONObject(s);
                        total = js.getInt("total");
                        JsonParsor jsonParsor = new JsonParsor();
                        ArrayList<User_BO> u = jsonParsor.parseStudentsTeachers(s);
                        for(int i=0; i<u.size(); i++){
                            adapter.addUser(u.get(i));
                        }
                        pullToLoadView.setComplete();
                        teachers.show();
                        page_number = page_number + 1;
                    }
                    catch (Exception e) {
                        Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        teachers.hide();
                        return;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                isLoading = false;
                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                teachers.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put(Constants.User_Key_School_name,String.valueOf(session.getSchoolName()));
                map.put(Constants.User_Key_Page,String.valueOf(page_number));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
