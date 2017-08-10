package Model.Principal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.plan9.naseemdev.naseem.R;
import java.util.ArrayList;
import BussinessObjects.Principal_Test_BO;
import Model.Constants;

/**
 * Created by Muhammad Taimoor on 8/10/2017.
 */

public class Principal_Student_Teacher_Test_List_Adapter extends RecyclerView.Adapter<Principal_Student_Teacher_Test_List_Adapter.MyViewHolder>{
    private Context context;
    private ArrayList<Principal_Test_BO> test_list;
    private RecyclerView recyclerView;
    private String role;
    public Principal_Student_Teacher_Test_List_Adapter(Context c, ArrayList<Principal_Test_BO> u, RecyclerView rv, String r){
        context = c;
        recyclerView = rv;
        test_list = u;
        role = r;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_principal_student_teacher_tests_list, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Principal_Test_BO test = test_list.get(position);
                if(test.getStatus() == 1){
                    Toast.makeText(context, "I will show you his test", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast toast = Toast.makeText(context, Constants.No_Submission_From_Student_1, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Principal_Test_BO test = test_list.get(position);
        holder.test_name.setText(test.getName());
        if(role.equals("Student")) {
            holder.teacher_name.setText(test.getTeacher_name());
            if (test.getStatus() == 1) {
                holder.test_attempt.setText("View Submission");
            } else {
                holder.test_attempt.setText("No Submission");
            }
        }
    }

    @Override
    public int getItemCount() {
        return test_list.size();
    }

    public void addTest(Principal_Test_BO test){
        test_list.add(test);
        notifyDataSetChanged();
    }
    public void clearTest(){
        test_list.clear();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView label_teacher_name, label_test_attempt, teacher_name, test_name, test_attempt, teacher_label;
        public MyViewHolder(View item){
            super(item);
            label_teacher_name = (TextView)item.findViewById(R.id.label_teacher_name);
            label_test_attempt = (TextView)item.findViewById(R.id.label_test_attempt);
            teacher_name = (TextView)item.findViewById(R.id.p_teacher_name);
            test_name = (TextView)item.findViewById(R.id.p_test_name);
            test_attempt = (TextView)item.findViewById(R.id.p_test_attempt);
            teacher_label = (TextView)item.findViewById(R.id.p_teacher_label);
        }
    }
}
