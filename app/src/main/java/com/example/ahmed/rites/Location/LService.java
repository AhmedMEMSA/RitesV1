package com.example.ahmed.rites.Location;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.rites.Activities.Home;
import com.example.ahmed.rites.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by ahmed on 6/20/2016.
 */
public class LService extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;


    private LocationRequest mLocationRequest;

    private static int UPDATE_INTERVAL = 10000;
    private static int FATEST_INTERVAL = 5000;
    private static int DISPLACEMENT = 10;


    private int id;

    public LService() {
        super("LService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("ABC", "Service running");
        id = intent.getIntExtra("id", 20);
        connect();
        checkNotify();

    }


    public void notifyMe(int n) {

        long vibernate[] = {0, 100, 500, 400};
        String rite = "";
        Intent intent = new Intent(getApplicationContext(), Home.class);
        if(n == 1){ rite = getString(R.string.Ihram);}
        else if ( n == 2 ){rite = getString(R.string.arriveMecca);}
        else if ( n == 3 ){rite = getString(R.string.twafKappa);}
        else if ( n == 4 ){rite = getString(R.string.alsae);}
        else if ( n == 5 ){rite = getString(R.string.cutHair);}
        else if ( n == 6 ){rite = getString(R.string.goMina);}
        else if ( n == 7 ){rite = getString(R.string.goArafa);}
        else if ( n == 8 ){rite = getString(R.string.goMuzdalfa);}
        else if ( n == 9 ){rite = getString(R.string.gmaratAkaba);}
        else if ( n == 10 ){rite = getString(R.string.nahrAladahy);}
        else if ( n == 11 ){rite = getString(R.string.backMina);}
        else if ( n == 12 ){rite = getString(R.string.twafWadaa);}
        else {return;}

        intent.putExtra("currentRite",rite);

        PendingIntent pin = PendingIntent.getActivity(getApplicationContext(), 101,intent , PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setTicker("Rites")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Its time to "+rite)
                .setContentText("Click to see updates")
                .setContentIntent(pin)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(vibernate);


        NotificationManager mngr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification not = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            not = builder.build();
        }
        mngr.notify(1, not);
        deleteNotify();


    }


    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longtitude = mLastLocation.getLongitude();
            setLocation(latitude, longtitude);
        } else {
        }
    }


    protected synchronized void connect() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        mGoogleApiClient.connect();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        displayLocation();
        stopLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("ABC", "Connection failed: " + connectionResult.getErrorCode());
    }

    public void setLocation(Double lat, Double lng) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/updatelocation";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", id + "").
                appendQueryParameter("lat", lat + "").
                appendQueryParameter("lng", lng + "").
                build();
        String url = uri.toString();
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("========================");
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jsonObject);
                        System.out.println(jsonObject.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public void checkNotify() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/selectnotification";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", id + "").
                build();
        String url = uri.toString();
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("========================");
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int ritenum = jsonObject.getInt("ritenum");
                            if (ritenum != 0){
                                notifyMe(ritenum);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(jsonObject);
                        System.out.println(jsonObject.toString());


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
    public void deleteNotify() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/updatenotification";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", id + "").
                build();
        String url = uri.toString();
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("========================");
                        System.out.println(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}
