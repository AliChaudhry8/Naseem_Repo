package Model.Groups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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
import edu.plan9.naseemdev.naseem.Assign_Students_Group;
import edu.plan9.naseemdev.naseem.New_Group;
import edu.plan9.naseemdev.naseem.R;

import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Group_BO;
import Model.Constants;
import Model.Session;

/**
 * Created by DELL on 9/20/2017.
 */

public class Custom_Dialog_Group extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Dialog dialog;
    private Button edit, assign , delete, cancel;
    private Group_BO group;
    private TextView name;
    Session session;
    ProgressDialog progressDialog;
    String group_id;

    public Custom_Dialog_Group(Activity a, Group_BO u){
        super(a);
        activity = a;
        group = u;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_group);
        edit = (Button)findViewById(R.id.dialog_edit);
        assign = (Button)findViewById(R.id.dialog_assign);
        delete = (Button)findViewById(R.id.dialog_delete);
        cancel = (Button)findViewById(R.id.dialog_cancel);
        edit.setOnClickListener(this);
        assign.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        name = (TextView)findViewById(R.id.name);
        name.setText(group.getName());

        session = new Session(activity);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_edit:{
                //Toast.makeText(getContext() , "Edit" , Toast.LENGTH_LONG).show();
                Intent it = new Intent(activity , New_Group.class) ;
                it.putExtra("Edit" , "1");
                it.putExtra("Group_name" , group.getName());
                it.putExtra("Group_id" , String.valueOf(group.getId()));
                //Toast.makeText(getContext(), "group_id: "+group.getId(), Toast.LENGTH_LONG);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.dismiss();
                activity.startActivity(it);
                this.dismiss();
                break;
            }
            case R.id.dialog_assign:{
                //Toast.makeText(getContext() , "Assign Student" , Toast.LENGTH_LONG).show();
                Intent it = new Intent(activity , Assign_Students_Group.class);
                it.putExtra("Group_id" , String.valueOf(group.getId()));
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.dismiss();
                activity.startActivity(it);
                this.dismiss();
                break;
            }

            case R.id.dialog_delete:{
                //Toast.makeText(getContext() , "Delete Test" , Toast.LENGTH_LONG).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("Warning");

                builder.setMessage("Are you sure to Delete?!1");
                builder.setPositiveButton("Ok", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_group();
                    }
                });
                builder.setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
            case R.id.dialog_cancel:{
                dismiss();
                break;
            }
            default:{
                dismiss();
            }
        }
    }

    public void delete_group() {
        String URL = Constants.URL_Teacher_Groups_Delete + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("-1")){
                            session.destroy_session();
                            Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                            progressDialog.dismiss();
                            return;
                        }
                        else if(s.equals("-2") || s.equals("-3")){
                            Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            progressDialog.dismiss();
                            return;
                        }
                        else {
                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Successfully Deleted", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast toast = Toast.makeText(activity.getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",String.valueOf(session.getUserId()));
                map.put("group_id",String.valueOf(group.getId()));
                return map;
            }
        };

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Registering);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

}
