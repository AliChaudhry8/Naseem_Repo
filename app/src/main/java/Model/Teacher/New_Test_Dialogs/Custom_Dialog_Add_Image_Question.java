package Model.Teacher.New_Test_Dialogs;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

import BussinessObjects.Show_Test_BO.Image_Questions;
import BussinessObjects.Show_Test_BO.Text_Questions;
import BussinessObjects.Test_BO;
import Model.Image_Question_Event;
import Model.MarshMallowPermission;
import Model.Session;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;
import Model.Text_Question_Event;
import edu.plan9.naseemdev.naseem.R;

import static android.R.attr.bitmap;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by DELL on 10/13/2017.
 */

public class Custom_Dialog_Add_Image_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button add ;
    private ImageView camera  , gallery , image;
    private Test_BO tests;

    EditText add_title , add_description , add_marks;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    ProgressDialog progressDialog;
    New_Test_Custom_Text_Questions_Adapter text_q_adapter;
    Uri uri;

    public Custom_Dialog_Add_Image_Question(Activity a ) {
        super(a);
        activity = a;
        //tests = u;
        //text_q_adapter = ad;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_add_image_question);

      /*  MarshMallowPermission m = new MarshMallowPermission(activity);
        if(!m.checkPermissionForExternalStorage()){
            m.requestPermissionForExternalStorage();
        }
        if(!m.checkPermissionForCamera()){
            m.requestPermissionForCamera();
        }*/

        add = (Button) findViewById(R.id.dialog_add);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        camera = (ImageView)findViewById(R.id.add_image_camera);
        gallery = (ImageView)findViewById(R.id.add_image_gallery);
        image = (ImageView)findViewById(R.id.new_image);
        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        add_title = (EditText)findViewById(R.id.image_question_title);
        add_description = (EditText)findViewById(R.id.image_description);
        add_marks =  (EditText)findViewById(R.id.image_marks);


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
            case R.id.dialog_add: {
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
                image_questions.setImage(new File(getRealPathFromURI(uri).toString()));

                // txt.setId(-1);
                Image_Question_Event images = new Image_Question_Event();
                images.setImages(image_questions);
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
                edit.remove("Marks");
                edit.remove("ImageURI");
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
                edit.apply();
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Always show the chooser (if there are multiple options available)
                activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), 2);
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
        activity.startActivityForResult(cameraIntent, 3);
    }


}