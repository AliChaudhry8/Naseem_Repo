package Model.Student.Start_Test_Adapters;

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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Image_Questions;
import Model.Student.Custom_Dialog_Image_Draw_Question;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Image_Question;
import Model.Teacher.New_Test_Content.New_Test_Custom_Image_Questions_Adapter;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;
import edu.plan9.naseemdev.naseem.TakeTest;

/**
 * Created by DELL on 10/22/2017.
 */

public class Take_Test_Image_Questions_Adapter extends ArrayAdapter<Image_Questions> {

    private Context context;
    private ArrayList<Image_Questions> image_questions;
    private TakeTest showTest;
    int currentlyFocusdRow;
    int lastFocus;
    int mposition = -1;

    public Take_Test_Image_Questions_Adapter(Context c, ArrayList<Image_Questions> image_questions, TakeTest show){
        super(c, 0, image_questions);
        context = c;
        this.image_questions = image_questions;
        showTest = show;
    }

    private class ViewHolder {
        private ImageView image;
        private TextView marks;
        private TextView title;
        private Button answer;
        private TextView description;
        private ProgressBar progressBar;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Image_Questions questions = getItem(position);
        final Take_Test_Image_Questions_Adapter.ViewHolder v ;
        try {
            if (convertView == null) {
                v = new Take_Test_Image_Questions_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_image_questions, parent, false);
                v.title = (TextView) convertView.findViewById(R.id.title);
                v.marks = (TextView)convertView.findViewById(R.id.marks);
                v.description = (TextView) convertView.findViewById(R.id.description);
                v.image = (ImageView) convertView.findViewById(R.id.test_imageview);
                v.progressBar = (ProgressBar)convertView.findViewById(R.id.progress_image);
                v.answer = (Button)convertView.findViewById(R.id.answer_image_question);

                convertView.setTag(v);
            }
            else {
                v = (Take_Test_Image_Questions_Adapter.ViewHolder) convertView.getTag();
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

            v.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Custom_Dialog_Image_Draw_Question img = new Custom_Dialog_Image_Draw_Question(showTest , questions , image_questions , position);
                    img.show();
                }
            });



            return convertView;
        }catch (Exception e) {
            return convertView;
        }
    }

}

