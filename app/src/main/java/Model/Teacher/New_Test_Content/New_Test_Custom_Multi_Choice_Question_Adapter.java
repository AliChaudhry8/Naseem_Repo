package Model.Teacher.New_Test_Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Model.Check_MetaData_Event;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Multi_Choice_Option;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Multi_Choice_Question;

/**
 * Created by DELL on 9/27/2017.
 */

public class New_Test_Custom_Multi_Choice_Question_Adapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions;
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

    public New_Test_Custom_Multi_Choice_Question_Adapter(Context c, ArrayList<MultipleChoiceQuestions> multi, New_Test show) {
        context = c;
        multipleChoiceQuestions = multi;
        showTest = show;
    }

    @Override
    public int getGroupCount() {
        return multipleChoiceQuestions.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return multipleChoiceQuestions.get(i).getOptions().size();
    }

    @Override
    public Object getGroup(int i) {
        return multipleChoiceQuestions.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return multipleChoiceQuestions.get(i).getOptions().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return multipleChoiceQuestions.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return multipleChoiceQuestions.get(i).getOptions().get(i1).getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        final MultipleChoiceQuestions mcq = (MultipleChoiceQuestions) getGroup(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_new_multi_choise_question, null);
            //LayoutInflater inflater = (LayoutInflater)this.context.getSystemService()
        }
        TextView title = (TextView) view.findViewById(R.id.new_multi_question);
        TextView marks = (TextView) view.findViewById(R.id.new_multi_question_marks);
        Button edit = (Button) view.findViewById(R.id.edit_multi_question);
        Button remove = (Button)view.findViewById(R.id.remove_multi_question);

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

        if(mcq.getCheckMeta() == 0){
            metadata.setText("Show MetaData");
            hide();
        }
        else if(mcq.getCheckMeta() == 1){
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

            subject.setText("Subject: "+mcq.getSubject());
            topic.setText("Topic: "+mcq.getTopic());
            subtopic.setText("Sub Topic: "+mcq.getSubtopic());
            grade.setText("Grade: "+ mcq.getGrade());
            inputmode.setText("Input Mode: "+mcq.getInputmode());
            presentationmode.setText("Presentation Mode: "+mcq.getPresentationmode());
            cognitivefaculty.setText("Cognitive Faculty: "+mcq.getCognitivefaculty());
            steps.setText("Steps: "+mcq.getSteps());
            teacherdifficulty.setText("Teacher Difficulty: "+mcq.getTeacherdifficulty());
            deviation.setText("Deviation: "+ mcq.getDeviation());
            ambiguity.setText("Ambiguity: "+mcq.getAmbiguity());
            clarity.setText("Clarity: "+mcq.getClarity());
            blooms.setText("Blooms: "+mcq.getBlooms());
        }

        //int id = scq.getStd_answer();
        //if(scq.getOptions().)
        title.setText(mcq.getTitle());
        marks.setText("" + mcq.getMarks());
        //answer.setText("Correct: ");
        ArrayList<Multi_Choice_Options> mco = mcq.getOptions();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Custom_Dialog_Edit_Multi_Choice_Question mcq1 = new Custom_Dialog_Edit_Multi_Choice_Question(showTest , mcq , multipleChoiceQuestions , i);
                mcq1.show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleChoiceQuestions.remove(i);
                notifyDataSetChanged();
                EventBus.getDefault().post(multipleChoiceQuestions);
            }
        });

        metadata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if(metadata.getText().toString().equals("Show MetaData")){
                    multipleChoiceQuestions.get(i).setCheckMeta(1);
                    Check_MetaData_Event event = new Check_MetaData_Event();
                    EventBus.getDefault().post(event);
                }
                else if(metadata.getText().toString().equals("Hide MetaData")){
                    multipleChoiceQuestions.get(i).setCheckMeta(0);
                    Check_MetaData_Event event = new Check_MetaData_Event();
                    EventBus.getDefault().post(event);
                }
            }
        });

        // showTest.setSingleChoiceQuestionSMarks(m);

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final Multi_Choice_Options mco = (Multi_Choice_Options) getChild(i, i1);
        final MultipleChoiceQuestions mcq = (MultipleChoiceQuestions) getGroup(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_choice_option, null);
        }
        CheckBox rb = (CheckBox) view.findViewById(R.id.option);
        Button edit = (Button) view.findViewById(R.id.edit_multi_option_question);
        Button remove = (Button)view.findViewById(R.id.remove_multi_option_question);
        if (mco.isCorrect() == true) {
            rb.setChecked(true);
        }
        else{
            rb.setChecked(false);
        }
        rb.setText("" + mco.getOption());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Custom_Dialog_Edit_Multi_Choice_Option mco1 = new Custom_Dialog_Edit_Multi_Choice_Option(showTest, mco, mcq.getTest_id() , multipleChoiceQuestions , i , i1);
                mco1.show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleChoiceQuestions.get(i).getOptions().remove(i1);
                notifyDataSetChanged();
                EventBus.getDefault().post(multipleChoiceQuestions);
            }
        });
        //rb.setEnabled(false);
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