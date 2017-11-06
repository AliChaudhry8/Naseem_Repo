package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import edu.plan9.naseemdev.naseem.R;
import java.util.ArrayList;
import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;
import edu.plan9.naseemdev.naseem.TakeTest;

/**
 * Created by Muhammad Taimoor on 8/21/2017.
 */

public class Take_Test_Single_Choice_Questions extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<SingleChoiceQuestions> singleChoiceQuestions;
    TakeTest takeTest;
    CheckBox rb;
    int mPosition = -1;

    public Take_Test_Single_Choice_Questions(Context c, ArrayList<SingleChoiceQuestions> questions , TakeTest takeTest){
        context = c;
        singleChoiceQuestions = questions;
        this.takeTest = takeTest;
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
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        SingleChoiceQuestions scq = (SingleChoiceQuestions) getGroup(i);
        if(view ==  null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_single_choice_question, null);
        }
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView marks = (TextView)view.findViewById(R.id.marks);
        TextView answer = (TextView)view.findViewById(R.id.answer);

        title.setText("Q: "+scq.getTitle());
        marks.setText(""+scq.getMarks());
        answer.setVisibility(View.GONE);


        return view;
    }
    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final Single_Choice_Options sco = (Single_Choice_Options)getChild(i, i1);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_single_choice_options, null);
        }
        rb = (CheckBox) view.findViewById(R.id.option);

        //rb.setChecked(false);
        rb.setText(""+sco.getOption());
        //rb.setChecked(true);
        rb.setChecked(sco.isChecked());
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rb.isChecked()){
                    int j = singleChoiceQuestions.get(i).getOptionselected();
                    if(j >= 0){
                        singleChoiceQuestions.get(i).getOptions().get(j).setChecked(false);
                        singleChoiceQuestions.get(i).setOptionselected(i1);
                        singleChoiceQuestions.get(i).setStd_answer(i1);
                        sco.setChecked(true);
                        notifyDataSetChanged();
                    }
                    else{
                        singleChoiceQuestions.get(i).setOptionselected(i1);
                        singleChoiceQuestions.get(i).setStd_answer(i1);
                        sco.setChecked(true);
                        notifyDataSetChanged();
                    }

                }
                else{
                    if(sco.isChecked()){
                        singleChoiceQuestions.get(i).setOptionselected(-1);
                        singleChoiceQuestions.get(i).setStd_answer(-1);
                        sco.setChecked(false);
                        notifyDataSetChanged();
                    }
                    else {
                        int j = singleChoiceQuestions.get(i).getOptionselected();
                        if (j >= 0) {
                            singleChoiceQuestions.get(i).getOptions().get(j).setChecked(false);
                            singleChoiceQuestions.get(i).setOptionselected(i1);
                            singleChoiceQuestions.get(i).setStd_answer(i1);
                            sco.setChecked(true);
                            notifyDataSetChanged();
                        } else {
                            singleChoiceQuestions.get(i).setOptionselected(i1);
                            singleChoiceQuestions.get(i).setStd_answer(i1);
                            sco.setChecked(true);
                            notifyDataSetChanged();
                        }
                    }
                }
               /* if(rb.isChecked()){
                    if(sco.isChecked() == false){
                        sco.setChecked(true);
                        for(int j = 0 ; j < singleChoiceQuestions.get(i).getOptions().size() ; j++){
                            if(singleChoiceQuestions.get(i).getOptions().get(j).equals(sco)){
                                sco.setChecked(true);
                            }
                            else{
                                sco.setChecked(false);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
                else{
                    if(sco.isChecked()){
                        sco.setChecked(false);
                        notifyDataSetChanged();
                    }
                    else{
                        sco.setChecked(true);
                        for(int j = 0 ; j < singleChoiceQuestions.get(i).getOptions().size() ; j++){
                            if(singleChoiceQuestions.get(i).getOptions().get(j).equals(sco)){
                                sco.setChecked(true);
                            }
                            else{
                                sco.setChecked(false);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }*/
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
