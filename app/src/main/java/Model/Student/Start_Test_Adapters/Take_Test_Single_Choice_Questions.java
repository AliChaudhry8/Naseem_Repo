package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.plan9.naseemdev.naseem.R;
import java.util.ArrayList;
import BussinessObjects.Show_Test_BO.SingleChoiceQuestions;
import BussinessObjects.Show_Test_BO.Single_Choice_Options;

/**
 * Created by Muhammad Taimoor on 8/21/2017.
 */

public class Take_Test_Single_Choice_Questions extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<SingleChoiceQuestions> singleChoiceQuestions;

    public Take_Test_Single_Choice_Questions(Context c, ArrayList<SingleChoiceQuestions> questions){
        context = c;
        singleChoiceQuestions = questions;
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
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Single_Choice_Options sco = (Single_Choice_Options)getChild(i, i1);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_single_choice_options, null);
        }
        RadioButton rb = (RadioButton)view.findViewById(R.id.option);
        rb.setChecked(false);
        rb.setText(""+sco.getOption());
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
