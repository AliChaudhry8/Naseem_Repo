package Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Video_BO;
import edu.plan9.naseemdev.naseem.Tutorials;
import edu.plan9.naseemdev.naseem.Videos;

/**
 * Created by DELL on 11/12/2017.
 */

public class Load_Tutorials_Paginater implements View.OnClickListener{
    private YouTubePlayerSupportFragment youTubePlayerView_1;
    private Context context;
    private PullToLoadView pullToLoadView;
    private RecyclerView recyclerView;
    private Load_Tutorials_Adapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int page_number, total;
    private boolean connected;
    private Session session;
    private Tutorials videos;
    String group_id ;
    ArrayList<Video_BO> video;
    ProgressDialog progressDialog;
    View v;

    public Load_Tutorials_Paginater(Context c, PullToLoadView p, Tutorials ps, boolean con , View v){
        context = c;
        session = new Session(c);
        pullToLoadView = p;
        progressDialog = new ProgressDialog(ps.getActivity());
        session = new Session(c);
        recyclerView = pullToLoadView.getRecyclerView();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        video = new ArrayList<Video_BO>();
        this.v = v;
        adapter = new Load_Tutorials_Adapter(context,video, recyclerView, ps , v);
        recyclerView.setAdapter(adapter);
        page_number = 1;
        total = 1;
        videos = ps;
        connected = con;

        if(connected)
            initialize_paginater();
    }

    public void initialize_paginater(){
        pullToLoadView.isLoadMoreEnabled(true);
        session = new Session(context);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                getVideos(page_number);
            }
            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadedAll = false;
                page_number = 1;
                getVideos(page_number);
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });
        pullToLoadView.initLoad();
    }

    public void getVideos(int page)
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
                                Toast toast = Toast.makeText(videos.getActivity().getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }
                            else if(s.toString().equals("-1")) {
                                Toast toast = Toast.makeText(videos.getActivity().getApplicationContext(), Constants.Error_USER_Login_Failed, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                return;
                            }

                            JsonParsor jsonParsor = new JsonParsor();
                            final ArrayList<Video_BO> videos_1 = jsonParsor.parse_Videos(s);
                            // video = new ArrayList<Video_BO>();
                            //video = videos_1;
                            Toast.makeText(context.getApplicationContext() , "Successfull" , Toast.LENGTH_LONG).show();
                            // Load_Videos_Adapter adapter = new Load_Videos_Adapter(context, videos_1 , recyclerView , videos , v);
                            for(int i = 0 ; i< videos_1.size() ; i++){
                                adapter.addUser(videos_1.get(i));
                            }
                          /*  videos_thumb.setAdapter(adapter);
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
                            });*/
                            pullToLoadView.setComplete();
                            page_number = page_number + 1;


                        }catch (Exception e)
                        {
                            Toast toast = Toast.makeText(context.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast toast = Toast.makeText(context.getApplicationContext(), Constants.Error_Unrecognized_Error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id" ,String.valueOf(session.getUserId()) );
                map.put(Constants.User_Key_Page,String.valueOf(page_number));
                return map;
            }
        };

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Constants.User_Authenticating);
        // progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        //  group.
    }


}

