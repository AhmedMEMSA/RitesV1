package com.example.ahmed.rites.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.rites.Database.DBHelper;
import com.example.ahmed.rites.Database.Data;
import com.example.ahmed.rites.Location.LReciver;
import com.example.ahmed.rites.Pojo.Friend;
import com.example.ahmed.rites.Pojo.Place;
import com.example.ahmed.rites.Pojo.User;
import com.example.ahmed.rites.R;
import com.example.ahmed.rites.adapters.FriendsAdapter;
import com.example.ahmed.rites.adapters.RecyclerItemClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean mapClicked;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private Button buttonBack;

    private RecyclerView fRecyclerView;
    private FriendsAdapter fAdapter;
    private RecyclerView.LayoutManager fLayoutManager;

    private LinearLayout addFriendLayout, listFriendLayout;
    private SharedPreferences data;
    private SharedPreferences.Editor dataE;
    private String cp;
    private EditText friendName, friendID;
    private Button add_friend;

    private CardView addCV;


    SQLiteDatabase db;
    DBHelper dbHelper;


    private Place zuAlHolifa;
    private Place alJuhfa;
    private Place zatEarq;
    private Place qarnAlmanazil;
    private Place yalamlam;
    private Place mecca;
    private Place kappa;
    private Place alSafaWalMarwa;
    private Place mina;
    private Place arafa;
    private Place muzdalfa;
    private ArrayList<Friend> friends;
    private int userID;
    MarkerOptions tempMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        friends = new ArrayList<>();
        fRecyclerView = (RecyclerView) findViewById(R.id.friends_recycler_view);
        fRecyclerView.setHasFixedSize(true);
        fLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fRecyclerView.setLayoutManager(fLayoutManager);
        fAdapter = new FriendsAdapter();
        fRecyclerView.setAdapter(fAdapter);


        buttonBack = (Button) findViewById(R.id.button_back);
        addFriendLayout = (LinearLayout) findViewById(R.id.layout_add_friend);
        listFriendLayout = (LinearLayout) findViewById(R.id.layout_list_friend);
        addCV = (CardView) findViewById(R.id.card_add);
        friendID = (EditText) findViewById(R.id.friend_id);
        friendName = (EditText) findViewById(R.id.friend_name);
        add_friend = (Button) findViewById(R.id.button_add);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();


        data = getSharedPreferences("data", MODE_PRIVATE);
        dataE = data.edit();
        dataE.commit();

        Intent comingIntnet = getIntent();

        if(comingIntnet.getIntExtra("userID",0)!=0){
            userID = comingIntnet.getIntExtra("userID",0);
            dataE.putInt("userID", userID);
            dataE.commit();
        }
        if(comingIntnet.getStringExtra("currentRite") != null){
            dataE.putString("currentRite", comingIntnet.getStringExtra("currentRite"));
            dataE.commit();
        }else {
            dataE.putString("currentRite", getString(R.string.Ihram));
        dataE.commit();
        }

            cp = data.getString("currentRite", null);




        zuAlHolifa = new Place();
        alJuhfa = new Place();
        zatEarq = new Place();
        qarnAlmanazil = new Place();
        yalamlam = new Place();
        mecca = new Place();
        kappa = new Place();
        alSafaWalMarwa = new Place();
        mina = new Place();
        arafa = new Place();
        muzdalfa = new Place();

        zuAlHolifa.setName(getString(R.string.zuAlHolifa));
        zuAlHolifa.setInfo(getString(R.string.zuAlHolifaINFO));
        zuAlHolifa.setLocation(new LatLng(24.5413569, 39.5545469));

        alJuhfa.setName(getString(R.string.alJuhfa));
        alJuhfa.setInfo(getString(R.string.alJuhfaINFO));
        alJuhfa.setLocation(new LatLng(22.5705187, 39.5146859));


        zatEarq.setName(getString(R.string.zatEarq));
        zatEarq.setInfo(getString(R.string.zatEarqINFO));
        zatEarq.setLocation(new LatLng(21.5929363, 40.5425791));


        qarnAlmanazil.setName(getString(R.string.qarnAlmanazil));
        qarnAlmanazil.setInfo(getString(R.string.qarnAlmanazilINFO));
        qarnAlmanazil.setLocation(new LatLng(21.5633232, 40.5427471));


        yalamlam.setName(getString(R.string.yalamlam));
        yalamlam.setInfo(getString(R.string.yalamlamINFO));
        yalamlam.setLocation(new LatLng(22.5705187, 39.5910418));


        mecca.setName(getString(R.string.mecca));
        mecca.setInfo(getString(R.string.meccaINFO));
        mecca.setLocation(new LatLng(21.389082, 39.857912));


        kappa.setName(getString(R.string.kappa));
        kappa.setInfo(getString(R.string.kappaINFO));
        kappa.setLocation(new LatLng(21.422507, 39.826209));


        alSafaWalMarwa.setName(getString(R.string.alSafaWalMarwa));
        alSafaWalMarwa.setInfo(getString(R.string.alSafaWalMarwaINFO));
        alSafaWalMarwa.setLocation(new LatLng(21.423499, 39.827418));

        mina.setName(getString(R.string.mina));
        mina.setInfo(getString(R.string.minaINFO));
        mina.setLocation(new LatLng(21.414605, 39.894564));

        arafa.setName(getString(R.string.arafa));
        arafa.setInfo(getString(R.string.arafaINFO));
        arafa.setLocation(new LatLng(21.354885, 39.984116));

        muzdalfa.setName(getString(R.string.muzdalfa));
        muzdalfa.setInfo(getString(R.string.muzdalfaINFO));
        muzdalfa.setLocation(new LatLng(21.387839, 39.914466));


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(cp);


        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Info.class);
                i.putExtra("title", cp);
                startActivity(i);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Home.this, Help.class));
                Intent i = new Intent(Home.this, Help.class);
                User user = new User();
                i.putExtra("dfd", "sds");
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFriendLayout.setVisibility(View.VISIBLE);
                addFriendLayout.setVisibility(View.INVISIBLE);

                showFriends();

            }
        });

        addCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFriendLayout.setVisibility(View.INVISIBLE);
                addFriendLayout.setVisibility(View.VISIBLE);
            }
        });

        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNewFriend();


            }
        });


        fRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.SimpleOnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                        Friend item = friends.get(position);
                        getFriendLocation(item);
                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {
                        super.onItemLongPress(childView, position);
                        Friend item = friends.get(position);
                        int delete = db.delete(Data.FRIENDS_TABLE_NAME, Data.FKEY_ID + "=" + item.getId(), null);
                        if (delete > 0) {
                            showFriends();
                            Toast.makeText(Home.this, "deleted", Toast.LENGTH_SHORT).show();

                        }
                    }
                }));


    }

    public void getFriendLocation(final Friend item) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/selectlocation";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", item.getId()+"").
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
                            Double lat = jsonObject.getDouble("lat");
                            Double lng = jsonObject.getDouble("lng");
                            tempMarker = new MarkerOptions();
                            tempMarker.position(new LatLng(lat,lng));
                            tempMarker.title(item.getName());
                            mMap.addMarker(tempMarker);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 20), 2000, null);
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


    private void showFriends() {
        friends.clear();
        String[] projection = {
                Data.FKEY_ID,
                Data.FKEY_NAME
        };


        Cursor c = db.query(
                Data.FRIENDS_TABLE_NAME,
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        c.moveToFirst();
        while (!c.isAfterLast()) {
            Friend f = new Friend();
            f.setId(c.getInt(0));
            f.setName(c.getString(1));
            friends.add(f);
            c.moveToNext();
        }
        c.close();

        fAdapter.setItems(friends);
    }


    private void saveNewFriend() {

        if (!friendID.getText().toString().isEmpty() &&
                !friendName.getText().toString().isEmpty()) {


            String id = friendID.getText().toString();
            String name = friendName.getText().toString();

            ContentValues values = new ContentValues();
            values.put(Data.FKEY_ID, id);
            values.put(Data.FKEY_NAME, name);


            long row = db.insert(Data.FRIENDS_TABLE_NAME, null, values);
            if (row > 0) {
                Toast.makeText(Home.this, "Done", Toast.LENGTH_SHORT).show();
                friendID.setText("");
                friendName.setText("");
            } else {
                Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void onResume() {
        cp = data.getString("currentRite", null);
        getSupportActionBar().setTitle(cp);

        showFriends();

        super.onResume();
    }

    @Override
    protected void onPause() {
        tempMarker = new MarkerOptions();
        tempMarker.visible(false);
        cancelAlarm();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        scheduleAlarm();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mecca.getLocation(), 14));

        mMap.addMarker(new MarkerOptions().position(zuAlHolifa.getLocation()).title(zuAlHolifa.getName()));
        mMap.addMarker(new MarkerOptions().position(alJuhfa.getLocation()).title(alJuhfa.getName()));
        mMap.addMarker(new MarkerOptions().position(zatEarq.getLocation()).title(zatEarq.getName()));
        mMap.addMarker(new MarkerOptions().position(qarnAlmanazil.getLocation()).title(qarnAlmanazil.getName()));
        mMap.addMarker(new MarkerOptions().position(yalamlam.getLocation()).title(yalamlam.getName()));
        mMap.addMarker(new MarkerOptions().position(mecca.getLocation()).title(mecca.getName()));
        mMap.addMarker(new MarkerOptions().position(kappa.getLocation()).title(kappa.getName()));
        mMap.addMarker(new MarkerOptions().position(alSafaWalMarwa.getLocation()).title(alSafaWalMarwa.getName()));
        mMap.addMarker(new MarkerOptions().position(mina.getLocation()).title(mina.getName()));
        mMap.addMarker(new MarkerOptions().position(arafa.getLocation()).title(arafa.getName()));
        mMap.addMarker(new MarkerOptions().position(muzdalfa.getLocation()).title(muzdalfa.getName()));


        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }
        mMap.setPadding(0, 130, 0, 0);
        mapClicked = false;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mapClicked) {
                    getSupportActionBar().show();
                    fab.show();
                    mMap.setPadding(0, 130, 0, 0);
                    mapClicked = false;

                } else {
                    getSupportActionBar().hide();
                    fab.hide();
                    mMap.setPadding(0, -130, 0, 0);
                    mapClicked = true;

                }
            }
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent i = new Intent(Home.this, Info.class);

                if (marker.getTitle().equals(zuAlHolifa.getName())) {
                    i.putExtra("marker", zuAlHolifa);
                } else if (marker.getTitle().equals(alJuhfa.getName())) {
                    i.putExtra("marker", alJuhfa);
                } else if (marker.getTitle().equals(zatEarq.getName())) {
                    i.putExtra("marker", zatEarq);
                } else if (marker.getTitle().equals(qarnAlmanazil.getName())) {
                    i.putExtra("marker", qarnAlmanazil);
                } else if (marker.getTitle().equals(yalamlam.getName())) {
                    i.putExtra("marker", yalamlam);
                } else if (marker.getTitle().equals(mecca.getName())) {
                    i.putExtra("marker", mecca);
                } else if (marker.getTitle().equals(kappa.getName())) {
                    i.putExtra("marker", kappa);
                } else if (marker.getTitle().equals(alSafaWalMarwa.getName())) {
                    i.putExtra("marker", alSafaWalMarwa);
                } else if (marker.getTitle().equals(mina.getName())) {
                    i.putExtra("marker", mina);
                } else if (marker.getTitle().equals(arafa.getName())) {
                    i.putExtra("marker", arafa);
                } else if (marker.getTitle().equals(muzdalfa.getName())) {
                    i.putExtra("marker", muzdalfa);
                }

                startActivity(i);

            }

        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 20), 2000, null);
            }
        });

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_logout) {
            cancelAlarm();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        return true;
    }


}
