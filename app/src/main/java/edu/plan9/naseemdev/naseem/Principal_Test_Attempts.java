package edu.plan9.naseemdev.naseem;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.srx.widget.PullToLoadView;

import Model.Constants;
import Model.Principal.Principal_Test_Attempts_Paginator;
import Model.Session;

/**
 * Created by Muhammad Taimoor on 8/3/2017.
 */

public class Principal_Test_Attempts extends Fragment{
    private Session session;
    private LinearLayout reload;
    private PullToLoadView pullToLoadTest;
    private Principal_Test_Attempts_Paginator paginator;
    public Principal_Test_Attempts(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_principal_test_attempts, container, false);
        session = new Session(getActivity().getApplicationContext());
        reload = (LinearLayout)rootView.findViewById(R.id.reload_p_Test_Attempts_Layout);
        reload.setVisibility(View.GONE);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    show();
                    paginator.initialize_paginater();
                }
                else{
                    hide();
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Tests, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        pullToLoadTest = (PullToLoadView)rootView.findViewById(R.id.pull_to_load_test_p_attempts);
        boolean connected = isConnected();
        paginator = new Principal_Test_Attempts_Paginator(getActivity().getApplicationContext(), pullToLoadTest, Principal_Test_Attempts.this, connected);
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Tests, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return rootView;
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

    public void unAuthorized(){
        paginator = null;
        Intent it = new Intent(getActivity().getApplicationContext(), SignUpSignIn.class);
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Unauthorized_Access, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        ActivityCompat.finishAffinity(getActivity());
        getActivity().finish();
        startActivity(it);
    }

    public void show(){
        reload.setVisibility(View.GONE);
        pullToLoadTest.setVisibility(View.VISIBLE);
    }
    public void hide(){
        reload.setVisibility(View.VISIBLE);
        pullToLoadTest.setVisibility(View.GONE);
    }
}
