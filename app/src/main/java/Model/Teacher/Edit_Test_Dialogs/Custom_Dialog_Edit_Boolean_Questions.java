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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Show_Test_BO.BooleanQuestions;
import BussinessObjects.Test_BO;
import Model.Bool_Question_Event;
import Model.Constants;
import Model.JsonConverter;
import Model.Session;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;

/**
 * Created by DELL on 10/6/2017.
 */

public class Custom_Dialog_Edit_Boolean_Questions extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button edit1 , metadata;
    private Test_BO tests;
    private Spinner subject;
    private Spinner grade;
    private Spinner topic;
    private Spinner subtopic;
    private Spinner inputmode;
    private Spinner presentationmode;
    private Spinner cognitivefaculty;
    private Spinner steps;
    private Spinner teacherdifficulty;
    private Spinner deviation;
    private Spinner ambiguity;
    private Spinner clarity;
    private Spinner blooms;
    EditText add_question , add_answer , add_marks;
    RadioGroup bool_group;
    RadioButton bool_button;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    BooleanQuestions bool;
    RadioButton bool_true , bool_false;
    BooleanQuestions bol;
    New_Test_Custom_Text_Questions_Adapter text_q_adapter;
    ArrayList<BooleanQuestions> bools ;
    int position;

    public Custom_Dialog_Edit_Boolean_Questions(Activity a  , BooleanQuestions bool , ArrayList<BooleanQuestions> bools , int position) {
        super(a);
        activity = a;
        this.bool = bool;
        this.bools = bools;
        this.position = position;
        //tests = u;
        // text_q_adapter = ad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_edit_boolean_questions);

        edit1 = (Button) findViewById(R.id.dialog_edit);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        edit1.setOnClickListener(this);
        cancel.setOnClickListener(this);
        add_question = (EditText)findViewById(R.id.new_boolean_question);
        add_marks = (EditText)findViewById(R.id.boolean_question_marks);
        bool_group = (RadioGroup)findViewById(R.id.bool_group);
        bool_true = (RadioButton) findViewById(R.id.bool_true);
        bool_false = (RadioButton)findViewById(R.id.bool_false);

        subject = (Spinner)findViewById(R.id.spinner_subject);
        grade = (Spinner)findViewById(R.id.spinner_grade);
        topic = (Spinner)findViewById(R.id.spinner_topic);
        subtopic = (Spinner)findViewById(R.id.spinner_subtopic);
        inputmode = (Spinner)findViewById(R.id.spinner_inputmode);
        presentationmode = (Spinner)findViewById(R.id.spinner_presentationmode);
        cognitivefaculty  = (Spinner)findViewById(R.id.spinner_cognitivefaculty);
        steps = (Spinner)findViewById(R.id.spinner_steps);
        teacherdifficulty = (Spinner)findViewById(R.id.spinner_teacherdifficulty);
        deviation = (Spinner)findViewById(R.id.spinner_deviation);
        ambiguity =  (Spinner)findViewById(R.id.spinner_ambiguity);
        clarity = (Spinner)findViewById(R.id.spinner_clarity);
        blooms = (Spinner)findViewById(R.id.spinner_blooms);

        topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(topic.getSelectedItem().equals("Numbers")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.number_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
                else if(topic.getSelectedItem().equals("Addition")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.addition_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
                else if(topic.getSelectedItem().equals("Subtraction")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.subtraction_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
                else if(topic.getSelectedItem().equals("Comparison of Objects")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.comparison_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
                else if(topic.getSelectedItem().equals("Money")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.money_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
                else if(topic.getSelectedItem().equals("Time and Date")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.time_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
                else if(topic.getSelectedItem().equals("Geometry")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity.getBaseContext(), R.array.geometry_subtopic, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Spinner sItems = (Spinner) findViewById(R.id.spinner_subtopic);
                    subtopic.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        hide();
        metadata = (Button)findViewById(R.id.meta_text_question);
        metadata.setOnClickListener(this);

        add_question.setText(bool.getTitle());
        add_marks.setText(""+bool.getMarks());
        if(bool.isCorrect()){
            bool_true.setChecked(true);
        }
        else{
            bool_false.setChecked(true);
        }


        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_edit: {
                bol = new BooleanQuestions();

                bol.setTitle(add_question.getText().toString());
                if(add_marks.getText().toString().equals(null) || add_marks.getText().toString().equals("") || add_marks.getText().toString().equals("null")) {
                    bol.setMarks(0);
                }
                else{
                    bol.setMarks(Integer.valueOf(add_marks.getText().toString()));
                }
                int id = bool_group.getCheckedRadioButtonId();
                bool_button = (RadioButton)findViewById(id);
                if(bool_button.getText().toString().equals("True")){
                    bol.setCorrect(true);
                }
                else if(bool_button.getText().toString().equals("False")){
                    bol.setCorrect(false);
                }

                if(subject.getSelectedItem().equals("Subject")){
                    bol.setSubject("");
                }
                else{
                    bol.setSubject(subject.getSelectedItem().toString());
                }
                if(grade.getSelectedItem().equals("Grade")){
                    bol.setGrade("");
                }
                else {
                    bol.setGrade(grade.getSelectedItem().toString());
                }
                if(topic.getSelectedItem().equals("Topic")){
                    bol.setTopic("");
                }
                else {
                    bol.setTopic(topic.getSelectedItem().toString());
                }
                if(subtopic.getSelectedItem().equals("Sub Topic")){
                    bol.setSubtopic("");
                }
                else{
                    bol.setSubtopic(subtopic.getSelectedItem().toString());
                }
                if(inputmode.getSelectedItem().equals("Input Mode")){
                    bol.setInputmode("");
                }
                else {
                    bol.setInputmode(inputmode.getSelectedItem().toString());
                }
                if(presentationmode.getSelectedItem().equals("Presentation Mode")){
                    bol.setPresentationmode("");
                }
                else{
                    bol.setPresentationmode(presentationmode.getSelectedItem().toString());
                }
                if(cognitivefaculty.getSelectedItem().equals("Cognitive Faculty")){
                    bol.setCognitivefaculty("");
                }
                else{
                    bol.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                }
                if(steps.getSelectedItem().equals("Steps")){
                    bol.setSteps("");
                }
                else {
                    bol.setSteps(steps.getSelectedItem().toString());
                }
                if(teacherdifficulty.getSelectedItem().equals("Teacher Difficulty")){
                    bol.setTeacherdifficulty("");
                }
                else{
                    bol.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                }
                if(deviation.getSelectedItem().equals("Deviation")){
                    bol.setDeviation("");
                }
                else {
                    bol.setDeviation(deviation.getSelectedItem().toString());
                }
                if(ambiguity.getSelectedItem().equals("Ambiguity")){
                    bol.setAmbiguity("");
                }
                else {
                    bol.setAmbiguity(ambiguity.getSelectedItem().toString());
                }
                if(clarity.getSelectedItem().equals("Clarity")){
                    bol.setClarity("");
                }
                else{
                    bol.setClarity(clarity.getSelectedItem().toString());
                }
                if(blooms.getSelectedItem().equals("Blooms")){
                    bol.setBlooms("");
                }else {
                    bol.setBlooms(blooms.getSelectedItem().toString());
                }

               /* bol.setSubject(subject.getSelectedItem().toString());
                bol.setGrade(grade.getSelectedItem().toString());
                bol.setTopic(topic.getSelectedItem().toString());
                bol.setSubtopic(subtopic.getSelectedItem().toString());
                bol.setInputmode(inputmode.getSelectedItem().toString());
                bol.setPresentationmode(presentationmode.getSelectedItem().toString());
                bol.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                bol.setSteps(steps.getSelectedItem().toString());
                bol.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                bol.setDeviation(deviation.getSelectedItem().toString());
                bol.setAmbiguity(ambiguity.getSelectedItem().toString());
                bol.setClarity(clarity.getSelectedItem().toString());
                bol.setBlooms(blooms.getSelectedItem().toString());*/

                bools.set(position , bol);
                EventBus.getDefault().post(bools);
               /* bol.setId(bool.getId());
                JsonConverter convert = new JsonConverter();
                JSONObject j = new JSONObject();
                j = convert.edit_bool(bol);
                try {
                    j.put("id" , session.getUserId());
                    j.put("q_id", "" + bool.getId());
                    j.put("test_id" , bool.getTest_id());
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Edit_boolean_Question(j);*/
                this.dismiss();
                /*Toast.makeText(activity.getApplicationContext(), "Date: " + txtDate.getText().toString() + " " + "Time: " + txtTime.getText().toString(), Toast.LENGTH_LONG).show();
                start_time = txtDate.getText().toString() + " " + txtTime.getText().toString();
                sharedPreferences = activity.getSharedPreferences("new_Test_Details", Context.MODE_PRIVATE);
                edit = sharedPreferences.edit();
                edit.putString("Test_Name", test_name.getText().toString());
                edit.putString("Time_Attempt", spinner.getSelectedItem().toString());
                edit.putString("Time_Start", start_time);
                edit.commit();
                progressDialog.setMessage("Creating Test. After That you will create Test Contents...");
                progressDialog.show();
                make_test();*/
                break;
            }
            case R.id.meta_text_question:{
                if(metadata.getText().toString().equals("Show MetaData")){
                    metadata.setText("Hide MetaData");
                    subject.setVisibility(View.VISIBLE);
                    topic.setVisibility(View.VISIBLE);
                    subtopic.setVisibility(View.VISIBLE);
                    grade.setVisibility(View.VISIBLE);
                    inputmode.setVisibility(View.VISIBLE);
                    presentationmode.setVisibility(View.VISIBLE);
                    cognitivefaculty.setVisibility(View.VISIBLE);
                    steps.setVisibility(View.VISIBLE);
                    teacherdifficulty.setVisibility(View.VISIBLE);
                    deviation.setVisibility(View.VISIBLE);
                    ambiguity.setVisibility(View.VISIBLE);
                    clarity.setVisibility(View.VISIBLE);
                    blooms.setVisibility(View.VISIBLE);

                    subject.setSelection(getIndex(subject , bool.getSubject()));
                    topic.setSelection(getIndex(topic , bool.getTopic()));
                    subtopic.setSelection(getIndex(subtopic , bool.getSubtopic()));
                    grade.setSelection(getIndex(grade , bool.getGrade()));
                    inputmode.setSelection(getIndex(inputmode , bool.getInputmode()));
                    presentationmode.setSelection(getIndex(presentationmode , bool.getPresentationmode()));
                    cognitivefaculty.setSelection(getIndex(cognitivefaculty , bool.getCognitivefaculty()));
                    steps.setSelection(getIndex(steps , bool.getSteps()));
                    teacherdifficulty.setSelection(getIndex(teacherdifficulty , bool.getTeacherdifficulty()));
                    deviation.setSelection(getIndex(deviation , bool.getDeviation()));
                    ambiguity.setSelection(getIndex(ambiguity , bool.getAmbiguity()));
                    clarity.setSelection(getIndex(clarity , bool.getClarity()));
                    blooms.setSelection(getIndex(blooms , bool.getBlooms()));

                }
                else if(metadata.getText().toString().equals("Hide MetaData")){
                    metadata.setText("Show MetaData");
                    hide();
                }
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

    public void Edit_boolean_Question(JSONObject array)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_Edit_BoolQ + session.getAuthenticationToken() , array,
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

                                Bool_Question_Event boolQ = new Bool_Question_Event();
                                boolQ.setBol(bol);
                                EventBus.getDefault().post(boolQ);
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

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void hide(){
        subject.setVisibility(View.GONE);
        topic.setVisibility(View.GONE);
        subtopic.setVisibility(View.GONE);
        grade.setVisibility(View.GONE);
        inputmode.setVisibility(View.GONE);
        presentationmode.setVisibility(View.GONE);
        cognitivefaculty.setVisibility(View.GONE);
        steps.setVisibility(View.GONE);
        teacherdifficulty.setVisibility(View.GONE);
        deviation.setVisibility(View.GONE);
        ambiguity.setVisibility(View.GONE);
        clarity.setVisibility(View.GONE);
        blooms.setVisibility(View.GONE);
    }
}

