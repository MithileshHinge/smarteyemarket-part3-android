package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Sibhali on 7/12/2017.
 */

public class NotifActivity extends AppCompatActivity {
    //public static ImageView jIVFrame;
    public static ImageView jIV;
    public static VideoView jVV;
    private static int videoNotifID;
    public static Context context;
    public static String servername;
    private Toolbar toolbar;
    private static SharedPreferences spref_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        context = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#F7E7CE"));

        spref_ip = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
        servername = spref_ip.getString("ip_address","");
        System.out.println("........................servername  " + servername);

        Intent intent = getIntent();
        String imageName = intent.getStringExtra("image_name");
        jIV = (ImageView) findViewById(R.id.xIV);
        jVV = (VideoView) findViewById(R.id.xVV);
        //jIVFrame = (ImageView) findViewById(R.id.xIVFrame);

        jVV.setMediaController(new MediaController(this));

        videoNotifID = intent.getIntExtra("video_notif_id", -1);

        if (videoNotifID != -1) {
            jIV.setImageResource(R.drawable.ic_file_download_24dp);
        }else if (imageName != null) {
            Bitmap frame = getImageBitmap(getApplicationContext(), imageName);
            jIV.setImageBitmap(frame);
        }

        if(videoNotifID != -1) {
            jIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socketVdo = new Socket(servername, 6668);
                                OutputStream outVdo = socketVdo.getOutputStream();
                                DataOutputStream doutVdo = new DataOutputStream(outVdo);
                                doutVdo.writeInt(videoNotifID);
                                doutVdo.flush();

                                InputStream inVdo = socketVdo.getInputStream();
                                DataInputStream dInVdo = new DataInputStream(inVdo);
                                int filenameSize = dInVdo.readInt();
                                byte[] filenameInBytes = new byte[filenameSize];
                                outVdo.write(1);
                                outVdo.flush();
                                inVdo.read(filenameInBytes);
                                final String filename = new String(filenameInBytes);
                                outVdo.write(1);
                                outVdo.flush();

                                FileOutputStream fileOut = openFileOutput(filename, MODE_PRIVATE);
                                byte[] buffer = new byte[16 * 1024];
                                int count;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        jIV.setImageResource(R.drawable.ic_schedule_24dp);
                                    }
                                });
                                while ((count = inVdo.read(buffer)) > 0) {
                                    fileOut.write(buffer, 0, count);
                                }
                                fileOut.close();
                                socketVdo.close();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Download successful.", Toast.LENGTH_LONG).show();
                                        jIV.setVisibility(View.GONE);
                                        String filepath = getFilesDir().getPath() + "/" + filename;
                                        System.out.println("^^^^^^^^^^^^^^^^folder path = " + getFilesDir().getPath() + "/" + filename);
                                        jVV.setVideoPath(filepath);
                                        jVV.setVisibility(View.VISIBLE);
                                        jVV.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) {
                                                jVV.start();
                                            }
                                        });
                                    }
                                });
                            } catch (IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "Download failed.", Toast.LENGTH_LONG).show();
                                    }
                                });
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }

       /* if (imageName != null){
            Bitmap frame = getImageBitmap(getApplicationContext(), imageName);
            //jIVFrame.setImageBitmap(frame);
        }*/
    }

    public Bitmap getImageBitmap(Context context, String name){
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
