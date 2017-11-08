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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.User_BO;
import BussinessObjects.Video_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Load_Videos_Paginater;
import Model.Session;
import Model.Student.Custom_Video_Thumbnail_Adapter;
import Model.Teacher.Assign_Group_Paginater;

/**
 * Created by Muhammad Taimoor on 5/16/2017.
 */

public class Videos extends Fragment {
    private YouTubePlayerSupportFragment youTubePlayerView_1;
    private ArrayList<Video_BO> video_bos;
    private ListView videos_thumb;
    private RelativeLayout video;
    private LinearLayout reload_video_layout;
    private ImageView reload_video;
    private ProgressDialog progressDialog;
    Session session;
    public Videos() {}


    private PullToLoadView pullToLoadVideos;
    private LinearLayout reload_videos_layout;
    private Load_Videos_Paginater paginater;
    String test_id;
    Button save_student;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
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
        paginater = new Load_Videos_Paginater(getActivity().getApplicationContext(), pullToLoadVideos,this, connected , video);
        if(!connected) {
            hide();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Cannot_Load_Students, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        return rootView;
    }

   /* public void loadVideos(){
        if(!isConnected()) {
            videos_thumb.setVisibility(View.GONE);
            video.setVisibility(View.INVISIBLE);
            reload_video_layout.setVisibility(View.VISIBLE);
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), Constants.Error_Internet, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        }else {
            //youTubePlayerView_1 = (YouTubePlayerSupportFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.myplayer_1);
            reload_video_layout.setVisibility(View.GONE);
            youTubePlayerView_1 = (YouTubePlayerSupportFragment)getChildFragmentManager().findFragmentById(R.id.myplayer_1);
            getVideos();


        }
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

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

    public RelativeLayout getLayout(){
        return video;
    }


    public void getVideos()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Get_Videos + session.getAuthenticationToken(),
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
                            final ArrayList<Video_BO> videos = jsonParsor.parse_Videos(s);
                            Toast.makeText(getActivity().getApplicationContext() , "Successfull" , Toast.LENGTH_LONG).show();
                            Custom_Video_Thumbnail_Adapter adapter = new Custom_Video_Thumbnail_Adapter(getActivity().getApplicationContext(), videos);
                            videos_thumb.setAdapter(adapter);
                            videos_thumb.setVisibility(View.VISIBLE);
                            videos_thumb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    video.setVisibility(View.VISIBLE);
                                    youTubePlayerView_1.onDestroy();
                                    final Video_BO video_bo = videos.get(i);
                                    youTubePlayerView_1.initialize(Constants.Naseem_Youtube_API, new YouTubePlayer.OnInitializedListener() {
                                        @Override
                                        public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                                            youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                                            youTubePlayer.setPlaybackEventListener(playbackEventListener);
                                            youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                                                @Override
                                                public void onFullscreen(boolean b) {
                                                    ((Student)getActivity()).youTubePlayer = youTubePlayer;
                                                    ((Student)getActivity()).fullscreen = true;
                                                }
                                            });
                                            if(!b)
                                                youTubePlayer.loadVideo(video_bo.getLink());
                                        }

                                        @Override
                                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Failed to load Video", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });


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
                map.put("id" ,String.valueOf(session.getUserId()) );
                return map;
            }
        };

        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Authenticating);
       // progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }*/

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
