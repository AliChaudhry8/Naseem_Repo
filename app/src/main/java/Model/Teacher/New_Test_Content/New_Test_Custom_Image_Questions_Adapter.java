package Model.Teacher.New_Test_Content;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Image_Questions;
import BussinessObjects.Show_Test_BO.Text_Questions;
import Model.Teacher.Edit_Test_Dialogs.Custom_Dialog_Edit_Image_Question;
import edu.plan9.naseemdev.naseem.New_Test;
import edu.plan9.naseemdev.naseem.R;

/**
 * Created by DELL on 10/13/2017.
 */

public class New_Test_Custom_Image_Questions_Adapter extends ArrayAdapter<Image_Questions> {

    private Context context;
    private ArrayList<Image_Questions> image_questions;
    private New_Test showTest;
    int currentlyFocusdRow;
    int lastFocus;
    int mposition = -1;
    New_Test_Custom_Image_Questions_Adapter.ViewHolder v ;

    public New_Test_Custom_Image_Questions_Adapter(Context c, ArrayList<Image_Questions> test, New_Test show){
        super(c, 0, test);
        context = c;
        image_questions = test;
        showTest = show;
    }

    private class ViewHolder {
        private ImageView image;
        private EditText title;
        private EditText description;
        private EditText marks;
        private Button remove;
        private Button edit;
        private ProgressBar progressBar;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Image_Questions questions = getItem(position);

        try {
            if (convertView == null) {
                v = new New_Test_Custom_Image_Questions_Adapter.ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_new_image_question, parent, false);
                v.title = (EditText)convertView.findViewById(R.id.new_image_title);
                v.description = (EditText) convertView.findViewById(R.id.new_image_description);
                v.marks = (EditText)convertView.findViewById(R.id.new_image_marks);
                v.image = (ImageView) convertView.findViewById(R.id.test_imageview);
                v.remove = (Button) convertView.findViewById(R.id.remove_image_question);
                v.edit = (Button)convertView.findViewById(R.id.edit_image_question);
                v.progressBar = (ProgressBar)convertView.findViewById(R.id.progress_image);


                convertView.setTag(v);
            }
            else {
                v = (New_Test_Custom_Image_Questions_Adapter.ViewHolder) convertView.getTag();
            }
            v.progressBar.setVisibility(View.VISIBLE);
            v.image.setVisibility(View.VISIBLE);
            v.title.setText(questions.getTitle());
            v.description.setText(questions.getDescription());
            v.marks.setText(""+questions.getMarks());
            Glide.with(context).load(questions.getImage()).listener(new RequestListener<Drawable>() {
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
            }).into(v.image);

            // if(mposition == position) {
            //Text_Question_Event testQ = new Text_Question_Event();
            //testQ.setTestQ(text_questions.get(mposition));
            //text_questions.remove(questions);
            //   EventBus.getDefault().post(questions);
            //notifyDataSetChanged();
            // }

            v.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mposition = position;
                    //notifyDataSetChanged();
                    image_questions.remove(position);
                    notifyDataSetChanged();
                    EventBus.getDefault().post(image_questions);
                }
            });

            v.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Custom_Dialog_Edit_Image_Question edit = new Custom_Dialog_Edit_Image_Question(showTest, questions , image_questions , position);
                    edit.show();
                }
            });



            return convertView;
        }catch (Exception e) {
            return convertView;
        }
    }


}

