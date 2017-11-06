package Model.Student.Show_Test_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Image_Questions;
import BussinessObjects.Show_Test_BO.Marks;
import BussinessObjects.Show_Test_BO.Text_Questions;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.ShowTest;

/**
 * Created by DELL on 10/26/2017.
 */

public class Show_Test_Custom_Image_Questions_Adapter extends ArrayAdapter<Image_Questions> {

    private Context context;
    private ArrayList<Image_Questions> image_questions;
    private ShowTest showTest;
    Show_Test_Custom_Image_Questions_Adapter.ViewHolder v = null;

    public Show_Test_Custom_Image_Questions_Adapter(Context c, ArrayList<Image_Questions> test, ShowTest show){
        super(c, 0, test);
        context = c;
        image_questions = test;
        showTest = show;
    }

    private class ViewHolder {
        private ImageView image;
        private TextView marks;
        private TextView title;
        private TextView answer;
        private TextView description;
        private ProgressBar progressBar;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Image_Questions questions = getItem(position);

        try {
            if (convertView == null) {
                v = new Show_Test_Custom_Image_Questions_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_image_questions, parent, false);
                v.title = (TextView)convertView.findViewById(R.id.title);
                v.marks = (TextView)convertView.findViewById(R.id.marks);
                v.answer = (TextView)convertView.findViewById(R.id.answer);
                v.description = (TextView) convertView.findViewById(R.id.description);
                v.image = (ImageView) convertView.findViewById(R.id.test_imageview);
                v.progressBar = (ProgressBar)convertView.findViewById(R.id.progress_image);
                //v.std_answer = (EditText) convertView.findViewById(R.id.std_answer);
                convertView.setTag(v);
            }
            else {
                v = (Show_Test_Custom_Image_Questions_Adapter.ViewHolder) convertView.getTag();
            }
            v.progressBar.setVisibility(View.VISIBLE);
            v.image.setVisibility(View.VISIBLE);
            v.title.setText("Q: "+questions.getTitle());
            v.marks.setText(""+questions.getMarks());
            v.description.setText(questions.getDescription());
           /* Glide.with(context).load(questions.getImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //v.image.setVisibility(View.VISIBLE);
                    v.progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(v.image);*/

            String path = questions.getImage().getPath();
            final Bitmap icon = BitmapFactory.decodeFile(path);
            v.image.setImageBitmap(icon);
            v.progressBar.setVisibility(View.GONE);
            return convertView;
        }catch (Exception e) {
            return null;
        }
    }
}
