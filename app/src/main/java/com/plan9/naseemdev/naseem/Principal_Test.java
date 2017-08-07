package com.plan9.naseemdev.naseem;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Principal_Test extends AppCompatActivity implements View.OnClickListener{

    private Button schedule, attempts;
    private ViewPager viewPager;
    private Test_Pager_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal__test);

        schedule  = (Button)findViewById(R.id.schedule);
        attempts = (Button)findViewById(R.id.attempts);
        schedule.setOnClickListener(this);
        attempts.setOnClickListener(this);
        viewPager = (ViewPager)findViewById(R.id.test_container);
        adapter  = new Test_Pager_Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        schedule.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        attempts.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        break;
                    }
                    case 1:{
                        schedule.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        attempts.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        break;
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.schedule:{
                schedule.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                attempts.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                viewPager.setCurrentItem(0);
                break;
            }
            case R.id.attempts:{
                schedule.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                attempts.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                viewPager.setCurrentItem(1);
                break;
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:{
                //this.finish();
                Intent it = new Intent(this, Principal.class);
                it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(it);
                break;
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //
        // this.finish();
        Intent it = new Intent(this, Principal.class);
        it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(it);
    }


    public class Test_Pager_Adapter extends FragmentPagerAdapter{
        public Test_Pager_Adapter(FragmentManager fm){super(fm);}
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Principal_Test_Schedule schedule = new Principal_Test_Schedule();
                return schedule;
            }
            else if (position == 1){
                Principal_Test_Attempts attempts = new Principal_Test_Attempts();
                return attempts;
            }
            return null;
        }
        @Override
        public int getCount() {
            return 2;
        }
    }
}
