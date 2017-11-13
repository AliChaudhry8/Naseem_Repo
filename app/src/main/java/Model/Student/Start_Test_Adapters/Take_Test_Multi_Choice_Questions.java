package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Multi_Choice_Options;
import BussinessObjects.Show_Test_BO.MultipleChoiceQuestions;
import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.TakeTest;

/**
 * Created by DELL on 10/22/2017.
 */

public class Take_Test_Multi_Choice_Questions extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<MultipleChoiceQuestions> multiChoiceQuestions;
    TakeTest takeTest;
    CheckBox rb;

    public Take_Test_Multi_Choice_Questions(Context c, ArrayList<MultipleChoiceQuestions> questions , TakeTest takeTest){
        context = c;
        multiChoiceQuestions = questions;
        this.takeTest = takeTest;
    }
    @Override
    public int getGroupCount() {
        return multiChoiceQuestions.size();
    }
    @Override
    public int getChildrenCount(int i) {
        return multiChoiceQuestions.get(i).getOptions().size();
    }

    @Override
    public Object getGroup(int i) {
        return multiChoiceQuestions.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return multiChoiceQuestions.get(i).getOptions().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return multiChoiceQuestions.get(i).getId();
    }

    @Override
    public long getChildId(int i, int i1) {
        return multiChoiceQuestions.get(i).getOptions().get(i1).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        MultipleChoiceQuestions mcq = (MultipleChoiceQuestions) getGroup(i);
        if(view ==  null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_choice_questions, null);
        }
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView marks = (TextView)view.findViewById(R.id.marks);
        TextView answer = (TextView)view.findViewById(R.id.answer);

        title.setText("Q: "+mcq.getTitle());
        marks.setText(""+mcq.getMarks());
        answer.setVisibility(View.GONE);
        return view;
    }
    @Override
    public View getChildView(final int i,final int i1, boolean b, View view, ViewGroup viewGroup) {
        final Multi_Choice_Options mco = (Multi_Choice_Options)getChild(i, i1);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_multi_choice_options, null);
        }
        rb = (CheckBox) view.findViewById(R.id.option);
        rb.setText(""+mco.getOption());
        rb.setChecked(mco.isChecked());

        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb.isChecked()){
                    if(mco.isChecked()){
                        mco.setChecked(false);
                        multiChoiceQuestions.get(i).getStd_answer().remove(mco.getId());
                        notifyDataSetChanged();
                    }
                    else{
                        mco.setChecked(true);
                        multiChoiceQuestions.get(i).getStd_answer().add(""+(mco.getId()));
                        notifyDataSetChanged();
                    }
                }
                else {
                    if(mco.isChecked()){
                        mco.setChecked(false);
                        multiChoiceQuestions.get(i).getStd_answer().remove(mco.getId());
                        notifyDataSetChanged();
                    }
                    else{
                        mco.setChecked(true);
                        try {

                            multiChoiceQuestions.get(i).getStd_answer().add(""+(mco.getId()));

                        }catch (Exception e){
                            e.toString();
                        }
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

}