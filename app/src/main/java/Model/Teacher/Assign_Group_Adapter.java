package Model.Teacher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import edu.plan9.naseemdev.naseem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Group_BO;
import BussinessObjects.Groups_Selected;
import Model.Constants;
import Model.Session;

/**
 * Created by DELL on 10/3/2017.
 */

public class Assign_Group_Adapter extends RecyclerView.Adapter<Model.Teacher.Assign_Group_Adapter.MyViewHolder1> {
    private Context context;
    private Groups_Selected users;
    private RecyclerView recyclerView;
    private Activity activity;
    Button save_students;
    ProgressDialog progressDialog;
    Session session;
    String test_id;
    JSONArray selected ;
    JSONObject jObject;

    public Assign_Group_Adapter(Context c, Groups_Selected u, RecyclerView rv, Activity a , String id){
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
    public Model.Teacher.Assign_Group_Adapter.MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assign_groups, parent, false);
        View g = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assign__students__group , parent , false);
        final Model.Teacher.Assign_Group_Adapter.MyViewHolder1 myViewHolder = new Model.Teacher.Assign_Group_Adapter.MyViewHolder1(v);

        save_students = (Button)activity.findViewById(R.id.save_students);
        save_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity.getApplicationContext() , "Heeeeeeeeeeelo" , Toast.LENGTH_LONG).show();
                for(int i = 0 ; i < users.getGroups().size() ; i++){
                    if(users.getGroups().get(i).is_selected()){
                        selected.put(String.valueOf(users.getGroups().get(i).getId()));
                    }

                }
                try {
                    jObject.put("selected", (Object) selected);
                    jObject.put("test_id" , (Object)test_id);
                    jObject.put("id" , (Object) session.getUserId());
                    selected_Students(jObject);
                }catch (JSONException e){
                    Toast.makeText(activity.getApplicationContext() , "Json Exception" , Toast.LENGTH_LONG).show();
                }

            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);

                Group_BO u = users.getGroups().get(position);

            }
        });

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final Model.Teacher.Assign_Group_Adapter.MyViewHolder1 holder,final int position) {
        Group_BO u = users.getGroups().get(position);
        holder.group_name.setText(users.getGroups().get(position).getName());
        holder.rdbtn.setChecked(u.is_selected());
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
        return users.getGroups().size();
    }

    public void addUser(Group_BO u ){
        // flag_selected = flag;
        users.getGroups().add(u);
        notifyDataSetChanged();
    }

    public void clear(){
        users.getGroups().clear();
        notifyDataSetChanged();
    }

    public void selected_Students(JSONObject array)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_Teacher_Assign_Group + session.getAuthenticationToken() , array,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("response").equals("-1")) {
                                Toast.makeText(activity.getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                return;
                            } else if (response.get("response").equals("-2")) {
                                Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                return;
                            } else if (response.get("response").equals("1")) {
                                Toast.makeText(activity.getApplicationContext(), "Successfully Assign Students to Group", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }catch (JSONException e){
                            Toast.makeText(activity.getApplicationContext() , Constants.Error_Unrecognized_Error , Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity.getApplicationContext() , Constants.Error_Unrecognized_Error , Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                //map.put("group_id",group_id);
                return map;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(activity);
        //requestQueue.add(jsonObjectRequest);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Registering);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(jsonObjectRequest);
    }




    class MyViewHolder1 extends RecyclerView.ViewHolder{
        public TextView group_name;
        public CheckBox rdbtn;

        public MyViewHolder1(View item){
            super(item);
            group_name = (TextView)item.findViewById(R.id.group_name);
            rdbtn = (CheckBox) item.findViewById(R.id.rd_btn_assign);

            //final int position  = MyViewHolder1.super.getAdapterPosition();
            rdbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rdbtn.isChecked()){
                        rdbtn.toggle();
                        if(users.getGroups().get(getAdapterPosition()).is_selected() == false){
                            users.getGroups().get(getAdapterPosition()).setIs_selected(true);
                        }
                        notifyDataSetChanged();
                    }
                    else{
                        rdbtn.toggle();
                        if(users.getGroups().get(getAdapterPosition()).is_selected()){
                            users.getGroups().get(getAdapterPosition()).setIs_selected(false);
                        }
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }


}


