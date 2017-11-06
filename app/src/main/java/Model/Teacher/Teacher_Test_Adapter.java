package Model.Teacher;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Test_BO;

/**
 * Created by DELL on 9/20/2017.
 */
public class Teacher_Test_Adapter extends RecyclerView.Adapter<Model.Teacher.Teacher_Test_Adapter.MyViewHolder1>{
    private Context context;
    private ArrayList<Test_BO> tests;
    private RecyclerView recyclerView;
    private Activity activity;

    public Teacher_Test_Adapter(Context c, ArrayList<Test_BO> u, RecyclerView rv, Activity a){
        context = c;
        recyclerView = rv;
        tests = u;
        activity = a;
    }

    @Override
    public Model.Teacher.Teacher_Test_Adapter.MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_teacher_test, parent, false);
        Model.Teacher.Teacher_Test_Adapter.MyViewHolder1 myViewHolder = new Model.Teacher.Teacher_Test_Adapter.MyViewHolder1(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Test_BO u = tests.get(position);
                Custom_Teacher_Test_Dialog dialog = new Custom_Teacher_Test_Dialog(activity, u);
                dialog.show();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final Model.Teacher.Teacher_Test_Adapter.MyViewHolder1 holder, int position) {
        Test_BO u = tests.get(position);
        holder.testname.setText(u.getName());
    }


    @Override
    public int getItemCount() {
        return tests.size();
    }

    public void addUser(Test_BO u){
        tests.add(u);
        notifyDataSetChanged();
    }

    public void clear(){
        tests.clear();
        notifyDataSetChanged();
    }
    class MyViewHolder1 extends RecyclerView.ViewHolder{
        public TextView testname;

        public MyViewHolder1(View item){
            super(item);
            testname = (TextView)item.findViewById(R.id.teacher_test_name);
        }
    }
}
