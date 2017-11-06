package Model.Parent;

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
import edu.plan9.naseemdev.naseem.Parent;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Parent_Test_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 8/2/2017.
 */

public class Parent_Paginater {
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView recyclerView;
    private Parent_Adapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int page_number;
    private boolean connected;
    private Session session;
    private Parent parent;

    public Parent_Paginater(Context c, PullToLoadView pull, Parent p, boolean conn){
        context =  c;
        pullToLoadView = pull;
        session = new Session(context);
        parent = p;
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        adapter = new Parent_Adapter(context, new ArrayList<Parent_Test_BO>(), recyclerView, parent);
        recyclerView.setAdapter(adapter);
        connected = conn;
        page_number = 1;
        if(connected)
            initialize_paginater();
    }
    public void initialize_paginater(){
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                load_data(page_number);
            }

            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                page_number = 1;
                load_data(page_number);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }

    public void load_data(int page){
        isLoading = true;
        String URL = Constants.URL_Get_Test + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        isLoading = false;
                        if(s.equals("-1")){
                            session.destroy_session();
                            parent.unAuthorized();
                            return;
                        }
                        else if(s.equals("-2")){
                            Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            parent.hide();
                            return;
                        }
                        else if (s.equals("-3")){
                            parent.parent_error();
                        }
                        else{
                            try{
                                JsonParsor jsonParsor = new JsonParsor();
                                ArrayList<Parent_Test_BO> pp = jsonParsor.parse_Parent_Test(s);
                                if(pp != null) {
                                    for (int i = 0; i < pp.size(); i++) {
                                        adapter.addTest(pp.get(i));
                                    }
                                    pullToLoadView.setComplete();
                                    parent.show();
                                    page_number = page_number + 1;
                                }else {
                                    Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    parent.hide();
                                    return;
                                }

                            }catch (Exception e){
                                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                parent.hide();
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
                parent.hide();
                return;
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put(Constants.User_Key_Page,String.valueOf(page_number));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
