package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Text_Question;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Text_Questions;
import edu.plan9.naseemdev.naseem.TakeTest;

/**
 * Created by Muhammad Taimoor on 8/21/2017.
 */

public class Take_Test_Text_Question_Adapter extends ArrayAdapter<Text_Questions> {

    private Context context;
    private ArrayList<Text_Questions> text_questions;
    private TakeTest showTest;
    int currentlyFocusdRow;
    int lastFocus;
    int mposition = -1;
    ListView list_text_questions;
    int checkMeta = 0;
    Take_Test_Text_Question_Adapter.ViewHolder v;

    public Take_Test_Text_Question_Adapter(Context c, ArrayList<Text_Questions> test, TakeTest show ){
        super(c, 0, test);
        context = c;
        text_questions = test;
        showTest = show;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private Button answer;
        private EditText std_answer;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Text_Questions questions = getItem(position);
        try {
            if (convertView == null) {
                v = new Take_Test_Text_Question_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_text_questions, parent, false);
                v.title = (TextView) convertView.findViewById(R.id.title);
                v.marks = (TextView) convertView.findViewById(R.id.marks);
                v.answer = (Button)convertView.findViewById(R.id.answer_text_question);
                v.std_answer = (EditText)convertView.findViewById(R.id.std_answer);

                convertView.setTag(v);
            }
            else {
                v = (Take_Test_Text_Question_Adapter.ViewHolder) convertView.getTag();
            }
            v.title.setText("Q: "+questions.getTitle());
            v.marks.setText(String.valueOf(questions.getMarks()));

            if(mposition == position){
                text_questions.get(position).setStd_answer(v.std_answer.getText().toString());
                notifyDataSetChanged();
            }
            v.answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v1) {
                    mposition = position;
                    Toast.makeText(showTest.getApplicationContext() , "Text Answer is Added" , Toast.LENGTH_LONG).show();
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }catch (Exception e) {
            return convertView;
        }
    }


}
