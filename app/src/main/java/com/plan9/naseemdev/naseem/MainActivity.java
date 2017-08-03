package com.plan9.naseemdev.naseem;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import Model.Session;

public class MainActivity extends Activity {
    private TextView txt_n, txt_a, txt_s, txt_e1, txt_e2, txt_m;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_n = (TextView)findViewById(R.id.main_n);
        txt_a = (TextView)findViewById(R.id.main_a);
        txt_s = (TextView)findViewById(R.id.main_s);
        txt_e1 = (TextView)findViewById(R.id.main_e1);
        txt_e2 = (TextView)findViewById(R.id.main_e2);
        txt_m = (TextView)findViewById(R.id.main_m);

        Animation rightToLeft = AnimationUtils.loadAnimation(MainActivity.this, R.anim.pull_back);
        Animation leftToRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.pull_forward);

        txt_n.startAnimation(rightToLeft);
        txt_a.startAnimation(leftToRight);
        txt_s.startAnimation(rightToLeft);
        txt_e1.startAnimation(leftToRight);
        txt_e2.startAnimation(rightToLeft);
        txt_m.startAnimation(leftToRight);
        session = new Session(getApplicationContext());
        // Waiting here for 2 seconds
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                if(session.getUserId() == -1) {
                    Intent it = new Intent(getApplicationContext(), SignUpSignIn.class);
                    startActivity(it);
                }
                else{
                    if(session.getRole().equals("Student")) {
                        Intent it = new Intent(getApplicationContext(), Student.class);
                        startActivity(it);
                    }
                    else if(session.getRole().equals("Principal")){
                        //Intent it = new Intent(getApplicationContext(), Principal.class);
                        finish();
                        Intent it = new Intent(getApplicationContext(), Principal.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(it);
                        //startActivity(it);
                    }
                    else if(session.getRole().equals("Parent")){
                        Intent it = new Intent(getApplicationContext(), Parent.class);
                        finish();
                        startActivity(it);
                    }
                    else {
                            Toast toast = Toast.makeText(getApplicationContext(), "No Activity for Other Roles\nJust Student and Principal is Allowed Yet", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                    }
                }
            }
        }.start();
    }
}
