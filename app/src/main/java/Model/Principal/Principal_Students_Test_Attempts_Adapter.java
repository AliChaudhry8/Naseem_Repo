package Model.Principal;

import android.app.Activity;
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
import BussinessObjects.Principal_Students_Test_Attempts_BO;
import Model.Constants;

/**
 * Created by Muhammad Taimoor on 8/9/2017.
 */

public class Principal_Students_Test_Attempts_Adapter extends RecyclerView.Adapter<Principal_Students_Test_Attempts_Adapter.MyViewHolder>{
    private Context context;
    private ArrayList<Principal_Students_Test_Attempts_BO> attempts;
    private RecyclerView recyclerView;

    public Principal_Students_Test_Attempts_Adapter(Context c, ArrayList<Principal_Students_Test_Attempts_BO> u, RecyclerView rv){
        context = c;
        recyclerView = rv;
        attempts = u;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_principal_students_attempts, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Principal_Students_Test_Attempts_BO attempts_bo = attempts.get(position);
                if(attempts_bo.getTaken() == 1){
                    Toast.makeText(context, "I will take you to new Activity", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast toast = Toast.makeText(context, attempts_bo.getFirst_name() + " " + attempts_bo.getSecond_name() + Constants.No_Submission_From_Student, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Principal_Students_Test_Attempts_BO attempts_bo = attempts.get(position);
        holder.first_name.setText(attempts_bo.getFirst_name());
        holder.second_name.setText(attempts_bo.getSecond_name());
        if(attempts_bo.getTaken() == 1)
            holder.attempt.setText("View Submission");
        else
            holder.attempt.setText("No Submission");
    }

    @Override
    public int getItemCount() {
        return attempts.size();
    }

    public void addAttempt(Principal_Students_Test_Attempts_BO attempts_bo){
        attempts.add(attempts_bo);
        notifyDataSetChanged();
    }

    public void clearAttempts(){
        attempts.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView first_name;
        public TextView second_name;
        public TextView attempt;
        public MyViewHolder(View item) {
            super(item);
            first_name = (TextView)item.findViewById(R.id.p_student_first_name);
            second_name = (TextView)item.findViewById(R.id.p_student_second_name);
            attempt = (TextView)item.findViewById(R.id.p_student_attempt);
        }
    }
}
