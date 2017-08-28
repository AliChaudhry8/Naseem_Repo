package Model.Student.Start_Test_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Text_Questions;

/**
 * Created by Muhammad Taimoor on 8/21/2017.
 */

public class Take_Test_Text_Question_Adapter extends RecyclerView.Adapter<Take_Test_Text_Question_Adapter.MyViewHolder>{
    private Context context;
    private ArrayList<Text_Questions> text_questions;

    public Take_Test_Text_Question_Adapter(Context c, ArrayList<Text_Questions> test) {
        context = c;
        text_questions = test;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text_questions, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Text_Questions question = text_questions.get(position);
        holder.title.setText("Q: "+question.getTitle());
        holder.answer.setVisibility(View.GONE);
        holder.marks.setText("" + question.getMarks());
    }

    @Override
    public int getItemCount() {
        return text_questions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView marks;
        private EditText std_answer;
        private TextView answer;

        public MyViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            marks = (TextView)view.findViewById(R.id.marks);
            answer = (TextView)view.findViewById(R.id.answer);
            std_answer = (EditText)view.findViewById(R.id.std_answer);
        }
    }
}
/*extends ArrayAdapter<Text_Questions> {
    private Context context;
    private ArrayList<Text_Questions> text_questions;

    public Take_Test_Text_Question_Adapter(Context c, ArrayList<Text_Questions> test) {
        super(c, 0, test);
        context = c;
        text_questions = test;
    }

    private class ViewHolder {
        private TextView title;
        private TextView marks;
        private EditText std_answer;
        private TextView answer;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
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
            //v.std_answer.setEnabled(true);
            v.answer.setVisibility(View.GONE);
            v.marks.setText("" + questions.getMarks());

            return convertView;
        }catch (Exception e) {
            return null;
        }
    }
}
*/