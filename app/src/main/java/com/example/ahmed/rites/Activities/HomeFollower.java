package com.example.ahmed.rites.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ahmed.rites.Location.LReciver;
import com.example.ahmed.rites.R;

public class HomeFollower extends AppCompatActivity {


    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_follower);

        Intent comingIntnet = getIntent();

        if(comingIntnet.getIntExtra("userID",0)!=0){
            userID = comingIntnet.getIntExtra("userID",0);
        }

    }

    @Override
    protected void onResume() {
        scheduleAlarm();
        super.onResume();
    }

    public void scheduleAlarm() {

        Intent intent = new Intent(getApplicationContext(), LReciver.class);
        intent.putExtra("id",userID);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LReciver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis()+1000;
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 20000L, pIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), LReciver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, LReciver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

}
