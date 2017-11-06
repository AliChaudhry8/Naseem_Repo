package Model.Groups;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Group_BO;

/**
 * Created by DELL on 9/20/2017.
 */

public class Groups_Adapter extends RecyclerView.Adapter<Model.Groups.Groups_Adapter.MyViewHolder1>{
    private Context context;
    private ArrayList<Group_BO> tests;
    private RecyclerView recyclerView;
    private Activity activity;

    public Groups_Adapter(Context c, ArrayList<Group_BO> u, RecyclerView rv, Activity a){
        context = c;
        recyclerView = rv;
        tests = u;
        activity = a;
    }

    @Override
    public Model.Groups.Groups_Adapter.MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_groups, parent, false);
        Model.Groups.Groups_Adapter.MyViewHolder1 myViewHolder = new Model.Groups.Groups_Adapter.MyViewHolder1(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Group_BO u = tests.get(position);

                Custom_Dialog_Group dialog = new Custom_Dialog_Group(activity, u);
                dialog.show();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final Model.Groups.Groups_Adapter.MyViewHolder1 holder, int position) {
        Group_BO u = tests.get(position);
        holder.groupname.setText(u.getName());
    }


    @Override
    public int getItemCount() {
        return tests.size();
    }

    public void addUser(Group_BO u){
        tests.add(u);
        notifyDataSetChanged();
    }

    public void clear(){
        tests.clear();
        notifyDataSetChanged();
    }
    class MyViewHolder1 extends RecyclerView.ViewHolder{
        public TextView groupname;

        public MyViewHolder1(View item){
            super(item);
            groupname = (TextView)item.findViewById(R.id.group_name);
        }
    }
}

