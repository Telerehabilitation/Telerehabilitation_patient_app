package com.example.telerehabilitationpatientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class PlayExerciseActivity extends AppCompatActivity implements SensorEventListener {

    Context context;
    public SensorManager sensorManager;
    public Sensor accelerometer, gyroscope, gravitySensor;
    boolean capturing = false;
    private long nextLecture;
    private long lectureGap = 1000/50;

    private class SensorData {
        public Long time;
        public float x;
        public float y;
        public float z;

        public SensorData(Long time, float x, float y, float z) {
            this.time = time;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private ArrayList<SensorData> accelerometer_data = new ArrayList<>();
    private ArrayList<SensorData> gyroscope_data = new ArrayList<>();
    private ArrayList<SensorData> gravitySensor_data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    },
                    1
            );
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_exercise);

        final TextView info = findViewById(R.id.info);
        final Button start = findViewById(R.id.button2);
        final Button stop = findViewById(R.id.stop);
        context = this;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturing = !capturing;
                if (capturing) {
                    info.setText("Capturando información...");
                    start.setText("Detener captura de información");
                } else {
                    info.setText("En reposo...");
                    start.setText("Comenzar captura de información");
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturing = false;
                info.setText("Procesando la información...");
                String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                StringBuilder filepath = new StringBuilder();
                filepath.append(baseDir);
                filepath.append(File.separator);
                filepath.append(System.currentTimeMillis());


                // acc
                File accelerometer_file = new File(filepath.toString().concat("acc.csv"));
                if(!accelerometer_file.exists() && !accelerometer_file.isDirectory()) {
                    try {
                        accelerometer_file.createNewFile();
                    } catch (IOException e) {
                        Log.d("ERROR", e.getMessage());
                    }
                }

                try {
                    FileWriter accWriter = new FileWriter(accelerometer_file.getAbsoluteFile());
                    BufferedWriter accBWriter = new BufferedWriter(accWriter);
                    for(SensorData data : accelerometer_data) {
                        StringBuilder dataBuilder = new StringBuilder();
                        dataBuilder.append(data.time);
                        dataBuilder.append(",");
                        dataBuilder.append(data.x);
                        dataBuilder.append(",");
                        dataBuilder.append(data.y);
                        dataBuilder.append(",");
                        dataBuilder.append(data.z);
                        accBWriter.write(dataBuilder.toString());
                        accBWriter.newLine();
                    }
                    accBWriter.close();
                } catch (IOException e) {
                    Log.d("ERROR", e.getMessage());
                }

                // gyr
                File gyroscope_file = new File(filepath.toString().concat("gyr.csv"));
                if(!gyroscope_file.exists() && !gyroscope_file.isDirectory()) {
                    try {
                        gyroscope_file.createNewFile();
                    } catch (IOException e) {
                        Log.d("ERROR", e.getMessage());
                    }
                }

                try {
                    FileWriter gyrWriter = new FileWriter(gyroscope_file.getAbsoluteFile());
                    BufferedWriter gyrBWriter = new BufferedWriter(gyrWriter);
                    for(SensorData data : gyroscope_data) {
                        StringBuilder dataBuilder = new StringBuilder();
                        dataBuilder.append(data.time);
                        dataBuilder.append(",");
                        dataBuilder.append(data.x);
                        dataBuilder.append(",");
                        dataBuilder.append(data.y);
                        dataBuilder.append(",");
                        dataBuilder.append(data.z);
                        gyrBWriter.write(dataBuilder.toString());
                        gyrBWriter.newLine();
                    }
                    gyrBWriter.close();
                } catch (IOException e) {
                    Log.d("ERROR", e.getMessage());
                }

                // grav
                File gravitySensor_file = new File(filepath.toString().concat("grav.csv"));
                if(!gravitySensor_file.exists() && !gravitySensor_file.isDirectory()) {
                    try {
                        gravitySensor_file.createNewFile();
                    } catch (IOException e) {
                        Log.d("ERROR", e.getMessage());
                    }
                }

                try {
                    FileWriter gravWriter = new FileWriter(gravitySensor_file.getAbsoluteFile());
                    BufferedWriter gravBWriter = new BufferedWriter(gravWriter);
                    for(SensorData data : gravitySensor_data) {
                        StringBuilder dataBuilder = new StringBuilder();
                        dataBuilder.append(data.time);
                        dataBuilder.append(",");
                        dataBuilder.append(data.x);
                        dataBuilder.append(",");
                        dataBuilder.append(data.y);
                        dataBuilder.append(",");
                        dataBuilder.append(data.z);
                        gravBWriter.write(dataBuilder.toString());
                        gravBWriter.newLine();
                    }
                    gravBWriter.close();
                } catch (IOException e) {
                    Log.d("ERROR", e.getMessage());
                }
                info.setText("¡Listo!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        long millis = System.currentTimeMillis();
        if (millis >= nextLecture && capturing) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometer_data.add(new SensorData(millis, event.values[0], event.values[1], event.values[2]));
            } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                gyroscope_data.add(new SensorData(millis, event.values[0], event.values[1], event.values[2]));
            } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
                gravitySensor_data.add(new SensorData(millis, event.values[0], event.values[1], event.values[2]));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
