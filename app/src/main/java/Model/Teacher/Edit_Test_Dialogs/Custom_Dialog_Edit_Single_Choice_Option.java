package Model.Teacher.Edit_Test_Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import BussinessObjects.Test_BO;
import Model.Constants;
import Model.JsonConverter;
import Model.Session;
import Model.Single_Choise_Option_Event;

/**
 * Created by DELL on 10/6/2017.
 */

public class Custom_Dialog_Edit_Single_Choice_Option extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button edit1 ;
    private Test_BO tests;
    private EditText option;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    Single_Choice_Options single_choice_options;
    CheckBox is_right;
    int test_id , position , positionchild;
    ArrayList<SingleChoiceQuestions> scqs;

    public Custom_Dialog_Edit_Single_Choice_Option(Activity a   , Single_Choice_Options sco , int test_id , ArrayList<SingleChoiceQuestions> scqs , int position , int positionchild) {
        super(a);
        activity = a;
        single_choice_options = new Single_Choice_Options();
        single_choice_options = sco;
        this.test_id = test_id;
        this.scqs = scqs;
        this.position = position;
        this.positionchild = positionchild;
        //tests = u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_edit_single_choice_option);


        edit1 = (Button) findViewById(R.id.dialog_edit);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        edit1.setOnClickListener(this);
        cancel.setOnClickListener(this);
        option = (EditText)findViewById(R.id.single_question_name);

        is_right = (CheckBox)findViewById(R.id.is_right);

        option.setText(single_choice_options.getOption());
        if(single_choice_options.getCorrect()){
            is_right.setChecked(true);
        }
        else{
            is_right.setChecked(false);
        }

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_edit: {
                if(option.getText().toString().equals("") || option.getText().toString().equals(null)){
                    Toast.makeText(activity.getApplicationContext() , "!!!!! Enter the Option Text !!!!!" , Toast.LENGTH_LONG).show();
                }
                else{
                    single_choice_options.setOption(option.getText().toString());
                    if(is_right.isChecked()){
                        if(scqs.get(position).isAlready()){
                            Toast.makeText(activity.getApplicationContext() , "You have Already Selected One Option true" , Toast.LENGTH_LONG).show();
                        }
                        else{
                            single_choice_options.setCorrect(is_right.isChecked());
                            scqs.get(position).setAlready(true);
                        }
                    }
                    else {
                        single_choice_options.setCorrect(is_right.isChecked());
                        scqs.get(position).setAlready(false);
                    }



                    scqs.get(position).getOptions().set(positionchild , single_choice_options);
                    EventBus.getDefault().post(scqs);
                   /* JsonConverter convert = new JsonConverter();
                    JSONObject j = new JSONObject();
                    j = convert.edit_sco(single_choice_options);
                    try {
                        j.put("id" , session.getUserId());
                        j.put("q_id", "" + single_choice_options.getQuestion_id());
                        j.put("o_id" , "" + single_choice_options.getId());
                        j.put("test_id" , ""+test_id);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    Edit_Single_Option(j);*/


                    this.dismiss();
                }
               /* if(singleChoiceQuestions.getOptions().size() == 0){
                    Toast.makeText(activity.getApplicationContext() , "Please Add Minimum of One Option" , Toast.LENGTH_LONG).show();
                }
                else {
                    Text_Questions txt = new Text_Questions();

                    txt.setTitle(add_question.getText().toString());
                    if (add_marks.getText().toString().equals(null) || add_marks.getText().toString().equals("") || add_marks.getText().toString().equals("null")) {
                        txt.setMarks(0);
                    } else {
                        txt.setMarks(Integer.valueOf(add_marks.getText().toString()));
                    }
                    Text_Question_Event testQ = new Text_Question_Event();
                    testQ.setTestQ(txt);
                    EventBus.getDefault().post(testQ);
                    this.dismiss();*/
                //}
                break;
            }
            case R.id.dialog_add_option:{

                break;
            }
            case R.id.dialog_cancel: {
                dismiss();
                break;
            }
            default: {
                dismiss();
            }
        }
    }

    public void Edit_Single_Option(JSONObject array)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_Edit_SingleO + session.getAuthenticationToken() , array,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("response").equals("-1")) {
                                Toast.makeText(activity, Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                return;
                            } if (response.get("response").equals("-2") || response.get("response").equals("-3") ) {
                                Toast.makeText(activity, Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                return;
                            } else if (response.get("response").equals("1")) {
                                Toast.makeText(activity, "Successfully Edit Test", Toast.LENGTH_SHORT).show();

                                Single_Choise_Option_Event scoQ = new Single_Choise_Option_Event();
                                scoQ.setSco(single_choice_options);
                                EventBus.getDefault().post(scoQ);
                                progressDialog.dismiss();
                            }
                        }catch (JSONException e){
                            Toast.makeText(activity , Constants.Error_Unrecognized_Error , Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity , Constants.Error_Unrecognized_Error , Toast.LENGTH_LONG).show();
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

        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Registering);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(jsonObjectRequest);
    }
}

