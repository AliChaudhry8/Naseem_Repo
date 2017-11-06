package edu.plan9.naseemdev.naseem;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;

import Model.Constants;
import Model.Session;

import static android.view.View.GONE;

public class Profile extends AppCompatActivity {

    private ImageView profile_image;
    private ProgressBar profile_image_progress;
    private Session session;
    private EditText first_name, second_name, user_name, email, school_name, section_name, telephone, password, confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new Session(getApplicationContext());
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(session.getUsername());
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile_image = (ImageView) findViewById(R.id.image_id);
        profile_image_progress = (ProgressBar)findViewById(R.id.profile_image_progress);
        first_name = (EditText)findViewById(R.id.first_name);
        second_name = (EditText)findViewById(R.id.second_name);
        user_name= (EditText)findViewById(R.id.user_name);
        email= (EditText)findViewById(R.id.email);
        school_name= (EditText)findViewById(R.id.school_name);
        section_name= (EditText)findViewById(R.id.section_name);
        telephone= (EditText)findViewById(R.id.telephone);
        password= (EditText)findViewById(R.id.password);
        confirm_password= (EditText)findViewById(R.id.confirm_password);
        boolean flag = isNull(session.getFirstName().toString());
        if(flag)
            first_name.setText(session.getFirstName().toString());
        flag = isNull(session.getSecondName().toString());
        if(flag)
            second_name.setText(session.getSecondName().toString());
        user_name.setText(session.getUsername().toString());
        flag = isNull(session.getEmail().toString());
        //Toast.makeText(getApplicationContext(), "Email: " + session.getEmail(), Toast.LENGTH_LONG).show();
        if(flag)
            email.setText(session.getEmail().toString());
        flag = isNull(session.getSchoolName().toString());
        if(flag)
            school_name.setText(session.getSchoolName().toString());
        flag = isNull(session.getSectionName().toString());
        if(flag)
            section_name.setText(session.getSectionName().toString());
        flag = isNull(session.getTelephone());
        if(flag)
            telephone.setText(session.getTelephone());

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
                        profile_image_progress.setVisibility(View.INVISIBLE);
                    }
                    else{
                        load_image_from_amazon();
                    }
                }
                else {
                    profile_image_progress.setVisibility(View.INVISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
        else{
            // For device below MarshMallow
            load_image_from_internal_storage();
        }
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
                            profile_image_progress.setVisibility(GONE);
                        }
                        else{
                            load_image_from_amazon_and_save_to_internal_storage();
                        }
                    }
                    else {
                        profile_image_progress.setVisibility(GONE);
                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else
                {
                    profile_image_progress.setVisibility(GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_GET_WRITE_PERMISSION, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
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

    // Function to load Image from Amazon S3
    public void load_image_from_amazon(){
        profile_image_progress.setVisibility(View.VISIBLE);
        try{
            Glide.with(this).asBitmap().load(session.getAvatar()).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    profile_image_progress.setVisibility(View.GONE);
                    profile_image.setImageBitmap(resource);

                }
            });
        }catch (Exception e){
            profile_image_progress.setVisibility(GONE);
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture_1, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    // Function to load Image from Internal Storage
    public void load_image_from_internal_storage() {

        profile_image_progress.setVisibility(View.VISIBLE);
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
                            profile_image_progress.setVisibility(View.GONE);
                            profile_image.setImageBitmap(resource);
                        }
                    });
                }catch (Exception e){
                    profile_image_progress.setVisibility(GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture_1, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }else{

                if(isConnected()) {
                    if(session.getAvatar().equals(null) || session.getAvatar().equals("") || session.getAvatar().equals("null")) {
                        profile_image_progress.setVisibility(GONE);
                    }
                    else{
                        load_image_from_amazon_and_save_to_internal_storage();
                    }
                }
                else {
                    profile_image_progress.setVisibility(GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
        else {

            if(isConnected()) {
                if(session.getAvatar().equals(null) || session.getAvatar().equals("") || session.getAvatar().equals("null")) {
                    profile_image_progress.setVisibility(GONE);
                }
                else{
                    load_image_from_amazon_and_save_to_internal_storage();
                }
            }
            else {
                profile_image_progress.setVisibility(GONE);
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }
    // Function to load Image from Amazon S3 and save to Internal Storage
    public void load_image_from_amazon_and_save_to_internal_storage(){

        profile_image_progress.setVisibility(View.VISIBLE);
        try{
            String url = session.getAvatar()+"";
            Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    profile_image_progress.setVisibility(View.GONE);
                    profile_image.setImageBitmap(resource);
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
                        profile_image_progress.setVisibility(GONE);
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

    public boolean isNull(String s){
        boolean flag = true;
        if(s.equals("") || s.equals("null") || s.equals(null))
            flag = false;
        return flag;
    }
}
