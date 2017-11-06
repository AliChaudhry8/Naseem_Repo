package Model.Teacher.New_Test_Dialogs;

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

import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import BussinessObjects.Show_Test_BO.Text_Questions;
import BussinessObjects.Test_BO;
import Model.Session;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;
import Model.Text_Question_Event;

/**
 * Created by DELL on 9/27/2017.
 */

public class Custom_Dialog_Add_Text_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button  cancel;
    private Button add , metadata;
    private Test_BO tests;
    EditText add_question , add_answer , add_marks;
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
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    New_Test_Custom_Text_Questions_Adapter text_q_adapter;

    public Custom_Dialog_Add_Text_Question(Activity a , New_Test_Custom_Text_Questions_Adapter ad ) {
        super(a);
        activity = a;
        //tests = u;
        text_q_adapter = ad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_add_text_question);

        add = (Button) findViewById(R.id.dialog_add);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        add_question = (EditText)findViewById(R.id.text_question_name);
        add_answer = (EditText)findViewById(R.id.text_answer);
        add_marks = (EditText)findViewById(R.id.text_question_marks);
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

        //subject.setPrompt("Subject");
        //topic.setPrompt("Topic");
        metadata = (Button)findViewById(R.id.meta_text_question);
        metadata.setOnClickListener(this);
        hide();

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_add: {
                Text_Questions txt = new Text_Questions();

                txt.setTitle(add_question.getText().toString());
                txt.setAnswer(add_answer.getText().toString());
                if(add_marks.getText().toString().equals(null) || add_marks.getText().toString().equals("") || add_marks.getText().toString().equals("null")) {
                    txt.setMarks(0);
                }
                else{
                    txt.setMarks(Integer.valueOf(add_marks.getText().toString()));
                }

                if(subject.getSelectedItem().equals("Subject")){
                    txt.setSubject("");
                }
                else{
                    txt.setSubject(subject.getSelectedItem().toString());
                }
                if(grade.getSelectedItem().equals("Grade")){
                    txt.setGrade("");
                }
                else {
                    txt.setGrade(grade.getSelectedItem().toString());
                }
                if(topic.getSelectedItem().equals("Topic")){
                    txt.setTopic("");
                }
                else {
                    txt.setTopic(topic.getSelectedItem().toString());
                }
                if(subtopic.getSelectedItem().equals("Sub Topic")){
                    txt.setSubtopic("");
                }
                else{
                    txt.setSubtopic(subtopic.getSelectedItem().toString());
                }
                if(inputmode.getSelectedItem().equals("Input Mode")){
                    txt.setInputmode("");
                }
                else {
                    txt.setInputmode(inputmode.getSelectedItem().toString());
                }
                if(presentationmode.getSelectedItem().equals("Presentation Mode")){
                    txt.setPresentationmode("");
                }
                else{
                    txt.setPresentationmode(presentationmode.getSelectedItem().toString());
                }
                if(cognitivefaculty.getSelectedItem().equals("Cognitive Faculty")){
                    txt.setCognitivefaculty("");
                }
                else{
                    txt.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                }
                if(steps.getSelectedItem().equals("Steps")){
                    txt.setSteps("");
                }
                else {
                    txt.setSteps(steps.getSelectedItem().toString());
                }
                if(teacherdifficulty.getSelectedItem().equals("Teacher Difficulty")){
                    txt.setTeacherdifficulty("");
                }
                else{
                    txt.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                }
                if(deviation.getSelectedItem().equals("Deviation")){
                    txt.setDeviation("");
                }
                else {
                    txt.setDeviation(deviation.getSelectedItem().toString());
                }
                if(ambiguity.getSelectedItem().equals("Ambiguity")){
                    txt.setAmbiguity("");
                }
                else {
                    txt.setAmbiguity(ambiguity.getSelectedItem().toString());
                }
                if(clarity.getSelectedItem().equals("Clarity")){
                    txt.setClarity("");
                }
                else{
                    txt.setClarity(clarity.getSelectedItem().toString());
                }
                if(blooms.getSelectedItem().equals("Blooms")){
                    txt.setBlooms("");
                }else {
                    txt.setBlooms(blooms.getSelectedItem().toString());
                }

               /* txt.setSubject(subject.getSelectedItem().toString());
                txt.setGrade(grade.getSelectedItem().toString());
                txt.setTopic(topic.getSelectedItem().toString());
                txt.setSubtopic(subtopic.getSelectedItem().toString());
                txt.setInputmode(inputmode.getSelectedItem().toString());
                txt.setPresentationmode(presentationmode.getSelectedItem().toString());
                txt.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                txt.setSteps(steps.getSelectedItem().toString());
                txt.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                txt.setDeviation(deviation.getSelectedItem().toString());
                txt.setAmbiguity(ambiguity.getSelectedItem().toString());
                txt.setClarity(clarity.getSelectedItem().toString());
                txt.setBlooms(blooms.getSelectedItem().toString());*/
               // txt.setId(-1);
                Text_Question_Event testQ = new Text_Question_Event();
                testQ.setTestQ(txt);
                EventBus.getDefault().post(testQ);
                this.dismiss();

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
    /*public void show(){
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
    }*/
}