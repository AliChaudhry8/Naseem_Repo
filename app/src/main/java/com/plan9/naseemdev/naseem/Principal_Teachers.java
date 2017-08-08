package com.plan9.naseemdev.naseem;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.srx.widget.PullToLoadView;

import Model.Constants;
import Model.Principal.Principal_Teacher_Paginater;

/**
 * Created by Muhammad Taimoor on 7/24/2017.
 */

public class Principal_Teachers extends Fragment {
    private PullToLoadView pullToLoadTeachers;
    private LinearLayout reload_teachers;
    private Principal_Teacher_Paginater paginater;

    public Principal_Teachers(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teachers, container, false);
        reload_teachers = (LinearLayout)rootView.findViewById(R.id.reload_Teachers_Layout);
        reload_teachers.setVisibility(View.GONE);
        reload_teachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    show();
                    paginater.initialize_paginater();
                }
                else{
                    hide();
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Teachers, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        pullToLoadTeachers = (PullToLoadView)rootView.findViewById(R.id.pull_to_load_teachers);
        boolean connected = isConnected();
        paginater = new Principal_Teacher_Paginater(getActivity().getApplicationContext(), pullToLoadTeachers, Principal_Teachers.this, connected);
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Teachers, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return rootView;
    }

    public void hide(){
        reload_teachers.setVisibility(View.VISIBLE);
        pullToLoadTeachers.setVisibility(View.GONE);
    }

    public void show(){
        reload_teachers.setVisibility(View.GONE);
        pullToLoadTeachers.setVisibility(View.VISIBLE);
    }

    public void unAuthorized(){
        paginater = null;
        Intent it = new Intent(getActivity().getApplicationContext(), SignUpSignIn.class);
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        startActivity(it);
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
}
