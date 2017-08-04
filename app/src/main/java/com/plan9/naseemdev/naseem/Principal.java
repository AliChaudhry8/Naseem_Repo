package com.plan9.naseemdev.naseem;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;

import Model.Constants;
import Model.Session;

import static android.view.View.GONE;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button students, teachers;
    private NavigationView header;
    private View view;
    private ImageView profile_pic;
    private TextView principal_username, principal_school_name, principal_section_name;
    private Session session;
    private ProgressBar profile_pic_progress;
    private ProgressDialog logout_dialog;
    private ViewPager principal_view_pager;
    private PrincipalPageAdapter principalPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*int fi = getIntent().getExtras().getInt("finish");
        if (fi == 1){
            finish();
        }
        Intent it = getIntent();
        if(it != null){
            Toast.makeText(getApplicationContext(), "Intent is Not Null", Toast.LENGTH_LONG).show();
            int fin = it.getIntExtra("finish", -1);
            if (fin == 1){
                finish();
            }
        }*/
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_principal);
        navigationView.setNavigationItemSelectedListener(this);

        principal_view_pager = (ViewPager)findViewById(R.id.principal_container);
        principalPageAdapter = new PrincipalPageAdapter(getSupportFragmentManager());
        principal_view_pager.setAdapter(principalPageAdapter);
        principal_view_pager.setOffscreenPageLimit(2);
        // Changing background of Button on Swipe
        principal_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        students.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        teachers.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        //tests.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        break;
                    }
                    case 1:{
                        students.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        teachers.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        //tests.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        break;
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });


        session = new Session(getApplicationContext());
        students = (Button)findViewById(R.id.students);
        students.setOnClickListener(this);
        teachers = (Button)findViewById(R.id.teachers);
        teachers.setOnClickListener(this);
        //tests = (Button)findViewById(R.id.tests);
        //tests.setOnClickListener(this);

        header = (NavigationView)findViewById(R.id.nav_view_principal);
        view = header.getHeaderView(0);
        principal_username = (TextView)view.findViewById(R.id.principal_username);
        principal_school_name = (TextView)view.findViewById(R.id.principal_school_name);
        principal_section_name = (TextView)view.findViewById(R.id.principal_section_name);
        profile_pic = (ImageView)view.findViewById(R.id.principal_profile_pic);
        profile_pic_progress = (ProgressBar)view.findViewById(R.id.profilepicprogress);

        // Loading Username, School and Section name in Navigation bar
        principal_school_name.setText(session.getSchoolName());
        principal_section_name.setText(session.getSectionName());
        principal_username.setText(session.getUsername());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            // For device above MarshMallow
            boolean write_permission = getWritePermission();
            if(write_permission) {
                // If permission Granted
                load_image_from_internal_storage();
            }
            else {
                // If permission not Granted
                if(isConnected()) {
                    if(session.getAvatar().equals(null) || session.getAvatar().equals("") || session.getAvatar().equals("null")){
                        profile_pic_progress.setVisibility(GONE);
                        profile_pic.setVisibility(View.VISIBLE);
                    }
                    else{
                        load_image_from_amazon();
                    }
                }
                else {
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
        else{
            // For device below MarshMallow
            //Toast.makeText(getApplicationContext(), "Below MarshMallwo", Toast.LENGTH_LONG).show();
            load_image_from_internal_storage();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            new AlertDialog.Builder(this).setTitle("Close Naseem").setMessage("Are you sure you want to close the application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent it = new Intent(getApplicationContext(), Principal.class);
                            //it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            //it.putExtra("finish" , 1);
                            //finishAffinity();
                            ActivityCompat.finishAffinity(Principal.this);
                            finish();
                            //startActivity(it);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.principal_test_1:{
                Intent it = new Intent(this, Principal_Test.class);
                it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(it);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.principal_profile:{
                Intent it = new Intent(this, Profile.class);
                startActivity(it);
                //studentProfileBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                //std_bottom_sheet = true;
                break;
            }
            case R.id.principal_logout:{
                logout_dialog = new ProgressDialog(this, R.style.AppTheme_Dark);
                logout_dialog.setCancelable(false);
                logout_dialog.setIndeterminate(true);
                logout_dialog.show();
                if(isConnected())
                    logout();
                else{
                    logout_dialog.dismiss();
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            }
            case R.id.principal_test:{
                Intent it = new Intent(this, Principal_Test.class);
                it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(it);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.students:{
                students.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                teachers.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                //tests.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                principal_view_pager.setCurrentItem(0);
                break;
            }
            case R.id.teachers:{
                students.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                teachers.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                //tests.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                principal_view_pager.setCurrentItem(1);
                break;
            }
        }
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

    public boolean getWritePermission(){
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
        }
        return hasPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(isConnected()) {
                        if(session.getAvatar().equals(null) || session.getAvatar().equals("") || session.getAvatar().equals("null")){
                            profile_pic_progress.setVisibility(GONE);
                            profile_pic.setVisibility(View.VISIBLE);
                        }
                        else{
                            load_image_from_amazon_and_save_to_internal_storage();
                        }
                    }
                    else {
                        profile_pic_progress.setVisibility(GONE);
                        profile_pic.setVisibility(View.VISIBLE);
                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else
                {
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_GET_WRITE_PERMISSION, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
    }

    // Function to load Image from Amazon S3
    public void load_image_from_amazon(){
        profile_pic.setVisibility(View.INVISIBLE);
        profile_pic_progress.setVisibility(View.VISIBLE);
        try{
            Glide.with(this).asBitmap().load(session.getAvatar()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(), resource);
                    img.setCornerRadius(50);
                    img.setCircular(true);
                    profile_pic.setImageDrawable(img);
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){
            profile_pic_progress.setVisibility(GONE);
            profile_pic.setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture_1, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    // Function to load Image from Internal Storage
    public void load_image_from_internal_storage() {
        //Toast.makeText(getApplicationContext(), "Going to Load From Internal Storage", Toast.LENGTH_LONG).show();

        profile_pic.setVisibility(View.INVISIBLE);
        profile_pic_progress.setVisibility(View.VISIBLE);
        String folder_main = "Naseem";
        File f = new File(Environment.getExternalStorageDirectory()+"/" + folder_main, "Pictures");
        if (f.exists()) {
            String profile_img = session.getUsername()+".jpg";
            File file = new File(f, profile_img);
            if(file.exists()){
                try{
                    Uri uri = Uri.fromFile(new File(f, profile_img));
                    Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(), resource);
                            img.setCornerRadius(50);
                            img.setCircular(true);
                            profile_pic.setImageDrawable(img);
                            profile_pic_progress.setVisibility(GONE);
                            profile_pic.setVisibility(View.VISIBLE);
                        }
                    });
                }catch (Exception e){
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture_1, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }else{
                if(isConnected()) {
                    if(session.getAvatar().equals(null) || session.getAvatar().equals("") || session.getAvatar().equals("null")) {
                        profile_pic_progress.setVisibility(GONE);
                        profile_pic.setVisibility(View.VISIBLE);
                    }
                    else{
                        load_image_from_amazon_and_save_to_internal_storage();
                    }
                }
                else {
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
        else {
            //Toast.makeText(getApplicationContext(), "Internal Storage File not Found", Toast.LENGTH_LONG).show();

            if(isConnected()) {
                if(session.getAvatar().equals(null) || session.getAvatar().equals("") || session.getAvatar().equals("null")) {
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                }
                else{
                    load_image_from_amazon_and_save_to_internal_storage();
                }
            }
            else {
                profile_pic_progress.setVisibility(GONE);
                profile_pic.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
    // Function to load Image from Amazon S3 and save to Internal Storage
    public void load_image_from_amazon_and_save_to_internal_storage(){
        profile_pic.setVisibility(View.INVISIBLE);
        profile_pic_progress.setVisibility(View.VISIBLE);
        try{
            String url = session.getAvatar()+"";
            Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    //Toast.makeText(getApplicationContext(), "Image Loaded", Toast.LENGTH_LONG).show();

                    RoundedBitmapDrawable img = RoundedBitmapDrawableFactory.create(getResources(), resource);
                    img.setCornerRadius(50);
                    img.setCircular(true);
                    profile_pic.setImageDrawable(img);
                    profile_pic_progress.setVisibility(GONE);
                    profile_pic.setVisibility(View.VISIBLE);
                    String folder_main = "Naseem";
                    File f = new File(Environment.getExternalStorageDirectory()+"/" + folder_main, "Pictures");
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    String profile_img = session.getUsername()+".jpg";
                    File file = new File(f, profile_img);
                    if(file.exists()){
                        file.delete();
                    }
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        resource.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        profile_pic_progress.setVisibility(GONE);
                        profile_pic.setVisibility(View.VISIBLE);
                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture_1, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            });
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture_1, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void logout(){
        String URL = Constants.URL_Logout +session.getUserId() + "?auth=" + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            if(s.equals("1") || s.equals("-1") || s.equals("-2")){
                                logout_dialog.dismiss();
                                logout_successful();
                            }
                        }catch (Exception e) {
                            logout_dialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                logout_dialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void logout_successful() {
        session.destroy_session();
        Intent it = new Intent(this, SignUpSignIn.class);
        finish();
        startActivity(it);
    }


    public class PrincipalPageAdapter extends FragmentPagerAdapter {
        public PrincipalPageAdapter(FragmentManager fm){ super(fm);}

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                Principal_Students principal_students = new Principal_Students();
                return principal_students;
            }
            else if(position == 1){
                Principal_Teachers principal_teachers = new Principal_Teachers();
                return principal_teachers;
            }
            return null;
        }
        @Override
        public int getCount() {
            return 2;
        }
    }
}
