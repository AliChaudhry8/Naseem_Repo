package Model.Teacher.New_Test_Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Single_Choice_Option;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Single_Choice_Question;

/**
 * Created by DELL on 9/27/2017.
 */

public class New_Test_Custom_Single_Choice_Question_Adapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<SingleChoiceQuestions> singleChoiceQuestions;
    private New_Test showTest;
    private EditText subject;
    private EditText grade;
    private EditText topic;
    private EditText subtopic;
    private EditText inputmode;
    private EditText presentationmode;
    private EditText cognitivefaculty;
    private EditText steps;
    private EditText teacherdifficulty;
    private EditText deviation;
    private EditText ambiguity;
    private EditText clarity;
    private EditText blooms;
    private Button metadata;


    public New_Test_Custom_Single_Choice_Question_Adapter(Context c, ArrayList<SingleChoiceQuestions> questions, New_Test show){
        context = c;
        singleChoiceQuestions = questions;
        showTest =  show;
    }

    @Override
    public int getGroupCount() {
        return singleChoiceQuestions.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return singleChoiceQuestions.get(i).getOptions().size();
    }

    @Override
    public Object getGroup(int i) {
        return singleChoiceQuestions.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return singleChoiceQuestions.get(i).getOptions().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return singleChoiceQuestions.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return singleChoiceQuestions.get(i).getOptions().get(i1).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        final SingleChoiceQuestions scq = (SingleChoiceQuestions) getGroup(i);
        if(view ==  null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_new_single_choise_question, null);
            //LayoutInflater inflater = (LayoutInflater)this.context.getSystemService()
        }
        EditText title = (EditText) view.findViewById(R.id.new_single_question);
        EditText marks = (EditText) view.findViewById(R.id.new_single_question_marks);
        Button edit = (Button)view.findViewById(R.id.edit_single_question);
        Button remove = (Button)view.findViewById(R.id.remove_single_question);

        subject = (EditText)view.findViewById(R.id.spinner_subject);
        grade = (EditText)view.findViewById(R.id.spinner_grade);
        topic = (EditText)view.findViewById(R.id.spinner_topic);
        subtopic = (EditText)view.findViewById(R.id.spinner_subtopic);
        inputmode = (EditText)view.findViewById(R.id.spinner_inputmode);
        presentationmode = (EditText)view.findViewById(R.id.spinner_presentationmode);
        cognitivefaculty  = (EditText)view .findViewById(R.id.spinner_cognitivefaculty);
        steps = (EditText)view.findViewById(R.id.spinner_steps);
        teacherdifficulty = (EditText)view.findViewById(R.id.spinner_teacherdifficulty);
        deviation = (EditText)view.findViewById(R.id.spinner_deviation);
        ambiguity =  (EditText)view .findViewById(R.id.spinner_ambiguity);
        clarity = (EditText)view .findViewById(R.id.spinner_clarity);
        blooms = (EditText)view.findViewById(R.id.spinner_blooms);
        metadata = (Button)view.findViewById(R.id.meta_text_question);
        if(scq.getCheckMeta() == 0){
            metadata.setText("Show MetaData");
            hide();
        }
        else if(scq.getCheckMeta() == 1){
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

            subject.setText("Subject: "+scq.getSubject());
            topic.setText("Topic: "+scq.getTopic());
            subtopic.setText("Sub Topic: "+scq.getSubtopic());
            grade.setText("Grade: "+ scq.getGrade());
            inputmode.setText("Input Mode: "+scq.getInputmode());
            presentationmode.setText("Presentation Mode: "+scq.getPresentationmode());
            cognitivefaculty.setText("Cognitive Faculty: "+scq.getCognitivefaculty());
            steps.setText("Steps: "+scq.getSteps());
            teacherdifficulty.setText("Teacher Difficulty: "+scq.getTeacherdifficulty());
            deviation.setText("Deviation: "+ scq.getDeviation());
            ambiguity.setText("Ambiguity: "+scq.getAmbiguity());
            clarity.setText("Clarity: "+scq.getClarity());
            blooms.setText("Blooms: "+scq.getBlooms());
        }

        metadata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if(metadata.getText().toString().equals("Show MetaData")){
                    singleChoiceQuestions.get(i).setCheckMeta(1);
                    EventBus.getDefault().post(singleChoiceQuestions);
                }
                else if(metadata.getText().toString().equals("Hide MetaData")){
                    singleChoiceQuestions.get(i).setCheckMeta(0);
                    EventBus.getDefault().post(singleChoiceQuestions);
                }
            }
        });


        title.setText(scq.getTitle());
        marks.setText(""+scq.getMarks());
        //answer.setText("Correct: ");

        ArrayList<Single_Choice_Options> sco = scq.getOptions();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Custom_Dialog_Edit_Single_Choice_Question scq1 = new Custom_Dialog_Edit_Single_Choice_Question(showTest , scq , singleChoiceQuestions , i);
                scq1.show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleChoiceQuestions.remove(i);
                notifyDataSetChanged();
                EventBus.getDefault().post(singleChoiceQuestions);
            }
        });


       // showTest.setSingleChoiceQuestionSMarks(m);

        return view;
    }

    @Override
    public View getChildView(final int i,final int i1, boolean b, View view, ViewGroup viewGroup) {
        final Single_Choice_Options sco = (Single_Choice_Options)getChild(i, i1);
        final SingleChoiceQuestions scq = (SingleChoiceQuestions)getGroup(i);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_new_single_choice_option, null);
        }
        RadioButton rb = (RadioButton)view.findViewById(R.id.option);
        Button edit = (Button)view.findViewById(R.id.edit_single_option_question);
        Button remove = (Button)view.findViewById(R.id.remove_single_option_question);
        if(sco.getCorrect() == true){
            rb.setChecked(true);
        }
        else{
            rb.setChecked(false);
        }
        rb.setText(""+sco.getOption());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Custom_Dialog_Edit_Single_Choice_Option sco1 = new Custom_Dialog_Edit_Single_Choice_Option(showTest, sco, scq.getTest_id() , singleChoiceQuestions , i,i1);
                sco1.show();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleChoiceQuestions.get(i).getOptions().remove(i1);
                notifyDataSetChanged();
                EventBus.getDefault().post(singleChoiceQuestions);
            }
        });
       // rb.setEnabled(false);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
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

