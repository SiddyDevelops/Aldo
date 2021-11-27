package com.siddydevelops.aldo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    VideoView videoView;
    String video_url = "https://www.youtube.com/watch?v=COZK7NATh4k";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.videoView);
        progressDialog = new ProgressDialog(VideoViewActivity.this);
        progressDialog.setMessage("Slow Internet Connectivity...");
        progressDialog.show();

        Uri uri = Uri.parse(video_url);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
            }
        });

    }
}