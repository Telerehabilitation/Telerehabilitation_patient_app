package com.example.telerehabilitationpatientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TherapyActivity extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy);
        final Button buttonTherapy = findViewById(R.id.button_therapy);
        buttonTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoutineActivity.class);
                setResult(Activity.RESULT_OK);
                startActivity(intent);
            }
        });
    }
}
