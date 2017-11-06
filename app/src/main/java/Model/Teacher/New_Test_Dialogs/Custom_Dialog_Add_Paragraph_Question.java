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

import BussinessObjects.Show_Test_BO.ParagraphQuestions;
import BussinessObjects.Test_BO;
import Model.Paragraph_Question_Event;
import Model.Session;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;

/**
 * Created by DELL on 10/2/2017.
 */

public class Custom_Dialog_Add_Paragraph_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button add , metadata;
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
    private Test_BO tests;
    EditText add_question ,  add_marks;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    New_Test_Custom_Text_Questions_Adapter text_q_adapter;

    public Custom_Dialog_Add_Paragraph_Question(Activity a) {
        super(a);
        activity = a;
        //tests = u;
        //text_q_adapter = ad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_new_paragraph_question);

        add = (Button) findViewById(R.id.dialog_add);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        add_question = (EditText)findViewById(R.id.paragraph_question_name);
        add_marks = (EditText)findViewById(R.id.paragraph_question_marks);
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
                ParagraphQuestions pgh = new ParagraphQuestions();

                pgh.setTitle(add_question.getText().toString());
                if(add_marks.getText().toString().equals(null) || add_marks.getText().toString().equals("") || add_marks.getText().toString().equals("null")) {
                    pgh.setMarks(0);
                }
                else{
                    pgh.setMarks(Integer.valueOf(add_marks.getText().toString()));
                }

                if(subject.getSelectedItem().equals("Subject")){
                    pgh.setSubject("");
                }
                else{
                    pgh.setSubject(subject.getSelectedItem().toString());
                }
                if(grade.getSelectedItem().equals("Grade")){
                    pgh.setGrade("");
                }
                else {
                    pgh.setGrade(grade.getSelectedItem().toString());
                }
                if(topic.getSelectedItem().equals("Topic")){
                    pgh.setTopic("");
                }
                else {
                    pgh.setTopic(topic.getSelectedItem().toString());
                }
                if(subtopic.getSelectedItem().equals("Sub Topic")){
                    pgh.setSubtopic("");
                }
                else{
                    pgh.setSubtopic(subtopic.getSelectedItem().toString());
                }
                if(inputmode.getSelectedItem().equals("Input Mode")){
                    pgh.setInputmode("");
                }
                else {
                    pgh.setInputmode(inputmode.getSelectedItem().toString());
                }
                if(presentationmode.getSelectedItem().equals("Presentation Mode")){
                    pgh.setPresentationmode("");
                }
                else{
                    pgh.setPresentationmode(presentationmode.getSelectedItem().toString());
                }
                if(cognitivefaculty.getSelectedItem().equals("Cognitive Faculty")){
                    pgh.setCognitivefaculty("");
                }
                else{
                    pgh.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                }
                if(steps.getSelectedItem().equals("Steps")){
                    pgh.setSteps("");
                }
                else {
                    pgh.setSteps(steps.getSelectedItem().toString());
                }
                if(teacherdifficulty.getSelectedItem().equals("Teacher Difficulty")){
                    pgh.setTeacherdifficulty("");
                }
                else{
                    pgh.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                }
                if(deviation.getSelectedItem().equals("Deviation")){
                    pgh.setDeviation("");
                }
                else {
                    pgh.setDeviation(deviation.getSelectedItem().toString());
                }
                if(ambiguity.getSelectedItem().equals("Ambiguity")){
                    pgh.setAmbiguity("");
                }
                else {
                    pgh.setAmbiguity(ambiguity.getSelectedItem().toString());
                }
                if(clarity.getSelectedItem().equals("Clarity")){
                    pgh.setClarity("");
                }
                else{
                    pgh.setClarity(clarity.getSelectedItem().toString());
                }
                if(blooms.getSelectedItem().equals("Blooms")){
                    pgh.setBlooms("");
                }else {
                    pgh.setBlooms(blooms.getSelectedItem().toString());
                }

               /* pgh.setSubject(subject.getSelectedItem().toString());
                pgh.setGrade(grade.getSelectedItem().toString());
                pgh.setTopic(topic.getSelectedItem().toString());
                pgh.setSubtopic(subtopic.getSelectedItem().toString());
                pgh.setInputmode(inputmode.getSelectedItem().toString());
                pgh.setPresentationmode(presentationmode.getSelectedItem().toString());
                pgh.setCognitivefaculty(cognitivefaculty.getSelectedItem().toString());
                pgh.setSteps(steps.getSelectedItem().toString());
                pgh.setTeacherdifficulty(teacherdifficulty.getSelectedItem().toString());
                pgh.setDeviation(deviation.getSelectedItem().toString());
                pgh.setAmbiguity(ambiguity.getSelectedItem().toString());
                pgh.setClarity(clarity.getSelectedItem().toString());
                pgh.setBlooms(blooms.getSelectedItem().toString());*/
                //pgh.setId(-1);
                Paragraph_Question_Event testQ = new Paragraph_Question_Event();
                testQ.setPgh(pgh);
                EventBus.getDefault().post(testQ);
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
}
