package com.example.telerehabilitationpatientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class ExerciseActivity extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise2);
        context = this;

        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        File video = new File(Environment.getExternalStorageDirectory(), "ejercicio.mp4");
        // videoView.setVideoPath("http://192.168.1.3:8081/ejercicio1.mp4");
        MediaController mediaController = new MediaController(context);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);

        Button play = findViewById(R.id.play_exercise);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayExerciseActivity.class);
                setResult(Activity.RESULT_OK);
                startActivity(intent);
            }
        });
    }
}
