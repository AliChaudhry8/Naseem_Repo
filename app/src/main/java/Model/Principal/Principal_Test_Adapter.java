package Model.Principal;

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
import BussinessObjects.Principal_Test_BO;

/**
 * Created by Muhammad Taimoor on 8/3/2017.
 */

public class Principal_Test_Adapter extends RecyclerView.Adapter<Principal_Test_Adapter.MyViewHolder>{
    private Context context;
    private ArrayList<Principal_Test_BO> tests;
    private RecyclerView recyclerView;

    public Principal_Test_Adapter(Context c, ArrayList<Principal_Test_BO> p, RecyclerView r){
        context = c;
        tests = p;
        recyclerView = r;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_principal_test, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Principal_Test_BO test = tests.get(position);
                Toast.makeText(context, "Position: " + position + " Test Name: " + test.getName(), Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Principal_Test_BO test = tests.get(position);
        holder.test_name.setText(test.getName());
        holder.teacher_name.setText("");
        if(test.getStart_time().equals("") || test.getStart_time().equals(null) || test.getStart_time().equals("null"))
            holder.start_time.setText("");
        else {
            String []startDateTime = test.getStart_time().split("T");
            String [] testTime = startDateTime[1].split(":");
            int testhour = Integer.parseInt(testTime[0]);
            int testminute = Integer.parseInt(testTime[1]);
            //holder.start_time.setText(test.getStart_time());
            holder.start_time.setText(startDateTime[0] + " " + testhour + ":"+  testminute +"");
        }
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }
    public void addTest(Principal_Test_BO test){
        tests.add(test);
        Set<Principal_Test_BO> set = new HashSet<Principal_Test_BO>();
        set.addAll(tests);
        tests.clear();
        tests.addAll(set);
        notifyDataSetChanged();
    }

    public void clear(){
        tests.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView test_name, teacher_name, start_time;
        public MyViewHolder(View item){
            super(item);
            test_name = (TextView)item.findViewById(R.id.p_test_name);
            teacher_name = (TextView)item.findViewById(R.id.p_teacher_name);
            start_time = (TextView)item.findViewById(R.id.p_start_time);
        }
    }
}
