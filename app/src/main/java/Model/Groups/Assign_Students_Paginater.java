package Model.Groups;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import edu.plan9.naseemdev.naseem.Assign_Students_Group;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Assign_Selected;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by DELL on 9/22/2017.
 */

public class Assign_Students_Paginater implements View.OnClickListener{
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView recyclerView;
    private Assign_Students_Adapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int page_number, total;
    private boolean connected;
    private Session session;
    private Assign_Students_Group group;
    String group_id ;
    Assign_Selected as;

    public Assign_Students_Paginater(Context c, PullToLoadView p, Assign_Students_Group ps, boolean con , String id){
        context = c;
        pullToLoadView = p;
        session = new Session(c);
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        as = new Assign_Selected();
        group_id = id;
        adapter = new Assign_Students_Adapter(context,as, recyclerView, ps , id);
        recyclerView.setAdapter(adapter);
        page_number = 1;
        total = 1;
        group = ps;
        connected = con;
        if(connected)
            initialize_paginater(group_id);
    }

    public void initialize_paginater(final String id){
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                load_data(page_number , id);
            }
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                page_number = 1;
                load_data(page_number , id);
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

    public void load_data(final int page ,final String groupId) {
        isLoading = true;
        group.show();
        String URL = Constants.URL_Teacher_Groups_Assign + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        isLoading = false;
                        if(s.equals("-1")){
                            session.destroy_session();
                            group.unAuthorized();
                            return;
                        }
                        else if(s.equals("-2")){
                            Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            group.hide();
                            return;
                        }
                        else {
                            try{
                                JSONObject js = new JSONObject(s);
                                total = js.getInt("total");
                                JsonParsor jsonParsor = new JsonParsor();
                                Assign_Selected as = jsonParsor.parse_Users(s);

                              //  Toast.makeText(context , "hello"+as.getSelected().get(1) , Toast.LENGTH_LONG).show();
                                if(as.getSelected().size() == 0){
                                    for(int i = 0; i < as.getUsers().size() ; i++){
                                        adapter.addUser(as.getUsers().get(i));
                                    }
                                }
                                else {
                                    for (int i = 0; i < as.getUsers().size(); i++) {
                                        if (as.getSelected().contains(String.valueOf(as.getUsers().get(i).getId()))) {
                                            as.getUsers().get(i).setIs_selected(true);
                                            adapter.addUser(as.getUsers().get(i));
                                        } else {
                                            as.getUsers().get(i).setIs_selected(false);
                                            adapter.addUser(as.getUsers().get(i));
                                        }
                                    }
                                }
                                pullToLoadView.setComplete();
                                group.show();
                                page_number = page_number + 1;
                            }catch (Exception e){
                                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                group.hide();
                                return;
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                isLoading = false;
                Toast toast = Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                group.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put(Constants.User_Key_Page,String.valueOf(page_number));
                map.put("group_id",groupId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
      //  group.
    }
}

