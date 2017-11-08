package Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BussinessObjects.Group_BO;
import BussinessObjects.Groups_Selected;
import BussinessObjects.Video_BO;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.Student;
import edu.plan9.naseemdev.naseem.Videos;

/**
 * Created by DELL on 11/6/2017.
 */

public class Load_Videos_Adapter extends RecyclerView.Adapter<Model.Load_Videos_Adapter.MyViewHolder1> {
    private YouTubePlayerSupportFragment youTubePlayerView_1;
    private Context context;
    private ArrayList<Video_BO> users;
    private RecyclerView recyclerView;
    private Videos activity;
    ProgressDialog progressDialog;
    Session session;
    String test_id;
    JSONArray selected ;
    JSONObject jObject;
    View v1;

    public Load_Videos_Adapter(Context c, ArrayList<Video_BO> u, RecyclerView rv, Videos a  , View v){
        context = c;
        recyclerView = rv;
        users = u;
        activity = a;
        session = new Session(c);
        selected  = new JSONArray();
        jObject = new JSONObject();
        this.v1 = v;
    }

    @Override
    public Model.Load_Videos_Adapter.MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_video_thumbnail, parent, false);
        View g = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assign__students__group , parent , false);
        final Model.Load_Videos_Adapter.MyViewHolder1 myViewHolder = new Model.Load_Videos_Adapter.MyViewHolder1(v);
        youTubePlayerView_1 = (YouTubePlayerSupportFragment)activity.getChildFragmentManager().findFragmentById(R.id.myplayer_1);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);

                v1.setVisibility(View.VISIBLE);
                youTubePlayerView_1.onDestroy();
                final Video_BO video_bo = users.get(position);
                youTubePlayerView_1.initialize(Constants.Naseem_Youtube_API, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                        youTubePlayer.setPlaybackEventListener(playbackEventListener);
                        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                            @Override
                            public void onFullscreen(boolean b) {
                                ((Student)context).youTubePlayer = youTubePlayer;
                                ((Student)context).fullscreen = true;
                            }
                        });
                        if(!b)
                            youTubePlayer.loadVideo(video_bo.getLink());
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(context.getApplicationContext(), "Failed to load Video", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final Model.Load_Videos_Adapter.MyViewHolder1 holder,final int position) {
        Video_BO video_bo = users.get(position);
        holder.thumb.setVisibility(View.INVISIBLE);
        holder.name.setText(""+video_bo.getDesc());


        String imgPath = "http://img.youtube.com/vi/" + video_bo.getLink() + "/hqdefault.jpg";
        Glide.with(context).load(imgPath).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.pB.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.pB.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.thumb);
        holder.thumb.setVisibility(View.VISIBLE);
        //return convertView;

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addUser(Video_BO u ){
        // flag_selected = flag;
        users.add(u);
        notifyDataSetChanged();
    }

    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }


    class MyViewHolder1 extends RecyclerView.ViewHolder{
        private ImageView thumb;
        private TextView name;
        private ProgressBar pB;

        public MyViewHolder1(View item){
            super(item);
            thumb = (ImageView) item.findViewById(R.id.thumb);
            name = (TextView) item.findViewById(R.id.thumb_name);
            pB = (ProgressBar) item.findViewById(R.id.loadthumb);
            //final int position  = MyViewHolder1.super.getAdapterPosition();

        }
    }

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
}


