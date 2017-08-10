package Model.Principal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.plan9.naseemdev.naseem.R;
import com.plan9.naseemdev.naseem.Student_Teacher_Profile;
import com.plan9.naseemdev.naseem.TeacherStudentTestList;

import BussinessObjects.User_BO;

/**
 * Created by Muhammad Taimoor on 7/28/2017.
 */

public class Custom_Dialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Dialog dialog;
    private Button profile, test, cancel;
    private User_BO user;
    private TextView name;

    public Custom_Dialog(Activity a, User_BO u){
        super(a);
        activity = a;
        user = u;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_student_teacher);
        profile = (Button)findViewById(R.id.dialog_profile);
        test = (Button)findViewById(R.id.dialog_test);
        cancel = (Button)findViewById(R.id.dialog_cancel);
        profile.setOnClickListener(this);
        test.setOnClickListener(this);
        cancel.setOnClickListener(this);
        name = (TextView)findViewById(R.id.name);
        name.setText(user.getUsername());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_profile:{
                Bundle b = new Bundle();
                b.putSerializable("user", user);
                Intent it = new Intent(activity, Student_Teacher_Profile.class);
                it.putExtras(b);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                dismiss();
                activity.startActivity(it);
                break;
            }
            case R.id.dialog_test:{
                Bundle b = new Bundle();
                b.putSerializable("user", user);
                Intent it = new Intent(activity, TeacherStudentTestList.class);
                it.putExtras(b);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                dismiss();
                activity.startActivity(it);
                dismiss();
                break;
            }
            case R.id.dialog_cancel:{
                dismiss();
                break;
            }
            default:{
                dismiss();
            }
        }
    }
}
