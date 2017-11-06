package edu.plan9.naseemdev.naseem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.graphics.CanvasView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.itextpdf.text.pdf.codec.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Draw extends AppCompatActivity {

    CanvasView canvasView = null;
    Button draw  , erase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        canvasView = (CanvasView)this.findViewById(R.id.canvas);
        draw = (Button)findViewById(R.id.draw);
        erase = (Button)findViewById(R.id.erase);

        canvasView.setBaseColor(0xFF000000);
        final Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.b2);
        canvasView.drawBitmap(icon);

        canvasView.setPaintStrokeColor(Color.RED);
        canvasView.setPaintStrokeWidth(5F);


        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.drawBitmap(icon);
                canvasView.setMode(CanvasView.Mode.DRAW);
                canvasView.setDrawer(CanvasView.Drawer.PEN);
            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.setMode(CanvasView.Mode.ERASER);
               // canvasView.clear();
                //canvasView.drawBitmap(icon);
            }
        });


        //canvasView.setOpacity(0);
        //canvasView.setBackgroundResource(R.drawable.b3);


    }
}
