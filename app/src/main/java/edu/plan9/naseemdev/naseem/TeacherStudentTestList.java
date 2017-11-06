package edu.plan9.naseemdev.naseem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srx.widget.PullToLoadView;

import BussinessObjects.User_BO;
import Model.Constants;
import Model.Principal.Principal_Student_Teacher_Test_List_Paginator;

public class TeacherStudentTestList extends AppCompatActivity {

    private LinearLayout reload;
    private TextView reload_prompt;
    private PullToLoadView test_list;
    private Principal_Student_Teacher_Test_List_Paginator paginator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_test_list);

        Bundle b = getIntent().getExtras();
        User_BO u = (User_BO) b.getSerializable("user");
        if(u.getRole().equals("Student"))
            setTitle("Student: " + u.getUsername());
        else if (u.getRole().equals("Teacher"))
            setTitle("Teacher: " + u.getUsername());
        reload = (LinearLayout)findViewById(R.id.reload_student_test_list);
        reload_prompt = (TextView)findViewById(R.id.label_reload_error);
        test_list = (PullToLoadView)findViewById(R.id.pull_to_load_test_list);
        reload.setVisibility(View.GONE);
        if(u.getRole().equals("Teacher"))
            reload_prompt.setText(R.string.string_reload_teacher_test_list);

        boolean connected = isConnected();

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    show();
                    paginator.initialize_paginater();
                } else {
                    hide();
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Student_Teacher_Test_List, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        paginator = new Principal_Student_Teacher_Test_List_Paginator(getApplicationContext(), test_list, TeacherStudentTestList.this, connected, u.getRole(), u.getId());
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Student_Teacher_Test_List, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home: {
                this.finish();
                break;
            }
        }
        return true;
    }

    public void unAuthorized(){
        paginator = null;
        Intent it = new Intent(getApplicationContext(), SignUpSignIn.class);
        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        ActivityCompat.finishAffinity(this);
        finish();
        startActivity(it);
    }

    public void show(){
        reload.setVisibility(View.GONE);
        test_list.setVisibility(View.VISIBLE);
    }
    public void hide(){
        reload.setVisibility(View.VISIBLE);
        test_list.setVisibility(View.GONE);
    }
    private boolean isConnected()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        else
            connected = false;
        return  connected;
    }
}
