package com.example.ahmed.rites.Location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.rites.Activities.Help;
import com.example.ahmed.rites.Activities.Home;
import com.example.ahmed.rites.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabstart = (FloatingActionButton) findViewById(R.id.fabstart);
        FloatingActionButton fabstop = (FloatingActionButton) findViewById(R.id.fabstop);
        fabstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                scheduleAlarm();
            }
        });

        fabstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Stop", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent i = new Intent(LTest.this, Home.class);
//                startActivity(i);
                cancelAlarm();

            }
        });
    }

    public void scheduleAlarm() {
        Intent intent = new Intent(getApplicationContext(), LReciver.class);
        intent.putExtra("id",22);
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
