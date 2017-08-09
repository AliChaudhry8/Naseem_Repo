package com.plan9.naseemdev.naseem;

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

import org.w3c.dom.Text;

import BussinessObjects.Principal_Test_BO;
import Model.Constants;
import Model.Principal.Principal_Students_Test_Attempts_Paginater;

public class PrincipalShowTestAttempts extends AppCompatActivity {

    private PullToLoadView attempts;
    private Principal_Students_Test_Attempts_Paginater paginater;
    private LinearLayout reload;
    private TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_show_test_attempts);
        Intent it = getIntent();
        Bundle b = it.getExtras();
        Principal_Test_BO test = (Principal_Test_BO) b.getSerializable("test");
        setTitle(test.getName());

        label = (TextView)findViewById(R.id.listing_students);
        attempts = (PullToLoadView)findViewById(R.id.principal_pull_to_load_test_attempts);
        reload = (LinearLayout)findViewById(R.id.principal_reload_test_attempts);
        reload.setVisibility(View.GONE);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected()) {
                    show();
                    paginater.initialize_paginater();
                } else {
                    hide();
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Tests, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        boolean connected = isConnected();
        paginater = new Principal_Students_Test_Attempts_Paginater(getApplicationContext(), attempts, PrincipalShowTestAttempts.this, connected, test.getId());
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Tests, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:{
                this.finish();
                break;
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        this.finish();
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

    public void unAuthorized(){
        paginater = null;
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
        label.setVisibility(View.VISIBLE);
        attempts.setVisibility(View.VISIBLE);
    }
    public void hide(){
        reload.setVisibility(View.VISIBLE);
        label.setVisibility(View.GONE);
        attempts.setVisibility(View.GONE);
    }
}
