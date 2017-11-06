package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.ParagraphQuestions;
import BussinessObjects.Show_Test_BO.Text_Questions;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Paragraph_Question;
import Model.Teacher.New_Test_Content.New_Test_Custom_Paragraph_Question_Adapter;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.TakeTest;

/**
 * Created by DELL on 10/22/2017.
 */

public class Take_Test_Paragraph_Question_Adapter extends ArrayAdapter<ParagraphQuestions> {

    private Context context;
    private ArrayList<ParagraphQuestions> paragraphQuestions;
    private TakeTest showTest;
    private int mPosition = -1;

    public Take_Test_Paragraph_Question_Adapter(Context c, ArrayList<ParagraphQuestions> questions, TakeTest show){
        super(c, 0, questions);
        context = c;
        paragraphQuestions = questions;
        showTest = show;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private EditText std_answer;
        private Button answer;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        //View superView = super.getView(position, convertView, parent);
        final ParagraphQuestions questions = getItem(position);
        Take_Test_Paragraph_Question_Adapter.ViewHolder v = null;
        try {
            if (convertView == null) {
                v = new Take_Test_Paragraph_Question_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_paragraph_question, parent, false);
                v.title = (TextView) convertView.findViewById(R.id.title);
                v.marks = (TextView) convertView.findViewById(R.id.marks);
                v.std_answer = (EditText)convertView.findViewById(R.id.std_answer);
                v.answer = (Button)convertView.findViewById(R.id.answer_paragraph_question);
                convertView.setTag(v);
            }
            else {
                v = (Take_Test_Paragraph_Question_Adapter.ViewHolder) convertView.getTag();
            }
            v.title.setText("Q: "+questions.getTitle());
            //v.std_answer.setText(""+questions.getStu_answer());
            v.marks.setText(""+questions.getMarks());

            if(mPosition == position){
               // paragraphQuestions.get(position).set
                paragraphQuestions.get(position).setStu_answer(v.std_answer.getText().toString());
                notifyDataSetChanged();
            }
            v.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosition = position;
                    Toast.makeText(showTest.getApplicationContext() , "Paragraph Answer is Added" , Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
            });

            //showTest.setParagraphQuestionsMarks(m);
            return convertView;
        }catch (Exception e) {
            return convertView;
        }
    }
}

