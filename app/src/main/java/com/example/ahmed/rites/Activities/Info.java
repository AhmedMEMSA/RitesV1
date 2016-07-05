package com.example.ahmed.rites.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ahmed.rites.Pojo.Place;
import com.example.ahmed.rites.Pojo.Rite;
import com.example.ahmed.rites.R;

public class Info extends AppCompatActivity {


    private Rite Ihram;
    private Rite arriveMecca;
    private Rite twafKappa;
    private Rite alsae;
    private Rite cutHair;
    private Rite goMina;
    private Rite goArafa;
    private Rite goMuzdalfa;
    private Rite gmaratAkaba;
    private Rite nahrAladahy;
    private Rite backMina;
    private Rite twafWadaa;


    TextView infoTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        infoTV = (TextView) findViewById(R.id.info_tv);


        Ihram = new Rite(1);
        arriveMecca = new Rite(2);
        twafKappa = new Rite(3);
        alsae = new Rite(4);
        goMina = new Rite(5);
        goArafa = new Rite(6);
        goMuzdalfa = new Rite(7);
        gmaratAkaba = new Rite(8);
        nahrAladahy = new Rite(9);
        cutHair = new Rite(10);
        backMina = new Rite(11);
        twafWadaa = new Rite(12);

        Ihram.setName(getString(R.string.Ihram));
        Ihram.setInfo(getString(R.string.IhramINFO));
        arriveMecca.setName(getString(R.string.arriveMecca));
        arriveMecca.setInfo(getString(R.string.arriveMeccaINFO));
        twafKappa.setName(getString(R.string.twafKappa));
        twafKappa.setInfo(getString(R.string.twafKappaINFO));
        alsae.setName(getString(R.string.alsae));
        alsae.setInfo(getString(R.string.alsaeINFO));
        cutHair.setName(getString(R.string.cutHair));
        cutHair.setInfo(getString(R.string.cutHairINFO));
        goMina.setName(getString(R.string.goMina));
        goMina.setInfo(getString(R.string.goMinaINFO));
        goArafa.setName(getString(R.string.goArafa));
        goArafa.setInfo(getString(R.string.goArafaINFO));
        goMuzdalfa.setName(getString(R.string.goMuzdalfa));
        goMuzdalfa.setInfo(getString(R.string.goMuzdalfaINFO));
        gmaratAkaba.setName(getString(R.string.gmaratAkaba));
        gmaratAkaba.setInfo(getString(R.string.gmaratAkabaINFO));
        nahrAladahy.setName(getString(R.string.nahrAladahy));
        nahrAladahy.setInfo(getString(R.string.nahrAladahyINFO));
        backMina.setName(getString(R.string.backMina));
        backMina.setInfo(getString(R.string.backMinaINFO));
        twafWadaa.setName(getString(R.string.twafWadaa));
        twafWadaa.setInfo(getString(R.string.twafWadaaINFO));




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        if (i.getParcelableExtra("marker") != null) {
            Place marker = i.getParcelableExtra("marker");
            getSupportActionBar().setTitle(marker.getName());
            infoTV.setText(marker.getInfo());
        }
        if (i.getStringExtra("title") != null) {
            String title = i.getStringExtra("title");

            if (title.equals(Ihram.getName())) {
                getSupportActionBar().setTitle(Ihram.getName());
                infoTV.setText(Ihram.getInfo());
            } else if (title.equals(arriveMecca.getName())) {
                getSupportActionBar().setTitle(arriveMecca.getName());
                infoTV.setText(arriveMecca.getInfo());
            } else if (title.equals(twafKappa.getName())) {
                getSupportActionBar().setTitle(twafKappa.getName());
                infoTV.setText(twafKappa.getInfo());
            } else if (title.equals(alsae.getName())) {
                getSupportActionBar().setTitle(alsae.getName());
                infoTV.setText(alsae.getInfo());
            } else if (title.equals(goMina.getName())) {
                getSupportActionBar().setTitle(goMina.getName());
                infoTV.setText(goMina.getInfo());
            } else if (title.equals(goArafa.getName())) {
                getSupportActionBar().setTitle(goArafa.getName());
                infoTV.setText(goArafa.getInfo());
            } else if (title.equals(goMuzdalfa.getName())) {
                getSupportActionBar().setTitle(goMuzdalfa.getName());
                infoTV.setText(goMuzdalfa.getInfo());
            } else if (title.equals(gmaratAkaba.getName())) {
                getSupportActionBar().setTitle(gmaratAkaba.getName());
                infoTV.setText(gmaratAkaba.getInfo());
            } else if (title.equals(nahrAladahy.getName())) {
                getSupportActionBar().setTitle(nahrAladahy.getName());
                infoTV.setText(nahrAladahy.getInfo());
            } else if (title.equals(cutHair.getName())) {
                getSupportActionBar().setTitle(cutHair.getName());
                infoTV.setText(cutHair.getInfo());
            } else if (title.equals(backMina.getName())) {
                getSupportActionBar().setTitle(backMina.getName());
                infoTV.setText(backMina.getInfo());
            } else if (title.equals(twafWadaa.getName())) {
                getSupportActionBar().setTitle(twafWadaa .getName());
                infoTV.setText(twafWadaa.getInfo());
            }


        }




    }
}
