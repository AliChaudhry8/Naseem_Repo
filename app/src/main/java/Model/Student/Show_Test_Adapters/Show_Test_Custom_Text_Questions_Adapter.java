package Model.Student.Show_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.plan9.naseemdev.naseem.R;
import com.plan9.naseemdev.naseem.ShowTest;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Marks;
import BussinessObjects.Show_Test_BO.Text_Questions;

/**
 * Created by Muhammad Taimoor on 7/4/2017.
 */

public class Show_Test_Custom_Text_Questions_Adapter extends ArrayAdapter<Text_Questions> {

    private Context context;
    private ArrayList<Text_Questions> text_questions;
    private ShowTest showTest;

    public Show_Test_Custom_Text_Questions_Adapter(Context c, ArrayList<Text_Questions> test, ShowTest show){
        super(c, 0, test);
        context = c;
        text_questions = test;
        showTest = show;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private EditText std_answer;
        private TextView answer;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //View superView = super.getView(position, convertView, parent);
        Text_Questions questions = getItem(position);
        ViewHolder v = null;
        try {
            if (convertView == null) {
                v = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_text_questions, parent, false);
                v.title = (TextView)convertView.findViewById(R.id.title);
                v.marks = (TextView)convertView.findViewById(R.id.marks);
                v.answer = (TextView)convertView.findViewById(R.id.answer);
                v.std_answer = (EditText) convertView.findViewById(R.id.std_answer);
                convertView.setTag(v);
            }
            else {
                v = (ViewHolder) convertView.getTag();
            }
            v.title.setText("Q: "+questions.getTitle());
            v.std_answer.setText("Ans: "+ questions.getStd_answer());
            v.answer.setText("Correct: " + questions.getAnswer());
            if(questions.getAnswer().toLowerCase().equals(questions.getStd_answer().toLowerCase())){
                v.marks.setText(""+questions.getMarks() + "/" + questions.getMarks());
                Marks m = new Marks(questions.getId(), questions.getMarks(), questions.getMarks());
                showTest.setTextQuestionsMarks(m);
            }else{
                v.marks.setText("0/" + questions.getMarks());
                Marks m = new Marks(questions.getId(), questions.getMarks(), 0);
                showTest.setTextQuestionsMarks(m);
            }
            v.std_answer.setEnabled(false);
            return convertView;
        }catch (Exception e) {
            return null;
        }
    }
}
