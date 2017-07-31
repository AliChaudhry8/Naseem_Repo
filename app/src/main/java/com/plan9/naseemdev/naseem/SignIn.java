package com.plan9.naseemdev.naseem;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import BussinessObjects.User_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 5/13/2017.
 */

public class SignIn extends Fragment implements View.OnClickListener{

    private EditText username, password;
    private Button signin;
    private ProgressDialog progressDialog;
    public SignIn()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.b3);

        username = (EditText)rootView.findViewById(R.id.signInUsername);
        password  = (EditText)rootView.findViewById(R.id.signInPassword);
        signin = (Button)rootView.findViewById(R.id.signInButton);
        signin.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signInButton:{
                if(!isConnected())
                {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                if(!isValidate())
                    return;
                authenticate_user();
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
        String pass = password.getText().toString();
        if (uname.isEmpty() || uname.length() <= 3)
        {
            username.setError(Constants.Error_User_Required);
            valid = false;
        }
        else {
            username.setError(null);
        }
        if (pass.isEmpty() || password.length() < 6 || password.length() > 20) {
            password.setError(Constants.Error_Password_Required);
            valid = false;
        }
        else {
            password.setError(null);
        }
        return valid;
    }

    public void authenticate_user()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                        try
                        {
                            if(s.toString().equals("-2")) {
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            else if(s.toString().equals("-1")) {
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_USER_Login_Failed, Toast.LENGTH_LONG);
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
                            else if(u.getRole().equals("Principal")){
                                Intent it = new Intent(getActivity(), Principal.class);
                                getActivity().finish();
                                startActivity(it);
                            }
                            else {
                                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No Activity for Other Roles\nJust Student and Principal is Allowed Yet", Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Constants.USER_U_Name,username.getText().toString());
                map.put(Constants.USER_U_Pass, password.getText().toString());
                return map;
            }
        };

        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Authenticating);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
