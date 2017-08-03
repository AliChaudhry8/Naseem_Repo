package Model.Parent;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.plan9.naseemdev.naseem.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import BussinessObjects.Parent_Test_BO;

/**
 * Created by Muhammad Taimoor on 8/2/2017.
 */

public class Parent_Adapter extends  RecyclerView.Adapter<Parent_Adapter.MyViewHolder>{

    private Context context;
    private ArrayList<Parent_Test_BO> test;
    private RecyclerView recyclerView;
    private Activity activity;

    public Parent_Adapter(Context c, ArrayList<Parent_Test_BO> t, RecyclerView r, Activity a){
        context = c;
        test = t;
        recyclerView = r;
        activity = a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_parent_student_test, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Parent_Test_BO test_bo = test.get(position);
                Toast.makeText(context, "Test Name: " + test_bo.getName() + " Taken: " + test_bo.getTaken(), Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Parent_Test_BO test_bo = test.get(position);
        holder.test_name.setText("" + test_bo.getName());
    }

    @Override
    public int getItemCount() {
        return test.size();
    }

    public void addTest(Parent_Test_BO t){
        test.add(t);
        Set<Parent_Test_BO> pp = new HashSet<Parent_Test_BO>();
        pp.addAll(test);
        test.clear();
        test.addAll(pp);
        notifyDataSetChanged();
    }
    public void clear(){
        test.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView test_name;
        public MyViewHolder(View item){
            super(item);
            test_name = (TextView)item.findViewById(R.id.test_name);
        }
    }
}
