package com.example.telerehabilitationpatientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoutineActivity extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        Button buttonRoutine = findViewById(R.id.button_routine);
        buttonRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                setResult(Activity.RESULT_OK);
                startActivity(intent);
            }
        });
    }
}
