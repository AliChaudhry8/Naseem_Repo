package edu.plan9.naseemdev.naseem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.SchoolSection;
import BussinessObjects.School_BO;
import BussinessObjects.Section_BO;
import BussinessObjects.User_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 5/13/2017.
 */

public class SignUp extends Fragment implements View.OnClickListener{

    private Spinner s1, s2;
    private EditText username, password, confirm_password;
    private Button sign_up;
    private RelativeLayout relativeLayout, reload_layout;
    private ArrayList<Integer> schoolsID, sectionsID;
    private ArrayList<String> schoolsName, sectionsName;
    private ArrayList<School_BO> schools;
    private ArrayList<Section_BO> sections;
    private boolean flag;
    private ProgressDialog progressDialog;
    private ImageView reload;
    public SignUp() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.b3);
        flag = false;
        reload_layout = (RelativeLayout)rootView.findViewById(R.id.reload_layout);
        reload_layout.setVisibility(View.GONE);
        reload = (ImageView)rootView.findViewById(R.id.reload_schools_sections);
        reload.setOnClickListener(this);
        s1 = (Spinner)rootView.findViewById(R.id.signUpSchool);
        s1.setVisibility(View.INVISIBLE);
        s2 = (Spinner)rootView.findViewById(R.id.signUpSection);
        s2.setVisibility(View.INVISIBLE);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(flag)
                {
                    if(position == 0)
                    {
                        TextView tv = (TextView)s1.getSelectedView();
                        tv.setError("Select a School First");
                        return;
                    }
                    s2.setAdapter(null);
                    sectionsID.clear();
                    sectionsName.clear();
                    int sID = schoolsID.get(position);
                    int index = 0;
                    String promptSection = "Select Section";
                    sectionsID.add(index);
                    sectionsName.add(promptSection);
                    for(int i=0; i<sections.size(); i++)
                    {
                        if( sID == sections.get(i).getSchool_id())
                        {
                            int sectionId = sections.get(i).getId();
                            sectionsID.add(sectionId);
                            String name = sections.get(i).getName();
                            sectionsName.add(name);
                        }
                    }
                    if(sectionsName.size() == 0)
                    {
                        sectionsName.add("No Section Available");
                    }
                    ArrayAdapter<String> sectionArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.school_section_spinner, sectionsName);
                    s2.setAdapter(sectionArrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schoolsID = new ArrayList<Integer>();
        sectionsID = new ArrayList<Integer>();

        schoolsName = new ArrayList<String>();
        sectionsName = new ArrayList<String>();
        schools = new ArrayList<School_BO>();
        sections = new ArrayList<Section_BO>();
        username  = (EditText)rootView.findViewById(R.id.signUpusername);
        password = (EditText)rootView.findViewById(R.id.signUpPassword);
        confirm_password = (EditText)rootView.findViewById(R.id.signUpPasswordConfirm);
        sign_up = (Button)rootView.findViewById(R.id.signUpButton);
        sign_up.setOnClickListener(this);
        sign_up.setEnabled(false);
        relativeLayout = (RelativeLayout)rootView.findViewById(R.id.progress_layout);
        if(isConnected()){
            reload_layout.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            getSchoolSection();
        }
        else{
            reload_layout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signUpButton:{
                if(!isConnected()) {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if(!isValidate())
                    return;
                register_user();
                break;
            }
            case R.id.reload_schools_sections:{
                if(isConnected()){
                    reload_layout.setVisibility(View.GONE);
                    sign_up.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    getSchoolSection();
                }
                else{
                    reload_layout.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    sign_up.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                break;
            }
        }
    }

    private boolean isConnected()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            connected = true;
        else
            connected = false;
        return  connected;
    }

    public boolean isValidate() {
        boolean valid = true;
        String uname = username.getText().toString();
        String password1 = password.getText().toString();
        String cPassword = confirm_password.getText().toString();

        if (uname.isEmpty() || uname.length() < 3) {
            username.setError(Constants.Error_User_Required);
            valid = false;
        } else {
            username.setError(null);
        }

        if (password1.isEmpty() || password1.length() < 6 || password1.length() > 20) {
            password.setError(Constants.Error_Password_Required);
            valid = false;
        } else {
            password.setError(null);
        }

        if (cPassword.isEmpty() || cPassword.length() < 6 || cPassword.length() > 20 || !(cPassword.equals(password1))) {
            confirm_password.setError(Constants.Error_Password_Match);
            valid = false;
        } else {
            confirm_password.setError(null);
        }
        if(s1.getSelectedItemPosition() == 0)
        {
            TextView tv = (TextView)s1.getSelectedView();
            tv.setError(Constants.Error_School_Required);
            valid = false;
        }
        else
        {
            TextView tv = (TextView)s1.getSelectedView();
            tv.setError(null);
        }
        if(s2.getSelectedItemPosition() == 0)
        {
            TextView tv = (TextView)s2.getSelectedView();
            tv.setError(Constants.Error_Section_Required);
            valid = false;
        }
        else {
            TextView tv = (TextView)s2.getSelectedView();
            tv.setError(null);
        }
        return valid;
    }

    public void getSchoolSection()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_School_Section,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("-1") || s.equals("-2"))
                        {
                            relativeLayout.setVisibility(View.GONE);
                            s1.setVisibility(View.VISIBLE);
                            s2.setVisibility(View.VISIBLE);
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return;
                        }
                        JsonParsor js = new JsonParsor();
                        SchoolSection schoolSection = js.parseSchoolSection(s);
                        if(schoolSection.getSchool().size() > 0 && schoolSection.getSection().size() > 0){
                            schools = schoolSection.getSchool();
                            sections = schoolSection.getSection();
                            int index= 0;
                            schoolsID.add(index);
                            String promptSchool= "Select School";
                            schoolsName.add(promptSchool);
                            for(int i =0; i<schools.size(); i++)
                            {
                                int id = schools.get(i).getId();
                                schoolsID.add(id);
                                String name = schools.get(i).getName();
                                schoolsName.add(name);
                            }
                            ArrayAdapter<String> schoolArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.school_section_spinner, schoolsName);
                            sectionsID.add(index);
                            String promptSection= "Select Section";
                            sectionsName.add(promptSection);
                            ArrayAdapter<String> sectionArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.school_section_spinner, sectionsName);
                            s1.setAdapter(schoolArrayAdapter);
                            s2.setAdapter(sectionArrayAdapter);
                            flag = true;
                        }
                        relativeLayout.setVisibility(View.GONE);
                        s1.setVisibility(View.VISIBLE);
                        s2.setVisibility(View.VISIBLE);
                        sign_up.setVisibility(View.VISIBLE);
                        sign_up.setEnabled(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        relativeLayout.setVisibility(View.GONE);
                        reload_layout.setVisibility(View.VISIBLE);
                        sign_up.setVisibility(View.GONE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void register_user()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Sign_Up,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        try{
                            if(s.toString().equals("1"))
                            {
                                username.setError(Constants.Error_Username_Not_available);
                                return;
                            }
                            else if(s.toString().equals("-1") || s.toString().equals("-2"))
                            {
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            JsonParsor jsonParsor = new JsonParsor();
                            User_BO u = jsonParsor.parseUser(s.toString());
                            Session session = new Session(getActivity().getApplicationContext());
                            session.create_session(u);

                            if(u.getRole().equals("Student")){
                                Intent it = new Intent(getActivity(), Student.class);
                                getActivity().finish();
                                startActivity(it);
                            }
                            else {
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No Activity for Other Roles\nJust Student is Allowed Yet", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }catch (Exception e)
                        {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_USER_Sign_Up_Failed, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.USER_U_Name,username.getText().toString());
                map.put(Constants.USER_U_Pass, password.getText().toString());
                map.put(Constants.USER_U_School_Name, s1.getSelectedItem().toString());
                map.put(Constants.USER_U_Section_Name, s2.getSelectedItem().toString());

                String id1 = schoolsID.get(s1.getSelectedItemPosition()).toString();
                String id2 = sectionsID.get(s2.getSelectedItemPosition()).toString();

                map.put(Constants.USER_U_School_Id, id1);
                map.put(Constants.USER_U_Section_Id, id2);
                return map;
            }
        };

        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Registering);
        progressDialog.show();
        username.setError(null);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}