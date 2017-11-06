package Model.Student.Show_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.ShowTest;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.BooleanQuestions;
import BussinessObjects.Show_Test_BO.Marks;

/**
 * Created by Muhammad Taimoor on 7/12/2017.
 */

public class Show_Test_Custom_Boolean_Questions_Adapter extends ArrayAdapter<BooleanQuestions> {
    private Context context;
    private ArrayList<BooleanQuestions> booleanQuestions;
    private ShowTest showTest;

    public Show_Test_Custom_Boolean_Questions_Adapter(Context c, ArrayList<BooleanQuestions> questions, ShowTest show){
        super(c, 0, questions);
        context = c;
        booleanQuestions = questions;
        showTest = show;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private TextView correct;
        private RadioButton option_true;
        private RadioButton option_false;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //View superView = super.getView(position, convertView, parent);
        BooleanQuestions questions = getItem(position);
        ViewHolder v = null;
        try {
            if (convertView == null) {
                v = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_boolean_question, parent, false);
                v.title = (TextView)convertView.findViewById(R.id.title);
                v.marks = (TextView)convertView.findViewById(R.id.marks);
                v.correct = (TextView)convertView.findViewById(R.id.correct);
                v.option_true = (RadioButton)convertView.findViewById(R.id.bool_true);
                v.option_false = (RadioButton)convertView.findViewById(R.id.bool_false);
                convertView.setTag(v);
            }
            else {
                v = (ViewHolder) convertView.getTag();
            }
            v.title.setText("Q: "+questions.getTitle());
            v.correct.setText("Correct: "+ String.valueOf(questions.isCorrect()).toUpperCase());
            Marks m = new Marks();
            m.setId(questions.getId());
            m.setTotal(questions.getMarks());

            if(questions.isStd_answer())
                v.option_true.setChecked(true);
            else
                v.option_false.setChecked(true);

            v.option_false.setEnabled(false);
            v.option_true.setEnabled(false);

            if(questions.isStd_answer() == questions.isCorrect()){
                m.setObtained(questions.getMarks());
                v.marks.setText(questions.getMarks() + "/" + questions.getMarks());
            }
            else{
                m.setObtained(0);
                v.marks.setText("0/"+questions.getMarks());
            }
            showTest.setBooleanQuestionsMarks(m);
            return convertView;
        }catch (Exception e) {
            return null;
        }
    }
}
