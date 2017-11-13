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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

import BussinessObjects.Video_BO;
import Model.Constants;
import Model.Load_Tutorials_Paginater;
import Model.Load_Videos_Paginater;
import Model.Session;

/**
 * Created by DELL on 11/12/2017.
 */

public class Tutorials extends Fragment {
    private YouTubePlayerSupportFragment youTubePlayerView_1;
    private ArrayList<Video_BO> video_bos;
    private ListView videos_thumb;
    private RelativeLayout video;
    private LinearLayout reload_video_layout;
    private ImageView reload_video;
    private ProgressDialog progressDialog;
    Session session;
    public Tutorials() {}


    private PullToLoadView pullToLoadVideos;
    private LinearLayout reload_videos_layout;
    private Load_Tutorials_Paginater paginater;
    String test_id;
    Button save_student;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutorials, container, false);
        session = new Session(this.getActivity());
        //  video_bos = new ArrayList<Video_BO>();
        video = (RelativeLayout)rootView.findViewById(R.id.video);
        video.setVisibility(View.INVISIBLE);
        video.setTag("video");
        reload_video_layout = (LinearLayout)rootView.findViewById(R.id.reload_Videos_Layout);
        reload_video_layout.setVisibility(View.GONE);
        reload_video = (ImageView)rootView.findViewById(R.id.reload_video);
      /*  reload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload_video_layout.setVisibility(View.GONE);
                loadVideos();
            }
        });
        loadVideos();*/

        reload_videos_layout = (LinearLayout)rootView.findViewById(R.id.reload_Videos_Layout);
        reload_videos_layout.setVisibility(View.GONE);
        reload_videos_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    show();
                    paginater.initialize_paginater();
                }
                else{
                    hide();
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Students, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        pullToLoadVideos = (PullToLoadView)rootView.findViewById(R.id.pull_to_load_videos);
        boolean connected = isConnected();
        paginater = new Load_Tutorials_Paginater(getActivity().getApplicationContext(), pullToLoadVideos,this, connected , video);
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Students, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return rootView;
    }

    public void hide(){
        reload_videos_layout.setVisibility(View.VISIBLE);
        pullToLoadVideos.setVisibility(View.GONE);
    }

    public void show(){
        reload_videos_layout.setVisibility(View.GONE);
        pullToLoadVideos.setVisibility(View.VISIBLE);
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

