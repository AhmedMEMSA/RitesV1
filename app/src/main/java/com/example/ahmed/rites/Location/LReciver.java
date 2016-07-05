package com.example.ahmed.rites.Location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ahmed on 6/20/2016.
 */
public class LReciver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.example.ahmed.rites.Location";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LService.class);
        i.putExtra("id", intent.getIntExtra("id", 20));
        Log.i("ABC", "Recevier running");
        context.startService(i);

    }
}
