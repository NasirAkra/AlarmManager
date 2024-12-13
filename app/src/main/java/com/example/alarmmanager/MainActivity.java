package com.example.alarmmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    static final int ALARM_REQ_CODE=100;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.SetButton);
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);


        button.setOnClickListener(v -> {
            int time= Integer.parseInt(((EditText)(findViewById(R.id.edit))).getText().toString());
            long triggertime=System.currentTimeMillis()+(time* 1000L);
            Intent intent=new Intent(MainActivity.this,MyReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,ALARM_REQ_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_IMMUTABLE);

            alarmManager.set(AlarmManager.RTC_WAKEUP,triggertime,pendingIntent);


        });



    }
}