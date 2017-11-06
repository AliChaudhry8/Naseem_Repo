package Model.Teacher.Edit_Test_Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Image_Questions;
import BussinessObjects.Test_BO;
import Model.Image_Question_Event;
import Model.Session;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;
import edu.plan9.naseemdev.naseem.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DELL on 10/14/2017.
 */

public class Custom_Dialog_Edit_Image_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button edit1;
    private ImageView camera  , gallery , image;
    private Test_BO tests;
    EditText add_title , add_description , add_marks;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    New_Test_Custom_Text_Questions_Adapter text_q_adapter;
    Uri uri;
    Image_Questions image1;
    ArrayList<Image_Questions> images;
    int position;

    public Custom_Dialog_Edit_Image_Question(Activity a , Image_Questions image , ArrayList<Image_Questions> images , int position) {
        super(a);
        activity = a;
        //tests = u;
        //text_q_adapter = ad;
        this.image1 = image;
        this.images = images;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_edit_image_question);

      /*  MarshMallowPermission m = new MarshMallowPermission(activity);
        if(!m.checkPermissionForExternalStorage()){
            m.requestPermissionForExternalStorage();
        }
        if(!m.checkPermissionForCamera()){
            m.requestPermissionForCamera();
        }*/

        edit1 = (Button) findViewById(R.id.dialog_edit);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        camera = (ImageView)findViewById(R.id.add_image_camera);
        gallery = (ImageView)findViewById(R.id.add_image_gallery);
        image = (ImageView)findViewById(R.id.new_image);
        edit1.setOnClickListener(this);
        cancel.setOnClickListener(this);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        add_title = (EditText)findViewById(R.id.image_question_title);
        add_description = (EditText)findViewById(R.id.image_description);
        add_marks = (EditText)findViewById(R.id.image_marks);
        image.setVisibility(View.VISIBLE);
        if(image1 != null){
            add_title.setText(image1.getTitle());
            add_description.setText(image1.getDescription());
            add_marks.setText(""+image1.getMarks());
            Glide.with(activity).load(image1.getImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(image);
        }

        sharedPreferences = activity.getSharedPreferences("Images" , MODE_PRIVATE);
        edit = sharedPreferences.edit();
        if(sharedPreferences.getString("ImageURI" , null) != null){
            String img = sharedPreferences.getString("ImageURI" , null);
            edit.remove("ImageURI");
            uri = Uri.parse(img);
            image.setVisibility(View.VISIBLE);
            image.setImageURI(uri);
        }
        if(sharedPreferences.getString("Title" , null) != null){
            add_title.setText(sharedPreferences.getString("Title" , null));
            edit.remove("Title");
        }
        if(sharedPreferences.getString("Description" , null) != null){
            add_description.setText(sharedPreferences.getString("Description" , null));
            edit.remove("Description");
        }
        if(sharedPreferences.getString("Marks" , null) != null){
            add_marks.setText(sharedPreferences.getString("Marks" , null));
            edit.remove("Marks");
        }
        edit.commit();

        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

        progressDialog = new ProgressDialog(activity, R.style.AppTheme_Dark);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_edit: {
                Image_Questions image_questions = new Image_Questions();

                image_questions.setTitle(add_title.getText().toString());
                image_questions.setDescription(add_description.getText().toString());
                image_questions.setMarks(Integer.valueOf(add_marks.getText().toString()));
               /* String imagePath = "";
                if (uri.toString().contains("content:")) {
                    imagePath = getRealPathFromURI(uri);
                } else if (uri.toString().contains("file:")) {
                    imagePath = uri.getPath();
                } else {
                    imagePath = null;
                }*/
               if(image1 != null){
                   image_questions.setImage(image1.getImage());
               }
               else {
                   image_questions.setImage(new File(getRealPathFromURI(uri).toString()));
               }

                // txt.setId(-1);
               // Image_Question_Event images = new Image_Question_Event();
                //images.setImages(image_questions);
                images.set(position , image_questions);
                EventBus.getDefault().post(images);
                this.dismiss();
                /*Toast.makeText(activity.getApplicationContext(), "Date: " + txtDate.getText().toString() + " " + "Time: " + txtTime.getText().toString(), Toast.LENGTH_LONG).show();
                start_time = txtDate.getText().toString() + " " + txtTime.getText().toString();
                sharedPreferences = activity.getSharedPreferences("new_Test_Details", Context.MODE_PRIVATE);
                edit = sharedPreferences.edit();
                edit.putString("Test_Name", test_name.getText().toString());
                edit.putString("Time_Attempt", spinner.getSelectedItem().toString());
                edit.putString("Time_Start", start_time);
                edit.commit();
                progressDialog.setMessage("Creating Test. After That you will create Test Contents...");
                progressDialog.show();
                make_test();*/
                break;
            }
            case R.id.dialog_cancel: {
                edit.remove("Title");
                edit.remove("Description");
                edit.remove("ImageURI");
                edit.remove("Marks");
                edit.commit();
                dismiss();
                break;
            }
            case R.id.add_image_camera:{
                sharedPreferences = activity.getSharedPreferences("Images" , MODE_PRIVATE);
                edit = sharedPreferences.edit();
                if(add_title != null || !add_title.equals("")){
                    edit.putString("Title" , add_title.getText().toString());
                }
                if(add_description != null || !add_description.equals("")){
                    edit.putString("Description" , add_description.getText().toString());
                }
                if(add_marks != null || !add_marks.equals("")){
                    edit.putString("Marks" , add_marks.getText().toString());
                }
                edit.putString("position" , ""+position);
                Gson g = new Gson();
                String list = g.toJson(images);
                edit.putString("ArrayList" , list);
                edit.apply();
                takePicture();
                this.dismiss();
                break;
            }
            case R.id.add_image_gallery:{
                sharedPreferences = activity.getSharedPreferences("Images" , MODE_PRIVATE);
                edit = sharedPreferences.edit();
                if(add_title != null || !add_title.equals("")){
                    edit.putString("Title" , add_title.getText().toString());
                }
                if(add_description != null || !add_description.equals("")){
                    edit.putString("Description" , add_description.getText().toString());
                }
                if(add_marks != null || !add_marks.equals("")){
                    edit.putString("Marks" , add_marks.getText().toString());
                }
                edit.putString("position" , ""+position);
                Gson g = new Gson();
                String list = g.toJson(images);
                edit.putString("ArrayList" , list);
                edit.apply();
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Always show the chooser (if there are multiple options available)
                activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), 4);
                this.dismiss();
                break;
            }
            default: {
                dismiss();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor =  activity.managedQuery(contentUri, proj, null, null,null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e) {
            return contentUri.getPath();
        }
    }

    public void takePicture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, 5);
    }



}
