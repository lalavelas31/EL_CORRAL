package com.example.lauravelasquezcano.el_corral;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;


public class splash extends Activity {

    private static final long SPLASH_DELAY=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        TimerTask task= new TimerTask() {
            @Override
            public void run() {

                Intent mainIntent = new Intent().setClass(splash.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        Timer timer=new Timer();
        timer.schedule(task, SPLASH_DELAY);
    }
}
