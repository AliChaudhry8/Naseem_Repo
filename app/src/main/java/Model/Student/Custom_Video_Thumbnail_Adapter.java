package Model.Student;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import edu.plan9.naseemdev.naseem.R;

import java.util.ArrayList;

import BussinessObjects.Video_BO;

/**
 * Created by Muhammad Taimoor on 6/2/2017.
 */

public class Custom_Video_Thumbnail_Adapter extends ArrayAdapter<Video_BO>{
    private Context context;
    private ArrayList<Video_BO> video_bos;
    public Custom_Video_Thumbnail_Adapter(Context c, ArrayList<Video_BO> v){
        super(c, 0, v);
        StrictMode.ThreadPolicy p = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(p);
        context = c;
        video_bos = v;
    }

    private class ViewHolder {
        private ImageView thumb;
        private TextView name;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Video_BO video_bo = getItem(position);
        ViewHolder v = null;
        View view = convertView;
        try
        {
            if (convertView == null) {
                v = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_video_thumbnail, parent, false);
                v.thumb = (ImageView) convertView.findViewById(R.id.thumb);
                v.name = (TextView) convertView.findViewById(R.id.thumb_name);
                convertView.setTag(v);
            }
            else {
                v = (ViewHolder) convertView.getTag();
            }

            v.thumb.setVisibility(View.INVISIBLE);
            v.name.setText(""+video_bo.getDesc());

            final ProgressBar pB = (ProgressBar) convertView.findViewById(R.id.loadthumb);
            String imgPath = "http://img.youtube.com/vi/" + video_bo.getLink() + "/hqdefault.jpg";
            Glide.with(context).load(imgPath).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    pB.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    pB.setVisibility(View.GONE);
                    return false;
                }
            }).into(v.thumb);
            v.thumb.setVisibility(View.VISIBLE);
            return convertView;

        }catch (Exception e)
        {
            return null;
        }

    }
}
