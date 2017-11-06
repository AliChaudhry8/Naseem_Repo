package Model.Student.Show_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.ShowTest;
import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Marks;
import BussinessObjects.Show_Test_BO.ParagraphQuestions;

/**
 * Created by Muhammad Taimoor on 7/12/2017.
 */

public class  Show_Test_Custom_Paragraph_Questions_Adapter extends ArrayAdapter<ParagraphQuestions> {

    private Context context;
    private ArrayList<ParagraphQuestions> paragraphQuestions;
    private ShowTest showTest;

    public Show_Test_Custom_Paragraph_Questions_Adapter(Context c, ArrayList<ParagraphQuestions> questions, ShowTest show){
        super(c, 0, questions);
        context = c;
        paragraphQuestions = questions;
        showTest = show;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private EditText std_answer;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        //View superView = super.getView(position, convertView, parent);
        ParagraphQuestions questions = getItem(position);
        ViewHolder v = null;
        try {
            if (convertView == null) {
                v = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_paragraph_question, parent, false);
                v.title = (TextView)convertView.findViewById(R.id.title);
                v.marks = (TextView)convertView.findViewById(R.id.marks);
                v.std_answer = (EditText) convertView.findViewById(R.id.std_answer);
                convertView.setTag(v);
            }
            else {
                v = (ViewHolder) convertView.getTag();
            }
            v.title.setText("Q: "+questions.getTitle());
            v.std_answer.setText("Ans: "+ questions.getAnswer().getAnswer());
            Marks m = new Marks();
            m.setId(questions.getId());
            m.setTotal(questions.getMarks());
            if(questions.getAnswer().getMarks() == -1) {
                v.marks.setText("-/" + questions.getMarks());
                m.setObtained(0);
            }
            else {
                v.marks.setText(questions.getAnswer().getMarks() + "/" + questions.getMarks());
                m.setObtained(questions.getAnswer().getMarks());
            }
            showTest.setParagraphQuestionsMarks(m);
            v.std_answer.setEnabled(false);
            return convertView;
        }catch (Exception e) {
            return null;
        }
    }
}
