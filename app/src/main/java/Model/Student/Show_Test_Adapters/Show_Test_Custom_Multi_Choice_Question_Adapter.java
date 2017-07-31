package Model.Student.Show_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.plan9.naseemdev.naseem.R;
import com.plan9.naseemdev.naseem.ShowTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import BussinessObjects.Show_Test_BO.Marks;
import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;

/**
 * Created by Muhammad Taimoor on 7/11/2017.
 */

public class Show_Test_Custom_Multi_Choice_Question_Adapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<MultipleChoiceQuestions> multipleChoiceQuestions;
    private ShowTest showTest;
    public Show_Test_Custom_Multi_Choice_Question_Adapter(Context c, ArrayList<MultipleChoiceQuestions> multi, ShowTest show){
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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        MultipleChoiceQuestions mcq = (MultipleChoiceQuestions) getGroup(i);
        if(view ==  null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_choice_questions, null);
            //LayoutInflater inflater = (LayoutInflater)this.context.getSystemService()
        }
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView marks = (TextView)view.findViewById(R.id.marks);
        TextView answer = (TextView)view.findViewById(R.id.answer);

        title.setText("Q: "+mcq.getTitle());
        ArrayList<String> std_answer = mcq.getStd_answer();
        ArrayList<Multi_Choice_Options> options = mcq.getOptions();
        String correct = "";
        boolean flag = true;
        Set<Integer> set_answer = new HashSet<Integer>();
        for (int j=0; j<options.size(); j++){
            if(options.get(j).isCorrect()){
                correct = correct + options.get(j).getOption() + ", ";
                set_answer.add(options.get(j).getId());
            }
        }
        correct = correct.substring(0, correct.length()-2);
        answer.setText("Correct: " + correct);

        Marks m = new Marks();
        m.setId(mcq.getId());
        m.setTotal(mcq.getMarks());
        if(set_answer.size() == std_answer.size()){
            for(int k=0; k<set_answer.size(); k++){
                if(set_answer.contains(Integer.parseInt(std_answer.get(k)))){
                    flag = true;
                }
                else {
                    flag = false;
                }
            }
        }
        else {
            flag = false;
        }

        if(flag){
            m.setObtained(mcq.getMarks());
            marks.setText(mcq.getMarks()+"/"+mcq.getMarks());

        }
        else {
            m.setObtained(0);
            marks.setText("0/"+mcq.getMarks());
        }
        showTest.setMultiChoiceQuestionSMarks(m);
        return  view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Multi_Choice_Options mco = (Multi_Choice_Options)getChild(i, i1);
        MultipleChoiceQuestions mcq = (MultipleChoiceQuestions) getGroup(i);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_choice_options, null);
        }

        CheckBox box = (CheckBox)view.findViewById(R.id.option);
        ArrayList<String> answer = mcq.getStd_answer();
        for(int j=0; j<answer.size(); j++){
            if(mco.getId() == Integer.parseInt(answer.get(j)))
                box.setChecked(true);
        }
        box.setText(mco.getOption());
        box.setEnabled(false);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
