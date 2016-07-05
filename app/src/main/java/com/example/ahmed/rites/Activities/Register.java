package com.example.ahmed.rites.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.rites.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private Button signup;
    private EditText nameET;
    private EditText passET;
    private EditText phoneET;
    private CheckBox leaderCB;
    private EditText followerET;
    private EditText hidET;
    private Intent returnIntent;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        signup = (Button)findViewById(R.id.rig_signup);
        nameET = (EditText)findViewById(R.id.rig_name);
        passET = (EditText)findViewById(R.id.rig_pass);
        phoneET = (EditText)findViewById(R.id.rig_phone);
        hidET = (EditText)findViewById(R.id.rig_hid);
        followerET = (EditText)findViewById(R.id.rig_fnum);
        leaderCB = (CheckBox)findViewById(R.id.rig_leader);

        returnIntent = new Intent();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String follower = "0";
                int leader = 0;
                if (leaderCB.isChecked()){
                    leader = 1;
                    follower = followerET.getText().toString();
                }
                String name = nameET.getText().toString();
                String pass = passET.getText().toString();
                String phone = phoneET.getText().toString();
                String hid = hidET.getText().toString();
                regist(name,pass,phone,hid,leader,follower,v);
            }
        });



    }
    public void regist(String name, String pass, String phone, String hid, int leader, String follower, final View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/registuser";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("name", name).
                appendQueryParameter("password", pass).
                appendQueryParameter("phone", phone).
                appendQueryParameter("hajjid", hid).
                appendQueryParameter("type", leader+"").
                appendQueryParameter("nfollower", follower).
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
                            id = jsonObject.getInt("id");
                            Snackbar s = Snackbar.make(view, "Your Rites ID is : "+id, Snackbar.LENGTH_LONG);
                            s.setAction("Done", null).show();
                            s.setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    returnIntent.putExtra("result",id);
                                    setResult(Activity.RESULT_OK,returnIntent);
                                    finish();
                                }

                                @Override
                                public void onShown(Snackbar snackbar) {
                                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            });


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

}
