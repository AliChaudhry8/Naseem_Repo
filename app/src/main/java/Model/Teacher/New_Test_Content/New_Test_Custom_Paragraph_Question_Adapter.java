package Model.Teacher.New_Test_Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Model.Check_MetaData_Event;
import Model.Paragraph_Question_Event;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.ParagraphQuestions;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Paragraph_Question;

/**
 * Created by DELL on 9/27/2017.
 */

public class New_Test_Custom_Paragraph_Question_Adapter extends ArrayAdapter<ParagraphQuestions> {

    private Context context;
    private ArrayList<ParagraphQuestions> paragraphQuestions;
    private New_Test showTest;
    New_Test_Custom_Paragraph_Question_Adapter.ViewHolder v = null;

    public New_Test_Custom_Paragraph_Question_Adapter(Context c, ArrayList<ParagraphQuestions> questions, New_Test show){
        super(c, 0, questions);
        context = c;
        paragraphQuestions = questions;
        showTest = show;
    }

    private class ViewHolder {
        private EditText title;
        private EditText marks;
        private Button remove;
        private  Button edit;

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


    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        //View superView = super.getView(position, convertView, parent);
        final ParagraphQuestions questions = getItem(position);

        try {
            if (convertView == null) {
                v = new New_Test_Custom_Paragraph_Question_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_new_paragraph_question, parent, false);
                v.title = (EditText) convertView.findViewById(R.id.paragraph_question);
                v.marks = (EditText) convertView.findViewById(R.id.paragraph_marks);
                v.remove = (Button) convertView.findViewById(R.id.remove_paragraph_question);
                v.edit = (Button) convertView.findViewById(R.id.edit_paragraph_question);
                v.subject = (EditText)convertView.findViewById(R.id.spinner_subject);
                v.grade = (EditText)convertView.findViewById(R.id.spinner_grade);
                v.topic = (EditText)convertView.findViewById(R.id.spinner_topic);
                v.subtopic = (EditText)convertView.findViewById(R.id.spinner_subtopic);
                v.inputmode = (EditText)convertView.findViewById(R.id.spinner_inputmode);
                v.presentationmode = (EditText)convertView.findViewById(R.id.spinner_presentationmode);
                v.cognitivefaculty  = (EditText)convertView .findViewById(R.id.spinner_cognitivefaculty);
                v.steps = (EditText)convertView.findViewById(R.id.spinner_steps);
                v.teacherdifficulty = (EditText)convertView.findViewById(R.id.spinner_teacherdifficulty);
                v.deviation = (EditText)convertView.findViewById(R.id.spinner_deviation);
                v.ambiguity =  (EditText)convertView .findViewById(R.id.spinner_ambiguity);
                v.clarity = (EditText)convertView .findViewById(R.id.spinner_clarity);
                v.blooms = (EditText)convertView.findViewById(R.id.spinner_blooms);
                v.metadata = (Button)convertView.findViewById(R.id.meta_text_question);
                if(questions.getCheckMeta() == 0){
                    v.metadata.setText("Show MetaData");
                    hide();
                }
                else if(questions.getCheckMeta() == 1){
                    v.metadata.setText("Hide MetaData");
                    v.subject.setVisibility(View.VISIBLE);
                    v.topic.setVisibility(View.VISIBLE);
                    v.subtopic.setVisibility(View.VISIBLE);
                    v.grade.setVisibility(View.VISIBLE);
                    v.inputmode.setVisibility(View.VISIBLE);
                    v.presentationmode.setVisibility(View.VISIBLE);
                    v.cognitivefaculty.setVisibility(View.VISIBLE);
                    v.steps.setVisibility(View.VISIBLE);
                    v.teacherdifficulty.setVisibility(View.VISIBLE);
                    v.deviation.setVisibility(View.VISIBLE);
                    v.ambiguity.setVisibility(View.VISIBLE);
                    v.clarity.setVisibility(View.VISIBLE);
                    v.blooms.setVisibility(View.VISIBLE);

                    v.subject.setText("Subject: "+questions.getSubject());
                    v.topic.setText("Topic: "+questions.getTopic());
                    v.subtopic.setText("Sub Topic: "+questions.getSubtopic());
                    v.grade.setText("Grade: "+ questions.getGrade());
                    v.inputmode.setText("Input Mode: "+questions.getInputmode());
                    v.presentationmode.setText("Presentation Mode: "+questions.getPresentationmode());
                    v.cognitivefaculty.setText("Cognitive Faculty: "+questions.getCognitivefaculty());
                    v.steps.setText("Steps: "+questions.getSteps());
                    v.teacherdifficulty.setText("Teacher Difficulty: "+questions.getTeacherdifficulty());
                    v.deviation.setText("Deviation: "+ questions.getDeviation());
                    v.ambiguity.setText("Ambiguity: "+questions.getAmbiguity());
                    v.clarity.setText("Clarity: "+questions.getClarity());
                    v.blooms.setText("Blooms: "+questions.getBlooms());
                }
                convertView.setTag(v);
            }
            else {
                v = (New_Test_Custom_Paragraph_Question_Adapter.ViewHolder) convertView.getTag();
            }
            v.title.setText(questions.getTitle());
            v.marks.setText(""+questions.getMarks());
            v.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paragraphQuestions.remove(position);
                    notifyDataSetChanged();
                    EventBus.getDefault().post(paragraphQuestions);
                }
            });

            v.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Custom_Dialog_Edit_Paragraph_Question edit = new Custom_Dialog_Edit_Paragraph_Question(showTest, questions , paragraphQuestions , position);
                    edit.show();
                }
            });

            v.metadata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    if(v.metadata.getText().toString().equals("Show MetaData")){
                        paragraphQuestions.get(position).setCheckMeta(1);
                        Check_MetaData_Event event = new Check_MetaData_Event();
                        EventBus.getDefault().post(event);
                    }
                    else if(v.metadata.getText().toString().equals("Hide MetaData")){
                        paragraphQuestions.get(position).setCheckMeta(0);
                        EventBus.getDefault().post(paragraphQuestions);
                    }
                }
            });

            //showTest.setParagraphQuestionsMarks(m);
            return convertView;
        }catch (Exception e) {
            return convertView;
        }
    }

    public void hide(){
        v.subject.setVisibility(View.GONE);
        v.topic.setVisibility(View.GONE);
        v.subtopic.setVisibility(View.GONE);
        v.grade.setVisibility(View.GONE);
        v.inputmode.setVisibility(View.GONE);
        v.presentationmode.setVisibility(View.GONE);
        v.cognitivefaculty.setVisibility(View.GONE);
        v.steps.setVisibility(View.GONE);
        v.teacherdifficulty.setVisibility(View.GONE);
        v.deviation.setVisibility(View.GONE);
        v.ambiguity.setVisibility(View.GONE);
        v.clarity.setVisibility(View.GONE);
        v.blooms.setVisibility(View.GONE);
    }
    public void show(){
        v.subject.setVisibility(View.VISIBLE);
        v.topic.setVisibility(View.VISIBLE);
        v.subtopic.setVisibility(View.VISIBLE);
        v.grade.setVisibility(View.VISIBLE);
        v.inputmode.setVisibility(View.VISIBLE);
        v.presentationmode.setVisibility(View.VISIBLE);
        v.cognitivefaculty.setVisibility(View.VISIBLE);
        v.steps.setVisibility(View.VISIBLE);
        v.teacherdifficulty.setVisibility(View.VISIBLE);
        v.deviation.setVisibility(View.VISIBLE);
        v.ambiguity.setVisibility(View.VISIBLE);
        v.clarity.setVisibility(View.VISIBLE);
        v.blooms.setVisibility(View.VISIBLE);
    }
}

