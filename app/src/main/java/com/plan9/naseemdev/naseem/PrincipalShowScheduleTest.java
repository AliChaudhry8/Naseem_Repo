package com.plan9.naseemdev.naseem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import BussinessObjects.Principal_Test_BO;

public class PrincipalShowScheduleTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_show_schedule_test);
        Intent it = getIntent();
        Bundle b = it.getExtras();
        Principal_Test_BO test = (Principal_Test_BO) b.getSerializable("test");
        setTitle(test.getName());

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

}
