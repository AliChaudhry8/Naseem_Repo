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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.User_BO;
import BussinessObjects.Video_BO;
import Model.Constants;
import Model.JsonParsor;
import Model.Session;
import Model.Student.Custom_Video_Thumbnail_Adapter;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        session = new Session(this.getActivity());
        video_bos = new ArrayList<Video_BO>();
        video = (RelativeLayout)rootView.findViewById(R.id.video);
        video.setVisibility(View.INVISIBLE);
        video.setTag("video");
        reload_video_layout = (LinearLayout)rootView.findViewById(R.id.reload_Videos_Layout);
        reload_video_layout.setVisibility(View.GONE);
        reload_video = (ImageView)rootView.findViewById(R.id.reload_video);
        videos_thumb = (ListView) rootView.findViewById(R.id.videos_thumb);
        reload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload_video_layout.setVisibility(View.GONE);
                loadVideos();
            }
        });
        loadVideos();
        return rootView;
    }

    public void loadVideos(){
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

           /* Video_BO v1 = new Video_BO(Constants.Jan_ID_1 , Constants.Jan_ID_1_Desc);
            Video_BO v2 = new Video_BO(Constants.Jan_ID_2, Constants.Jan_ID_2_Desc);
            //Video_BO v3 = new Video_BO(Constants.Jan_ID_3, Constants.Jan_ID_3_Desc);
            Video_BO v4 = new Video_BO(Constants.Motu_Patlu_ID_1, Constants.Motu_Patlu_ID_1_Desc);
            Video_BO v5 = new Video_BO(Constants.Motu_Patlu_ID_2, Constants.Motu_Patlu_ID_2_Desc);
            //Video_BO v6 = new Video_BO(Constants.Motu_Patlu_ID_3, Constants.Motu_Patlu_ID_3_Desc);
            Video_BO v7 = new Video_BO(Constants.Meena_ID_1, Constants.Meena_ID_1_Desc);
            Video_BO v8 = new Video_BO(Constants.Meena_ID_2, Constants.Meena_ID_2_Desc);
            //Video_BO v9 = new Video_BO(Constants.Meena_ID_3, Constants.Meena_ID_3_Desc);
            Video_BO v10 = new Video_BO(Constants.Tom_And_Jerry_ID_1, Constants.Tom_And_Jerry_ID_1_Desc);
            Video_BO v11 = new Video_BO(Constants.Tom_And_Jerry_ID_2, Constants.Tom_And_Jerry_ID_2_Desc);
            //Video_BO v12 = new Video_BO(Constants.Tom_And_Jerry_ID_3, Constants.Tom_And_Jerry_ID_3_Desc);
            Video_BO v13 = new Video_BO(Constants.Donal_Duck_ID_1, Constants.Donal_Duck_ID_1_Desc);
            Video_BO v14 = new Video_BO(Constants.Donal_Duck_ID_2, Constants.Donal_Duck_ID_2_Desc);
            //Video_BO v15 = new Video_BO(Constants.Donal_Duck_ID_3, Constants.Donal_Duck_ID_3_Desc);
            Video_BO v16 = new Video_BO(Constants.Pink_Panther_ID_1, Constants.Pink_Panther_ID_1_Desc);
            Video_BO v17 = new Video_BO(Constants.Pink_Panther_ID_2, Constants.Pink_Panther_ID_2_Desc);
            //Video_BO v18 = new Video_BO(Constants.Pink_Panther_ID_3, Constants.Pink_Panther_ID_3_Desc);
            video_bos.add(v1);
            video_bos.add(v2);
            //video_bos.add(v3);
            video_bos.add(v4);
            video_bos.add(v5);
            //video_bos.add(v6);
            video_bos.add(v7);
            video_bos.add(v8);
            //video_bos.add(v9);
            video_bos.add(v10);
            video_bos.add(v11);
            //video_bos.add(v12);
            video_bos.add(v13);
            video_bos.add(v14);
            //video_bos.add(v15);
            video_bos.add(v16);
            video_bos.add(v17);
            //video_bos.add(v18);*/
          /*  Custom_Video_Thumbnail_Adapter adapter = new Custom_Video_Thumbnail_Adapter(getActivity().getApplicationContext(), video_bos);
            videos_thumb.setAdapter(adapter);
            videos_thumb.setVisibility(View.VISIBLE);
            videos_thumb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    video.setVisibility(View.VISIBLE);
                    youTubePlayerView_1.onDestroy();
                    final Video_BO video_bo = video_bos.get(i);
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
            });*/
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
    }
}
