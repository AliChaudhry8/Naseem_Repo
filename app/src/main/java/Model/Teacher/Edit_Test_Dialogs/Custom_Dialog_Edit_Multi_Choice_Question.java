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
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import BussinessObjects.Test_BO;
import Model.Constants;
import Model.JsonConverter;
import Model.Multi_Choise_Option_Event;
import Model.Multi_Choise_Question_Event;
import Model.Session;
import Model.Teacher.New_Test_Dialogs.Custom_Dialog_Add_Multi_Option;

/**
 * Created by DELL on 10/6/2017.
 */

public class Custom_Dialog_Edit_Multi_Choice_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button edit1 , add_option , metadata;
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
    EditText add_question  , add_marks;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    Multi_Choice_Options multi_choice_options;
    MultipleChoiceQuestions multiChoiceQuestions ;
    int test_id , position;
    ArrayList<MultipleChoiceQuestions> mcqs;

    public Custom_Dialog_Edit_Multi_Choice_Question(Activity a , MultipleChoiceQuestions mcq  , ArrayList<MultipleChoiceQuestions> mcqs , int position) {
        super(a);
        activity = a;
        multi_choice_options = new Multi_Choice_Options();
        multiChoiceQuestions = new MultipleChoiceQuestions();
        multiChoiceQuestions = mcq;
        this.mcqs = mcqs;
        this.position = position;
        this.test_id = test_id;
        //tests = u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_edit_multi_choice_question);


        edit1 = (Button) findViewById(R.id.dialog_edit);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        add_option = (Button)findViewById(R.id.dialog_add_option);
        edit1.setOnClickListener(this);
        add_option.setOnClickListener(this);
        cancel.setOnClickListener(this);
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
        add_question = (EditText)findViewById(R.id.multi_question_name);
        add_marks = (EditText)findViewById(R.id.multi_question_marks);

        add_question.setText(multiChoiceQuestions.getTitle());
        add_marks.setText(""+multiChoiceQuestions.getMarks());

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_edit: {
                if(add_question.getText().toString().equals("") || add_question.getText().equals(null)){
                    Toast.makeText(activity.getApplicationContext() , "Please Enter Question Statement" , Toast.LENGTH_LONG).show();
                }
                if(multiChoiceQuestions.getOptions().size() == 0 ){
                    Toast.makeText(activity.getApplicationContext() , "Please Add Minimum of One Option" , Toast.LENGTH_LONG).show();
                }

                else {
                    multiChoiceQuestions.setTitle(add_question.getText().toString());

                    if (add_marks.getText().toString().equals(null) || add_marks.getText().toString().equals("") || add_marks.getText().toString().equals("null")) {
                        multiChoiceQuestions.setMarks(Integer.valueOf("1"));
                    } else {
                        multiChoiceQuestions.setMarks(Integer.valueOf(add_marks.getText().toString()));
                    }

                    if(subject.getSelectedItem().equals("Subject")){
                        multiChoiceQuestions.setSubject("");
                    }
                    else{
                        multiChoiceQuestions.setSubject(subject.getSelectedItem().toString());
                    }
                    if(grade.getSelectedItem().equals("Grade")){
                        multiChoiceQuestions.setGrade("");
                    }
                    else {
                        multiChoiceQuestions.setGrade(grade.getSelectedItem().toString());
                    }
                    if(topic.getSelectedItem().equals("Topic")){
                        multiChoiceQuestions.setTopic("");
                    }
                    else {
                        multiChoiceQuestions.setTopic(topic.getSelectedItem().toString());
                    }
                    if(subtopic.getSelectedItem().equals("Sub Topic")){
                        multiChoiceQuestions.setSubtopic("");
                    }
                    else{
                        multiChoiceQuestions.setSubtopic(subtopic.getSelectedItem().toString());
                    }
                    if(inputmode.getSelectedItem().equals("Input Mode")){
                        multiChoiceQuestions.setInputmode("");
                    }
                    else {
                        multiChoiceQuestions.setInputmode(inputmode.getSelectedItem().toString());
                    }
                    if(presentationmode.getSelectedItem().equals("Presentation Mode")){
                        multiChoiceQuestions.setPresentationmode("");
                    }
                    else{
                        multiChoiceQuestions.setPresentationmode(presentationmode.getSelectedItem().toString());
                    }
                    if(cognitivefaculty.getSelectedItem().equals("Cognitive Faculty")){
                        multiChoiceQuestions.setCognitivefaculty("");
                    }
                    else{
                        multiChoiceQuestions.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                    }
                    if(steps.getSelectedItem().equals("Steps")){
                        multiChoiceQuestions.setSteps("");
                    }
                    else {
                        multiChoiceQuestions.setSteps(steps.getSelectedItem().toString());
                    }
                    if(teacherdifficulty.getSelectedItem().equals("Teacher Difficulty")){
                        multiChoiceQuestions.setTeacherdifficulty("");
                    }
                    else{
                        multiChoiceQuestions.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                    }
                    if(deviation.getSelectedItem().equals("Deviation")){
                        multiChoiceQuestions.setDeviation("");
                    }
                    else {
                        multiChoiceQuestions.setDeviation(deviation.getSelectedItem().toString());
                    }
                    if(ambiguity.getSelectedItem().equals("Ambiguity")){
                        multiChoiceQuestions.setAmbiguity("");
                    }
                    else {
                        multiChoiceQuestions.setAmbiguity(ambiguity.getSelectedItem().toString());
                    }
                    if(clarity.getSelectedItem().equals("Clarity")){
                        multiChoiceQuestions.setClarity("");
                    }
                    else{
                        multiChoiceQuestions.setClarity(clarity.getSelectedItem().toString());
                    }
                    if(blooms.getSelectedItem().equals("Blooms")){
                        multiChoiceQuestions.setBlooms("");
                    }else {
                        multiChoiceQuestions.setBlooms(blooms.getSelectedItem().toString());
                    }

                   /* multiChoiceQuestions.setSubject(subject.getSelectedItem().toString());
                    multiChoiceQuestions.setGrade(grade.getSelectedItem().toString());
                    multiChoiceQuestions.setTopic(topic.getSelectedItem().toString());
                    multiChoiceQuestions.setSubtopic(subtopic.getSelectedItem().toString());
                    multiChoiceQuestions.setInputmode(inputmode.getSelectedItem().toString());
                    multiChoiceQuestions.setPresentationmode(presentationmode.getSelectedItem().toString());
                    multiChoiceQuestions.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                    multiChoiceQuestions.setSteps(steps.getSelectedItem().toString());
                    multiChoiceQuestions.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                    multiChoiceQuestions.setDeviation(deviation.getSelectedItem().toString());
                    multiChoiceQuestions.setAmbiguity(ambiguity.getSelectedItem().toString());
                    multiChoiceQuestions.setClarity(clarity.getSelectedItem().toString());
                    multiChoiceQuestions.setBlooms(blooms.getSelectedItem().toString());*/
                    mcqs.set(position , multiChoiceQuestions);
                    EventBus.getDefault().post(mcqs);
                    /*JsonConverter convert = new JsonConverter();
                    JSONObject j = new JSONObject();
                    j = convert.edit_multi(multiChoiceQuestions);
                    try {
                        j.put("id" , session.getUserId());
                        j.put("q_id", "" + multiChoiceQuestions.getId());
                        j.put("test_id" , multiChoiceQuestions.getTest_id());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    Edit_Multi_Question(j);*/
                    this.dismiss();

                }
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

                    subject.setSelection(getIndex(subject , multiChoiceQuestions.getSubject()));
                    topic.setSelection(getIndex(topic , multiChoiceQuestions.getTopic()));
                    subtopic.setSelection(getIndex(subtopic , multiChoiceQuestions.getSubtopic()));
                    grade.setSelection(getIndex(grade , multiChoiceQuestions.getGrade()));
                    inputmode.setSelection(getIndex(inputmode , multiChoiceQuestions.getInputmode()));
                    presentationmode.setSelection(getIndex(presentationmode , multiChoiceQuestions.getPresentationmode()));
                    cognitivefaculty.setSelection(getIndex(cognitivefaculty , multiChoiceQuestions.getCognitivefaculty()));
                    steps.setSelection(getIndex(steps , multiChoiceQuestions.getSteps()));
                    teacherdifficulty.setSelection(getIndex(teacherdifficulty , multiChoiceQuestions.getTeacherdifficulty()));
                    deviation.setSelection(getIndex(deviation , multiChoiceQuestions.getDeviation()));
                    ambiguity.setSelection(getIndex(ambiguity , multiChoiceQuestions.getAmbiguity()));
                    clarity.setSelection(getIndex(clarity , multiChoiceQuestions.getClarity()));
                    blooms.setSelection(getIndex(blooms , multiChoiceQuestions.getBlooms()));

                }
                else if(metadata.getText().toString().equals("Hide MetaData")){
                    metadata.setText("Show MetaData");
                    hide();
                }
                break;
            }
            case R.id.dialog_add_option:{
                Custom_Dialog_Add_Multi_Option scq_O = new Custom_Dialog_Add_Multi_Option(activity , multiChoiceQuestions);
                scq_O.show();
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

    @Subscribe
    public void onEvent(Multi_Choise_Option_Event event){
        //Toast.makeText(activity.getApplicationContext() , "Message: "+event.getSco().getCorrect(), Toast.LENGTH_LONG).show();
        multiChoiceQuestions.getOptions().add(event.getMco());
        //text_questionses.add(event.getTestQ());

        // set_Text_Questions();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void Edit_Multi_Question(JSONObject array)
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_Edit_MultiQ + session.getAuthenticationToken() , array,
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

                                Multi_Choise_Question_Event mcq = new Multi_Choise_Question_Event();
                                mcq.setMcq(multiChoiceQuestions);
                                EventBus.getDefault().post(mcq);
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

}

