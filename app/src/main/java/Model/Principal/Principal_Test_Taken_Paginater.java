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
import com.plan9.naseemdev.naseem.Principal_Test;
import com.plan9.naseemdev.naseem.Principal_Test_Taken;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Principal_Test_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 8/3/2017.
 */

public class Principal_Test_Taken_Paginater {
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView recyclerView;
    private Principal_Test_Adapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int page_number;
    private Session session;
    private Principal_Test_Taken test_taken;
    private boolean connected;

    public Principal_Test_Taken_Paginater(Context c, PullToLoadView loadView, Principal_Test_Taken p, boolean con){
        context = c;
        pullToLoadView = loadView;
        session = new Session(context);
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        adapter = new Principal_Test_Adapter(context, new ArrayList<Principal_Test_BO>(), recyclerView);
        recyclerView.setAdapter(adapter);
        page_number = 1;
        test_taken = p;
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


    public void load_data(final int page){
        isLoading = true;
        test_taken.show();
        String URL = Constants.URL_Principal_Get_Test_Taken + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        isLoading = false;
                        if(s.equals("-1")){
                            session.destroy_session();
                            test_taken.unAuthorized();
                            return;
                        }
                        else if (s.equals("-2")){
                            test_taken.hide();
                            Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else {
                            JsonParsor jsonParsor = new JsonParsor();
                            ArrayList<Principal_Test_BO> tests = jsonParsor.parse_Principal_Test(s);
                            if(tests != null){
                                for (int i=0; i<tests.size(); i++){
                                    adapter.addTest(tests.get(i));
                                }
                                pullToLoadView.setComplete();
                                page_number = page_number + 1;
                                test_taken.show();
                            }
                            else {
                                test_taken.hide();
                                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                isLoading = false;
                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error + volleyError.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                test_taken.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put(Constants.User_Key_Page,String.valueOf(page));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
