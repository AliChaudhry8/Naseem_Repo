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
import com.plan9.naseemdev.naseem.TeacherStudentTestList;
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
 * Created by Muhammad Taimoor on 8/10/2017.
 */

public class Principal_Student_Teacher_Test_List_Paginator {
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView recyclerView;
    private Principal_Student_Teacher_Test_List_Adapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int page_number;
    private Session session;
    private TeacherStudentTestList teacherStudentTest;
    private boolean connected;
    private String role;
    private int id;
    public Principal_Student_Teacher_Test_List_Paginator(Context c, PullToLoadView loadView, TeacherStudentTestList list, boolean con, String r, int i) {
        teacherStudentTest = list;
        context = c;
        pullToLoadView = loadView;
        session = new Session(context);
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        role = r;
        id = i;
        adapter = new Principal_Student_Teacher_Test_List_Adapter(context, new ArrayList<Principal_Test_BO>(), recyclerView, role);
        recyclerView.setAdapter(adapter);
        page_number = 1;
        teacherStudentTest = list;
        connected = con;
        Toast.makeText(context, "Role in Paginator: " + role, Toast.LENGTH_LONG).show();
        if(connected)
            initialize_paginater();
    }

    public void initialize_paginater() {
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                if(role.equals("Student"))
                    load_data_student(page_number);
                else if (role.equals("Teacher"))
                    load_data_teacher(page_number);
            }
            @Override
            public void onRefresh() {
                adapter.clearTest();
                hasLoadedAll = false;
                page_number = 1;
                if(role.equals("Student"))
                    load_data_student(page_number);
                else if (role.equals("Teacher"))
                    load_data_teacher(page_number);
            }
            @Override
            public boolean isLoading() {return isLoading;}
            @Override
            public boolean hasLoadedAllItems() {return hasLoadedAll;}
        });
        pullToLoadView.initLoad();
    }

    public void load_data_student(final int page){
        isLoading = true;
        teacherStudentTest.show();
        String URL = Constants.URL_Principal_Get_Students_Test_List + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        isLoading = false;
                        if(s.equals("-1")){
                            session.destroy_session();
                            teacherStudentTest.unAuthorized();
                            return;
                        }
                        else if (s.equals("-2")){
                            teacherStudentTest.hide();
                            Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else {
                            JsonParsor jsonParsor = new JsonParsor();
                            ArrayList<Principal_Test_BO> tests = jsonParsor.parse_Principal_Student_Test_List(s);
                            if(tests != null){
                                for (int i=0; i<tests.size(); i++){
                                    adapter.addTest(tests.get(i));
                                }
                                pullToLoadView.setComplete();
                                page_number = page_number + 1;
                                teacherStudentTest.show();
                            }
                            else {
                                teacherStudentTest.hide();
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
                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                teacherStudentTest.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put(Constants.Principal_Key_Student_Id,String.valueOf(id));
                map.put(Constants.User_Key_Page,String.valueOf(page));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void load_data_teacher(final int page){
        isLoading = true;
        teacherStudentTest.show();
        String URL = Constants.URL_Principal_Get_Teacher_Test_List + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        isLoading = false;
                        if(s.equals("-1")){
                            session.destroy_session();
                            teacherStudentTest.unAuthorized();
                            return;
                        }
                        else if (s.equals("-2")){
                            teacherStudentTest.hide();
                            Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else {
                            JsonParsor jsonParsor = new JsonParsor();
                            ArrayList<Principal_Test_BO> tests = jsonParsor.parse_Principal_Teacher_Test_List(s);
                            if(tests != null){
                                for (int i=0; i<tests.size(); i++){
                                    adapter.addTest(tests.get(i));
                                }
                                pullToLoadView.setComplete();
                                page_number = page_number + 1;
                                teacherStudentTest.show();
                            }
                            else {
                                teacherStudentTest.hide();
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
                Toast toast = Toast.makeText(context, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                teacherStudentTest.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put(Constants.Principal_Key_Teacher_Id,String.valueOf(id));
                map.put(Constants.User_Key_Page,String.valueOf(page));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
