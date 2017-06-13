package com.example.sibhali.facedet;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class DisplayImageActivity extends AppCompatActivity {
    public static ImageView img;
    public static boolean frameChanged = false;
    public static Bitmap frame = null;
    private static Client client;
    private static AudioClient audioClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        img = (ImageView) findViewById(R.id.imageView);
        ToggleButton jTB = (ToggleButton) findViewById(R.id.toggleButton);

        client = new Client();
        client.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (frameChanged) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                img.setImageBitmap(frame);
                            }
                        });
                        frameChanged = false;
                    }
                }
            }
        });
        t2.start();

        jTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    audioClient = new AudioClient();
                    audioClient.start();
                }else {
                    audioClient.status = false;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        client.running = false;
        audioClient.status = false;
        super.onPause();
    }
}
