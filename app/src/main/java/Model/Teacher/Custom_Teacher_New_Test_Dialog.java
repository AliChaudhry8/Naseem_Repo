package Model.Teacher;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Test_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by DELL on 9/27/2017.
 */

public class Custom_Teacher_New_Test_Dialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Dialog dialog;
    private Button next, cancel;
    private Test_BO tests;
    private TextView name;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime , test_name;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner spinner;
    Session session;
    String start_time;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;

    public Custom_Teacher_New_Test_Dialog(Activity a){
        super(a);
        activity = a;
        //tests = u;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_teacher_new_test);
        //edit = (Button)findViewById(R.id.dialog_edit);
        //assign = (Button)findViewById(R.id.dialog_assign);
        //st_Attempts = (Button)findViewById(R.id.dialog_st_attempts);
        //delete = (Button)findViewById(R.id.dialog_delete);
        test_name = (EditText) findViewById(R.id.test_name);
        next = (Button)findViewById(R.id.dialog_next);
        cancel = (Button)findViewById(R.id.dialog_cancel);
        //edit.setOnClickListener(this);
        //assign.setOnClickListener(this);
        //st_Attempts.setOnClickListener(this);
        //delete.setOnClickListener(this);
        next.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //name = (TextView)findViewById(R.id.name);
        //name.setText(tests.getName());

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.spinner1);
        spinner.setPrompt("Choose Time to Attempt");
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_edit:{
                Toast.makeText(getContext() , "Edit" , Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.dialog_assign:{
                Toast.makeText(getContext() , "Assign Student" , Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.dialog_st_attempts:{
                Toast.makeText(getContext() , "Student Attempts" , Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.dialog_delete:{
                Toast.makeText(getContext() , "Delete Test" , Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.dialog_next:{
                Toast.makeText(activity.getApplicationContext() , "Date: "+txtDate.getText().toString() +" "+"Time: "+txtTime.getText().toString() , Toast.LENGTH_LONG).show();
                start_time = txtDate.getText().toString()+" "+txtTime.getText().toString();
                sharedPreferences = activity.getSharedPreferences("new_Test_Details" , Context.MODE_PRIVATE);
                edit = sharedPreferences.edit();
                edit.putString("Test_Name" , test_name.getText().toString());
                edit.putString("Time_Attempt" , spinner.getSelectedItem().toString());
                edit.putString("Time_Start" , start_time);
                edit.commit();
                progressDialog.setMessage("Creating Test. After That you will create Test Contents...");
                progressDialog.show();
                make_test();
                break;
            }
            case R.id.dialog_cancel:{
                dismiss();
                break;
            }
            case R.id.btn_date:{
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText( year+"-"+(monthOfYear + 1) + "-" +dayOfMonth );

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            }
            case R.id.btn_time:{
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            }
            default:{
                dismiss();
            }
        }
    }


    public void make_test() {
        String URL = Constants.URL_Teacher_New_Test + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("-1")){
                            Toast.makeText(activity.getApplicationContext() , Constants.Error_Unauthorized_Access , Toast.LENGTH_LONG).show();
                            edit.remove("Test_Name");
                            edit.remove("Time_Attempt");
                            edit.remove("Time_Start");
                            edit.commit();
                            session.destroy_session();
                            progressDialog.dismiss();
                            return;
                        }
                        else if(s.equals("-2") || s.equals("-3")){
                            Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            edit.remove("Test_Name");
                            edit.remove("Time_Attempt");
                            edit.remove("Time_Start");
                            edit.commit();
                            progressDialog.dismiss();
                            return;
                        }
                        else {
                            try{
                                JsonParsor jsonParsor = new JsonParsor();
                                Test_BO test = jsonParsor.parse_Test(s);
                                Toast.makeText(activity.getApplicationContext() , "New Test ID: "+s.toString() , Toast.LENGTH_LONG).show();
                                Intent it = new Intent(activity , New_Test.class);
                                Bundle b = new Bundle();
                                b.putString("Edit" , "-1");
                                b.putSerializable("Test" , test);
                                it.putExtras(b);
                                activity.startActivity(it);
                                progressDialog.dismiss();
                                return;

                                //  Toast.makeText(context , "hello"+as.getSelected().get(1) , Toast.LENGTH_LONG).show();

                            }catch (Exception e){
                                Toast toast = Toast.makeText(activity.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast toast = Toast.makeText(activity.getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.User_Key_Id,String.valueOf(session.getUserId()));
                map.put("test_name", test_name.getText().toString());
                map.put("attempt_time" , spinner.getSelectedItem().toString());
                map.put("start_time" , start_time);
               // map.put("group_id",groupId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }
}