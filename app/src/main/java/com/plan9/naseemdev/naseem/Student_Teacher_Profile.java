package com.plan9.naseemdev.naseem;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import java.util.HashMap;
import java.util.Map;
import BussinessObjects.User_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

public class Student_Teacher_Profile extends AppCompatActivity implements View.OnClickListener {
    private final static int CODE_CAMERA = 100;
    private final static int CODE_GALLERY = 200;
    private ImageView profile_image;
    private ProgressBar profile_image_progress;
    private Session session;
    private EditText first_name, second_name, user_name, email, section_name, telephone, password, confirm_password;
    private LinearLayout reload, progress, radio_group_gender, radio_group_role;
    private Button update;
    private TextView tv_gender, tv_age, tv_password, tv_role;
    private int id;
    private Spinner spinner_age;
    private RadioButton male, female, role_student, role_teacher, role_principal, role_parent;
    private ProgressDialog progressDialog;
    private User_BO user;
    private String avatar;
    private FloatingActionButton camera, gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__teacher__profile);

        progressDialog = new ProgressDialog(Student_Teacher_Profile.this, R.style.AppTheme_Dark);

        Bundle b = getIntent().getExtras();
        User_BO u = (User_BO) b.getSerializable("user");
        id = u.getId();
        avatar = u.getAvatar();
        session = new Session(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(u.getUsername());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        camera = (FloatingActionButton) findViewById(R.id.st_camera);
        gallery = (FloatingActionButton) findViewById(R.id.st_gallery);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        profile_image = (ImageView) findViewById(R.id.image_id);
        profile_image_progress = (ProgressBar) findViewById(R.id.profile_image_progress);

        if (u.getAvatar().equals("") || u.getAvatar().equals(null) || u.getAvatar().equals("null")) {
            profile_image.setImageDrawable(getResources().getDrawable(R.drawable.temp_pic));
            profile_image.setVisibility(View.VISIBLE);
            profile_image_progress.setVisibility(View.GONE);
        } else {
            profile_image_progress.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(u.getAvatar()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    profile_image.setVisibility(View.VISIBLE);
                    profile_image_progress.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    profile_image.setVisibility(View.VISIBLE);
                    profile_image_progress.setVisibility(View.GONE);
                    return false;
                }
            }).into(profile_image);
        }

        progress = (LinearLayout) findViewById(R.id.load_profile);
        reload = (LinearLayout) findViewById(R.id.reload_profile_layout);
        reload.setOnClickListener(this);
        first_name = (EditText) findViewById(R.id.p_first_name);
        second_name = (EditText) findViewById(R.id.p_second_name);
        user_name = (EditText) findViewById(R.id.p_user_name);
        email = (EditText) findViewById(R.id.p_email);
        section_name = (EditText) findViewById(R.id.p_section_name);
        telephone = (EditText) findViewById(R.id.p_telephone);
        password = (EditText) findViewById(R.id.p_password);
        confirm_password = (EditText) findViewById(R.id.p_confirm_password);
        update = (Button) findViewById(R.id.p_update);
        update.setOnClickListener(this);
        tv_age = (TextView) findViewById(R.id.p_tv_age);
        tv_gender = (TextView) findViewById(R.id.p_tv_gender);
        tv_password = (TextView) findViewById(R.id.p_tv_password);
        radio_group_gender = (LinearLayout) findViewById(R.id.radio_group_gender);
        spinner_age = (Spinner) findViewById(R.id.spinner_age);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        tv_role = (TextView) findViewById(R.id.p_tv_role);
        radio_group_role = (LinearLayout) findViewById(R.id.radio_group_role);
        role_student = (RadioButton) findViewById(R.id.role_student);
        role_teacher = (RadioButton) findViewById(R.id.role_teacher);
        role_principal = (RadioButton) findViewById(R.id.role_principal);
        role_parent = (RadioButton) findViewById(R.id.role_parent);
        load_user();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.p_update: {
                update_user();
                break;
            }
            case R.id.reload_profile_layout: {
                load_user();
                break;
            }
            case R.id.st_camera: {
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = getCameraPermission();
                    if (permission) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CODE_CAMERA);
                    }
                } else {*/
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, CODE_CAMERA);
                //}
                break;
            }
            case R.id.st_gallery: {
                Toast.makeText(getApplicationContext(), "GALLERY", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profile_image.setImageBitmap(photo);
        }
    }


    public void load_user() {
        hide();
        progress.setVisibility(View.VISIBLE);
        reload.setVisibility(View.GONE);
        if (isConnected()) {
            getUser();
        } else {
            progress.setVisibility(View.GONE);
            reload.setVisibility(View.VISIBLE);
            hide();
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void getUser() {
        String URL = Constants.URL_Principal_User + session.getAuthenticationToken();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    if (s.equals("-1")) {
                        session.destroy_session();
                        Intent it = new Intent(getApplicationContext(), SignUpSignIn.class);
                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        finish();
                        startActivity(it);
                    } else if (s.equals("-2")) {
                        reload.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        hide();
                    } else {
                        JsonParsor jsonParsor = new JsonParsor();
                        user = jsonParsor.parseStudentTeacher(s);
                        user.setId(id);
                        first_name.setText(user.getFirst_name());
                        second_name.setText(user.getSecond_name());
                        user_name.setText(user.getUsername());
                        email.setText(user.getEmail());
                        section_name.setText(user.getSection_name());
                        telephone.setText(user.getTelephone());
                        if (user.getGender().equals("Male"))
                            male.setChecked(true);
                        else
                            female.setChecked(true);

                        spinner_age.setSelection(user.getAge());

                        if (user.getRole().equals("Student"))
                            role_student.setChecked(true);
                        else if (user.getRole().equals("Teacher"))
                            role_teacher.setChecked(true);
                        else if (user.getRole().equals("Principal"))
                            role_principal.setChecked(true);
                        else if (user.getRole().equals("Parent"))
                            role_parent.setChecked(true);
                        progress.setVisibility(View.GONE);
                        reload.setVisibility(View.GONE);
                        show();
                    }
                } catch (Exception e) {
                    reload.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                reload.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(Constants.User_Key_Id, String.valueOf(session.getUserId()));
                map.put(Constants.User_Key_U_Id, String.valueOf(id));

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private boolean isConnected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        else
            connected = false;
        return connected;
    }

    public void show() {
        tv_role.setVisibility(View.VISIBLE);
        radio_group_role.setVisibility(View.VISIBLE);
        first_name.setVisibility(View.VISIBLE);
        second_name.setVisibility(View.VISIBLE);
        user_name.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        section_name.setVisibility(View.VISIBLE);
        tv_gender.setVisibility(View.VISIBLE);
        tv_age.setVisibility(View.VISIBLE);
        telephone.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        tv_password.setVisibility(View.VISIBLE);
        confirm_password.setVisibility(View.VISIBLE);
        update.setVisibility(View.VISIBLE);
        radio_group_gender.setVisibility(View.VISIBLE);
        spinner_age.setVisibility(View.VISIBLE);
    }

    public void hide() {
        update.setVisibility(View.GONE);
        first_name.setVisibility(View.GONE);
        second_name.setVisibility(View.GONE);
        user_name.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        section_name.setVisibility(View.GONE);
        tv_gender.setVisibility(View.GONE);
        tv_age.setVisibility(View.GONE);
        telephone.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        tv_password.setVisibility(View.GONE);
        confirm_password.setVisibility(View.GONE);
        radio_group_gender.setVisibility(View.GONE);
        spinner_age.setVisibility(View.GONE);
        tv_role.setVisibility(View.GONE);
        radio_group_role.setVisibility(View.GONE);
    }

    public int get_data() {
        int i = 1;
        user.setFirst_name(first_name.getText().toString());
        user.setSecond_name(second_name.getText().toString());
        user.setUsername(user_name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setSection_name(section_name.getText().toString());
        if (male.isChecked())
            user.setGender(male.getText().toString());
        else
            user.setGender(female.getText().toString());

        int item = spinner_age.getSelectedItemPosition();
        if (item == 0) {
            i = -1;
            Toast.makeText(getApplicationContext(), "Select a Valid Age", Toast.LENGTH_LONG).show();
        } else {
            String age = (String) spinner_age.getSelectedItem().toString();
            user.setAge(Integer.parseInt(age));
        }

        if (role_student.isChecked())
            user.setRole(role_student.getText().toString());
        else if (role_teacher.isChecked())
            user.setRole(role_teacher.getText().toString());
        else if (role_principal.isChecked())
            user.setRole(role_principal.getText().toString());
        else if (role_parent.isChecked())
            user.setRole(role_parent.getText().toString());

        user.setTelephone(telephone.getText().toString());
        user.setAvatar(avatar);



        return i;
    }

    public void update_user() {
        if (isConnected()) {
            int a = get_data();
            if (a == 1) {
                progressDialog.setCancelable(false);
                progressDialog.setMessage(Constants.User_Updating);

                String URL = Constants.URL_Principal_Update_User + session.getAuthenticationToken();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                try {
                                    progressDialog.dismiss();
                                    if (s.equals("1")) {
                                        Toast toast = Toast.makeText(getApplicationContext(), Constants.User_Updated_1 + user.getUsername() + Constants.User_Updated_2, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        finish();
                                    } else if (s.equals("-1")) {
                                        session.destroy_session();
                                        Intent it = new Intent(getApplicationContext(), SignUpSignIn.class);
                                        finish();
                                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        startActivity(it);
                                        return;
                                    } else if (s.equals("-2")) {
                                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        return;
                                    } else {
                                        JsonParsor jsonParsor = new JsonParsor();
                                        String error = jsonParsor.parseErrors(s);
                                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Following + error, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                } catch (Exception e) {
                                    progressDialog.dismiss();
                                    Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error + e.getMessage(), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(Constants.Principal_Key_Update_User_P_Id, String.valueOf(session.getUserId()));

                        map.put(Constants.Principal_Key_Update_User_Id, String.valueOf(user.getId()));
                        map.put(Constants.Principal_Key_Update_User_First_Name, String.valueOf(user.getFirst_name()));
                        map.put(Constants.Principal_Key_Update_User_Second_Name, String.valueOf(user.getSecond_name()));
                        map.put(Constants.Principal_Key_Update_User_Username, String.valueOf(user.getUsername()));
                        map.put(Constants.Principal_Key_Update_User_Email, String.valueOf(user.getEmail()));
                        map.put(Constants.Principal_Key_Update_User_Section_Name, String.valueOf(user.getSection_name()));
                        map.put(Constants.Principal_Key_Update_User_Gender, String.valueOf(user.getGender()));
                        map.put(Constants.Principal_Key_Update_User_Age, String.valueOf(user.getAge()));
                        map.put(Constants.Principal_Key_Update_User_Role, String.valueOf(user.getRole()));
                        map.put(Constants.Principal_Key_Update_User_Telephone, String.valueOf(user.getTelephone()));
                        map.put(Constants.Principal_Key_Update_User_Avatar, String.valueOf(user.getAvatar()));
                        map.put(Constants.Principal_Key_Update_User_Password, String.valueOf(user.getPassword()));
                        map.put(Constants.Principal_Key_Update_User_Password_Confirmation, String.valueOf(user.getPassword_confirmation()));

                        return map;
                    }
                };
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public boolean getCameraPermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 10);
        }
        return hasPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10: {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, CODE_CAMERA);
                break;
            }
        }
    }
}