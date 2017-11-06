package Model.Student.Show_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.ShowTest;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Marks;
import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;

/**
 * Created by Muhammad Taimoor on 7/7/2017.
 */

public class Show_Test_Custom_Single_Choice_Question_Adapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<SingleChoiceQuestions> singleChoiceQuestions;
    private ShowTest showTest;


    public Show_Test_Custom_Single_Choice_Question_Adapter(Context c, ArrayList<SingleChoiceQuestions> questions, ShowTest show){
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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        SingleChoiceQuestions scq = (SingleChoiceQuestions) getGroup(i);
        if(view ==  null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_single_choice_question, null);
            //LayoutInflater inflater = (LayoutInflater)this.context.getSystemService()
        }
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView marks = (TextView)view.findViewById(R.id.marks);
        TextView answer = (TextView)view.findViewById(R.id.answer);

        //int id = scq.getStd_answer();
        //if(scq.getOptions().)
        title.setText("Q: "+scq.getTitle());
        marks.setText(""+scq.getMarks());
        //answer.setText("Correct: ");
        ArrayList<Single_Choice_Options> sco = scq.getOptions();
        int count = sco.size();
        int answer_id = scq.getStd_answer();
        Marks m = new Marks();
        m.setId(scq.getId());
        m.setTotal(scq.getMarks());
        boolean flag = false;
        for(int j=0; j<count; j++){
            if(sco.get(j).getCorrect())
                answer.setText("Correct: " + sco.get(j).getOption());

            if(sco.get(j).getCorrect() && answer_id == sco.get(j).getId()){
                marks.setText(scq.getMarks()+"/"+scq.getMarks());
                m.setObtained(scq.getMarks());
                flag = true;
            }
        }
        if(!flag){
            marks.setText("0/"+scq.getMarks());
            m.setObtained(0);
        }
        showTest.setSingleChoiceQuestionSMarks(m);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Single_Choice_Options sco = (Single_Choice_Options)getChild(i, i1);
        SingleChoiceQuestions scq = (SingleChoiceQuestions)getGroup(i);
         if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_single_choice_options, null);
        }
        CheckBox rb = (CheckBox) view.findViewById(R.id.option);
        if(scq.getStd_answer() == sco.getId()){
            rb.setChecked(true);
        }
        rb.setText(""+sco.getOption());
        rb.setEnabled(false);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


}
