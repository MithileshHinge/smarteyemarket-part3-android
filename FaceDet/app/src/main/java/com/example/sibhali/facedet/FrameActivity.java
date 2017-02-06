package com.example.sibhali.facedet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;

public class FrameActivity extends AppCompatActivity {

    public static ImageView jIVFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        Intent intent = getIntent();
        String imageName = intent.getStringExtra("image_name");

        jIVFrame = (ImageView) findViewById(R.id.xIVFrame);

        if (imageName != null){
            Bitmap frame = getImageBitmap(getApplicationContext(), imageName);
            jIVFrame.setImageBitmap(frame);
        }
    }

    public Bitmap getImageBitmap(Context context,String name){
        name=name+".jpg";
        try{
            FileInputStream fis = context.openFileInput(name);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        }
        catch(Exception e){
        }
        return null;
    }
}
