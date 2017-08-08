package com.plan9.naseemdev.naseem;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
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
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.google.android.youtube.player.YouTubePlayer;

import java.io.File;
import java.io.FileOutputStream;

import Model.Constants;
import Model.Session;

import static android.view.View.GONE;

public class Student extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Button gameButton, videosButton, testButton;
    private NavigationView header;
    private View view;
    private ImageView profile_pic;
    private TextView student_username, student_school_name, student_section_name;
    private Session session;
    private ProgressBar profile_pic_progress;
    private StudentPageAdapter studentPageAdapter;
    private ViewPager stdViewPager;
    private BottomSheetBehavior studentProfileBottomSheet;
    private ProgressDialog logout_dialog;

    public boolean fullscreen, std_bottom_sheet;
    public YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Getting all widgets by ID
        studentPageAdapter = new StudentPageAdapter(getSupportFragmentManager());
        stdViewPager = (ViewPager)findViewById(R.id.stdcontainer);
        stdViewPager.setAdapter(studentPageAdapter);
        stdViewPager.setOffscreenPageLimit(3);
        // Changing background of Button on Swipe
        stdViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        gameButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        videosButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        testButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        break;
                    }
                    case 1:{
                        gameButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        videosButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        testButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        break;
                    }
                    case 2:{
                        gameButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        videosButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                        testButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                        break;
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        session = new Session(getApplicationContext());
        gameButton = (Button)findViewById(R.id.games);
        videosButton = (Button)findViewById(R.id.videos);
        testButton = (Button)findViewById(R.id.test);
        header = (NavigationView)findViewById(R.id.nav_view_student);
        view = header.getHeaderView(0);
        student_username = (TextView)view.findViewById(R.id.studentusername);
        profile_pic = (ImageView)view.findViewById(R.id.studentprofilepic);
        profile_pic_progress = (ProgressBar)view.findViewById(R.id.profilepicprogress);
        student_school_name = (TextView)view.findViewById(R.id.studentschoolname);
        student_section_name = (TextView)view.findViewById(R.id.studentsectionname);
        gameButton.setOnClickListener(this);
        videosButton.setOnClickListener(this);
        testButton.setOnClickListener(this);
        // Loading bottom sheet
        View v = findViewById(R.id.student_bottom_sheet);
        studentProfileBottomSheet = BottomSheetBehavior.from(v);
        studentProfileBottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        // Making bottom sheet hidden
        studentProfileBottomSheet.setPeekHeight(0);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        studentProfileBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_DRAGGING) {
                    // set peek height to device height
                    studentProfileBottomSheet.setPeekHeight(height);
                    studentProfileBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
        // Used to detect bottom sheet
        std_bottom_sheet = false;
        // Used to detect Cartoons are playing or not
        fullscreen = false;


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_student);
        navigationView.setNavigationItemSelectedListener(this);
        // Loading Username, School and Section name in Navigation bar
        student_school_name.setText(session.getSchoolName());
        student_section_name.setText(session.getSectionName());
        student_username.setText(session.getUsername());

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
        else if(std_bottom_sheet){
            studentProfileBottomSheet.setPeekHeight(0);
            studentProfileBottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
            std_bottom_sheet = false;
        }
        else if(stdViewPager.getCurrentItem() == 1) {
            RelativeLayout r = (RelativeLayout)stdViewPager.findViewWithTag("video");
            //View v = getSupportFragmentManager().getFragments().get(1).getView();
            //RelativeLayout r = (RelativeLayout)v.findViewById(R.id.video);
            if(youTubePlayer != null && fullscreen) {
                fullscreen = false;
                youTubePlayer.setFullscreen(fullscreen);
            }
            //else if(r.getVisibility() == View.VISIBLE)
               //Toast.makeText(getApplicationContext(), "Video is Playing", Toast.LENGTH_LONG).show();

            else if(r.getVisibility() == View.VISIBLE)

            {
                //Toast.makeText(getApplicationContext(), "Video is Playing", Toast.LENGTH_LONG).show();
                r.setVisibility(View.INVISIBLE);
                //getSupportFragmentManager().getFragments().get(1).getView().findViewById(R.id.video).setVisibility(View.INVISIBLE);
            }
            else{
                new AlertDialog.Builder(this).setTitle("Close Naseem").setMessage("Are you sure you want to close the application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
        else{
            new AlertDialog.Builder(this).setTitle("Close Naseem").setMessage("Are you sure you want to close the application?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Query: "+ query, Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.student_profile:{
                Intent it = new Intent(this, Profile.class);
                startActivity(it);
                //studentProfileBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                //std_bottom_sheet = true;
                break;
            }
            case R.id.student_logout:{
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.games: {
                gameButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                videosButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                testButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                stdViewPager.setCurrentItem(0);
                break;
            }
            case R.id.videos: {
                gameButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                videosButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                testButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                stdViewPager.setCurrentItem(1);
                break;
            }
            case R.id.test: {
                gameButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                videosButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_1));
                testButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.user_button_bg_2));
                stdViewPager.setCurrentItem(2);
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
        //Toast.makeText(getApplicationContext(), "Going to Load From Amazon", Toast.LENGTH_LONG).show();

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
                            if(s.equals("1")){
                                logout_dialog.dismiss();
                                logout_successful();
                            }
                            else {
                                logout_dialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
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


    public class StudentPageAdapter extends FragmentPagerAdapter{
        public StudentPageAdapter(FragmentManager fm){ super(fm);}

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                Games games = new Games();
                return games;
            }
            else if(position == 1){
                Videos videos = new Videos();
                return videos;
            }
            else if(position == 2){
                Test test = new Test();
                return test;
            }
            return null;
        }
        @Override
        public int getCount() {
            return 3;
        }
    }
}
