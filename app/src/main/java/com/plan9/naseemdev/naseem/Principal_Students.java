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
import Model.Principal.Principal_Student_Paginater;

/**
 * Created by Muhammad Taimoor on 7/24/2017.
 */

public class Principal_Students extends Fragment {
    private PullToLoadView pullToLoadStudents;
    private LinearLayout reload_students;
    private Principal_Student_Paginater paginater;
    public Principal_Students(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);
        reload_students = (LinearLayout)rootView.findViewById(R.id.reload_Students_Layout);
        reload_students.setVisibility(View.GONE);
        reload_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    show();
                    paginater.initialize_paginater();
                }
                else{
                    hide();
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Profile_Picture, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        pullToLoadStudents = (PullToLoadView)rootView.findViewById(R.id.pull_to_load_students);
        boolean connected = isConnected();
        paginater = new Principal_Student_Paginater(getActivity().getApplicationContext(), pullToLoadStudents, Principal_Students.this, connected);
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Students, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return rootView;
    }

    public void hide(){
        reload_students.setVisibility(View.VISIBLE);
        pullToLoadStudents.setVisibility(View.GONE);
    }

    public void show(){
        reload_students.setVisibility(View.GONE);
        pullToLoadStudents.setVisibility(View.VISIBLE);
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
