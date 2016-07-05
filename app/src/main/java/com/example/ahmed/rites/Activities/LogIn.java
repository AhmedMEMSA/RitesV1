package com.example.ahmed.rites.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ahmed.rites.Location.LTest;
import com.example.ahmed.rites.Pojo.User;
import com.example.ahmed.rites.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

    private Button login;
    private Button signup;
    private User user;
    private EditText id;
    private EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_log_in);

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        id = (EditText) findViewById(R.id.id);
        pass = (EditText) findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().equals("0")) {
                    Intent i = new Intent(LogIn.this, HomeFollower.class);
                    i.putExtra("userID",Integer.parseInt(id.getText().toString()));
                    startActivity(i);
                    finish();
                }
                if (id.getText().toString().equals(pass.getText().toString())) {
                    Intent i = new Intent(LogIn.this, Home.class);
                    i.putExtra("userID",Integer.parseInt(id.getText().toString()));
                    startActivity(i);
                    finish();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this, Register.class);
                startActivityForResult(i,111);
            }
        });

    }

    public void login() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/loginuser";

        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", "2").
                appendQueryParameter("password", "123").
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
                            int result = jsonObject.getInt("result");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 111) {
            if(resultCode == Activity.RESULT_OK){
                id.setText(data.getIntExtra("result",0)+"");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


}
