package Model.Student;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.android.graphics.CanvasView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import BussinessObjects.Show_Test_BO.Image_Questions;
import BussinessObjects.Test_BO;
import Model.Session;
import Model.Teacher.New_Test_Content.New_Test_Custom_Text_Questions_Adapter;
import edu.plan9.naseemdev.naseem.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DELL on 10/22/2017.
 */

public class Custom_Dialog_Image_Draw_Question extends Dialog implements View.OnClickListener {
    private Activity activity;
    private Button cancel;
    private Button ok;
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

    CanvasView canvasView = null;
    Button redo  , erase;

    public Custom_Dialog_Image_Draw_Question(Activity a , Image_Questions image , ArrayList<Image_Questions> images  , int position) {
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
        setContentView(R.layout.custom_dialog_image_draw_question);

        ok = (Button)findViewById(R.id.dialog_ok);
        cancel = (Button)findViewById(R.id.dialog_cancel);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        canvasView = (CanvasView)this.findViewById(R.id.canvas);
        redo = (Button)findViewById(R.id.redo);
        erase = (Button)findViewById(R.id.erase);

        canvasView.setBaseColor(0xFF000000);
        String path = image1.getImage().getPath();
        final Bitmap icon = BitmapFactory.decodeFile(path);
        final Bitmap copyicon = icon;
        canvasView.drawBitmap(icon);

        canvasView.setPaintStrokeColor(Color.RED);
        canvasView.setPaintStrokeWidth(5F);


        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.redo();
            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.undo();
            }
        });
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        session = new Session(activity);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_ok: {
                Bitmap bitmap = canvasView.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                try {
                    FileOutputStream fos = new FileOutputStream(images.get(position).getImage());
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                }catch (FileNotFoundException e){

                }catch (IOException e){

                }
                Toast.makeText(activity.getApplicationContext() , "Image Answer is Added" , Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(images);
                dismiss();
                break;
            }
            case R.id.dialog_cancel: {
                dismiss();
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

