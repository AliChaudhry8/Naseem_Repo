package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.BooleanQuestions;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Boolean_Questions;
import Model.Teacher.New_Test_Content.New_Test_Custom_Boolean_Questions_Adapter;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.TakeTest;

/**
 * Created by DELL on 10/22/2017.
 */

public class Take_Test_Boolean_Question_Adapter extends ArrayAdapter<BooleanQuestions> {
    private Context context;
    private ArrayList<BooleanQuestions> booleanQuestions;
    private TakeTest showTest;
    int mPosition = -1;

    public Take_Test_Boolean_Question_Adapter(Context c, ArrayList<BooleanQuestions> questions, TakeTest show){
        super(c, 0, questions);
        context = c;
        booleanQuestions = questions;
        showTest = show;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private TextView correct;
        private Button answer;
        private RadioGroup bool_group;
        private RadioButton bool_button;
        private RadioButton bool_true;
        private RadioButton bool_false;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        //View superView = super.getView(position, convertView, parent);
        final BooleanQuestions questions = getItem(position);
        Take_Test_Boolean_Question_Adapter.ViewHolder v = null;
        try {
            if (convertView == null) {
                v = new Take_Test_Boolean_Question_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_boolean_question, parent, false);
                v.title = (TextView) convertView.findViewById(R.id.title);
                v.marks = (TextView) convertView.findViewById(R.id.marks);
                v.correct = (TextView)convertView.findViewById(R.id.correct);
                v.bool_group = (RadioGroup)convertView.findViewById(R.id.bool_group);
                v.bool_true = (RadioButton)convertView.findViewById(R.id.bool_true);
                v.bool_false = (RadioButton)convertView.findViewById(R.id.bool_false);
                v.answer = (Button)convertView.findViewById(R.id.answer_boolean_question);

                convertView.setTag(v);
            }
            else {
                v = (Take_Test_Boolean_Question_Adapter.ViewHolder) convertView.getTag();
            }
            v.correct.setVisibility(View.GONE);
            v.title.setText("Q: "+questions.getTitle());
            v.marks.setText(String.valueOf(questions.getMarks()));

            if(mPosition == position){
                int id = v.bool_group.getCheckedRadioButtonId();
                v.bool_button = (RadioButton)convertView.findViewById(id);
                if(v.bool_button.getText().toString().equals("True")){
                    booleanQuestions.get(position).setStd_answer(true);
                    notifyDataSetChanged();
                }
                else{
                    booleanQuestions.get(position).setStd_answer(false);
                    notifyDataSetChanged();
                }

            }
            v.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosition = position;
                    Toast.makeText(showTest.getApplicationContext() , "Boolean Answer is Added" , Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
            });
            //showTest.setBooleanQuestionsMarks(m);
            return convertView;
        }catch (Exception e) {
            return convertView;
        }
    }
}
