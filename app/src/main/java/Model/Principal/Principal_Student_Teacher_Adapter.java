package Model.Principal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.User_BO;
import static android.view.View.GONE;

/**
 * Created by Muhammad Taimoor on 7/25/2017.
 */

public class Principal_Student_Teacher_Adapter extends RecyclerView.Adapter<Principal_Student_Teacher_Adapter.MyViewHolder>{
    private Context context;
    private ArrayList<User_BO> users;
    private RecyclerView recyclerView;
    private Activity activity;

    public Principal_Student_Teacher_Adapter(Context c, ArrayList<User_BO> u, RecyclerView rv, Activity a){
        context = c;
        recyclerView = rv;
        users = u;
        activity = a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_principal_students_teachers, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                User_BO u = users.get(position);
                Custom_Dialog dialog = new Custom_Dialog(activity, u);
                dialog.show();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User_BO u = users.get(position);
        holder.profile_pic.setVisibility(View.INVISIBLE);
        if(u.getAvatar().equals("") || u.getAvatar().equals(null) || u.getAvatar().equals("null")){
            try{
                Glide.with(context).clear(holder.profile_pic);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.temp_pic);
                RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                img.setCornerRadius(50);
                img.setCircular(true);
                holder.profile_pic.setImageDrawable(img);
                holder.progress.setVisibility(GONE);
                holder.profile_pic.setVisibility(View.VISIBLE);
            }catch (Exception e){
                holder.profile_pic.setImageDrawable(context.getResources().getDrawable(R.drawable.temp_pic));
                holder.progress.setVisibility(GONE);
                holder.profile_pic.setVisibility(View.VISIBLE);
            }
        }
        else {
            Glide.with(context).asBitmap().load(u.getAvatar()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    img.setCornerRadius(50);
                    img.setCircular(true);
                    holder.profile_pic.setImageDrawable(img);
                    holder.progress.setVisibility(GONE);
                    holder.profile_pic.setVisibility(View.VISIBLE);
                }
            });
        }
        holder.username.setText(u.getUsername());
        holder.school_name.setText(u.getSchool_name());
        holder.section_name.setText(u.getSection_name());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addUser(User_BO u){
        users.add(u);
        notifyDataSetChanged();
    }

    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile_pic;
        public TextView username;
        public TextView school_name;
        public TextView section_name;
        public ProgressBar progress;

        public MyViewHolder(View item){
            super(item);
            profile_pic = (ImageView)item.findViewById(R.id.principal_student_teacher_image);
            progress = (ProgressBar)item.findViewById(R.id.principal_student_teacher_progress);
            username = (TextView)item.findViewById(R.id.principal_student_teacher_username);
            school_name = (TextView)item.findViewById(R.id.principal_student_teacher_school_name);
            section_name = (TextView)item.findViewById(R.id.principal_student_teacher_section_name);
        }
    }
}
