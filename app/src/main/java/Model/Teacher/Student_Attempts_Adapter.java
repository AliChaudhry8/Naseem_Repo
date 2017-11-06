package Model.Teacher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.ShowTest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Assign_Selected;
import BussinessObjects.Test_BO;
import BussinessObjects.User_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by DELL on 10/4/2017.
 */

public class Student_Attempts_Adapter extends RecyclerView.Adapter<Model.Teacher.Student_Attempts_Adapter.MyViewHolder1> {
    private Context context;
    private Assign_Selected users;
    private RecyclerView recyclerView;
    private Activity activity;
    Button save_students;
    ProgressDialog progressDialog;
    Session session;
    String test_id;
    JSONArray selected ;
    JSONObject jObject;

    public Student_Attempts_Adapter(Context c, Assign_Selected u, RecyclerView rv, Activity a , String id){
        context = c;
        recyclerView = rv;
        users = u;
        activity = a;
        test_id = id;
        session = new Session(c);
        selected  = new JSONArray();
        jObject = new JSONObject();
    }

    @Override
    public Model.Teacher.Student_Attempts_Adapter.MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_attempts, parent, false);
        View g = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assign__students__group , parent , false);
        final Model.Teacher.Student_Attempts_Adapter.MyViewHolder1 myViewHolder = new Model.Teacher.Student_Attempts_Adapter.MyViewHolder1(v);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);

                User_BO u = users.getUsers().get(position);

            }
        });

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final Model.Teacher.Student_Attempts_Adapter.MyViewHolder1 holder,final int position) {
        final User_BO u = users.getUsers().get(position);
        holder.firstname.setText(u.getFirst_name());
        holder.secondname.setText(u.getSecond_name());
        holder.username.setText(u.getUsername());
        holder.attempts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getApplicationContext() , "Hello asdaff" , Toast.LENGTH_LONG).show();

                getTest(u.getId());
            }
        });
        //holder.attempts.setText(""+u.getAttempts());
      /*  holder.rdbtn.setOnCheckedChangeListener(null);
        holder.rdbtn.setSelected(u.is_selected());
        holder.rdbtn.setChecked(u.is_selected());
        holder.rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   // int position = holder.getPosition();
                    if (isChecked) {
                        users.getUsers().get(position).setIs_selected(false);
                        holder.rdbtn.setChecked(false);
                        notifyDataSetChanged();
                    } else {
                        users.getUsers().get(position).setIs_selected(true);
                        holder.rdbtn.setChecked(true);
                        notifyDataSetChanged();
                    }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return users.getUsers().size();
    }

    public void addUser(User_BO u ){
        // flag_selected = flag;
        users.getUsers().add(u);
        notifyDataSetChanged();
    }

    public void clear(){
        users.getUsers().clear();
        notifyDataSetChanged();
    }
    class MyViewHolder1 extends RecyclerView.ViewHolder{
        public TextView firstname;
        public TextView secondname;
        public TextView username;
        public Button attempts;

        public MyViewHolder1(View item){
            super(item);
            firstname = (TextView)item.findViewById(R.id.first_name);
            secondname = (TextView) item.findViewById(R.id.second_name);
            username = (TextView) item.findViewById(R.id.user_name);
            attempts = (Button) item.findViewById(R.id.student_attempt);
            //final int position  = MyViewHolder1.super.getAdapterPosition();
        }
    }


    public void getTest(final int id)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Teacher_Single_Test + session.getAuthenticationToken(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        try
                        {
                            if(s.toString().equals("-2")) {
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            else if(s.toString().equals("-1")) {
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_USER_Login_Failed, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }

                            JsonParsor jsonParsor = new JsonParsor();
                            Test_BO u = jsonParsor.parse_Test(s);

                            Intent it = new Intent(activity, ShowTest.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("student_id" , ""+id);
                            bundle.putSerializable("test", u);
                            it.putExtras(bundle);
                            activity.startActivity(it);

                        }catch (Exception e)
                        {
                            Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",""+session.getUserId());
                map.put("testid", test_id);
                map.put("student_id" ,""+id );
                return map;
            }
        };

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Authenticating);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }


}


