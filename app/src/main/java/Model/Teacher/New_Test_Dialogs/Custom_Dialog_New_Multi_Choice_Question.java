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
import android.widget.Toast;

import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import BussinessObjects.Test_BO;
import Model.Multi_Choise_Option_Event;
import Model.Multi_Choise_Question_Event;
import Model.Session;

/**
 * Created by DELL on 10/2/2017.
 */

public class Custom_Dialog_New_Multi_Choice_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button add , add_option , metadata;
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

    public Custom_Dialog_New_Multi_Choice_Question(Activity a) {
        super(a);
        activity = a;
        //tests = u;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_multi_choice_question);
        multi_choice_options = new Multi_Choice_Options();
        multiChoiceQuestions = new MultipleChoiceQuestions();

        add = (Button) findViewById(R.id.dialog_add);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        add_option = (Button)findViewById(R.id.dialog_add_option);
        add.setOnClickListener(this);
        add_option.setOnClickListener(this);
        cancel.setOnClickListener(this);
        add_question = (EditText)findViewById(R.id.multi_question_name);
        add_marks = (EditText)findViewById(R.id.multi_question_marks);
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

                  /*  multiChoiceQuestions.setSubject(subject.getSelectedItem().toString());
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
                   // multi_choice_options.setId(-1);
                    Multi_Choise_Question_Event mcq = new Multi_Choise_Question_Event();
                    mcq.setMcq(multiChoiceQuestions);
                    EventBus.getDefault().post(mcq);
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
