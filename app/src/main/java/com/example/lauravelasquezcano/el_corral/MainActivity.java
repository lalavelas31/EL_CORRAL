package com.example.lauravelasquezcano.el_corral;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Puntos_de_venta puntos_de_venta= new Puntos_de_venta();
    private Publicidad publicidad=new Publicidad();
    private Mapa mapa=new Mapa();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(savedInstanceState==null){
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(android.R.id.content,publicidad).commit();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.m_puntos_de_venta:
                fragmentTransaction.replace(android.R.id.content, puntos_de_venta).commit();
                break;
            case R.id.m_mapa:
                fragmentTransaction.replace(android.R.id.content, mapa).commit();
                break;
            case R.id.m_publicidad:
                fragmentTransaction.replace(android.R.id.content, publicidad).commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
