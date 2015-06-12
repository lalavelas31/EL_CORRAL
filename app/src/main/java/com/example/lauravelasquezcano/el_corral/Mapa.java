package com.example.lauravelasquezcano.el_corral;

import android.app.Activity;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Mapa extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap map;
    private CameraUpdate cameraUpdate;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LatLng currentLocation;
    private LatLng medellin=new LatLng(6.270530, -75.57212);
    private FragmentManager manager;

    private boolean newLocationReady=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_mapa, container, false);
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        cameraUpdate=CameraUpdateFactory.newLatLngZoom(medellin,13);
        map.animateCamera(cameraUpdate);
        buildGoogleApiClient();
        createLocationRequest();
        Marcadores();


        return view;
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation!=null){
            setNewLocation(mLastLocation);
            newLocationReady=true;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        setNewLocation(location);
        newLocationReady=true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        manager = getFragmentManager();
        manager.beginTransaction().remove(manager.findFragmentById(R.id.map)).commit();


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected  synchronized void buildGoogleApiClient(){
        mGoogleApiClient=new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest(){
        mLocationRequest=new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void setNewLocation(Location location){
        double latitude=location.getLatitude();
        double longitude=location.getLongitude();

        currentLocation=new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(currentLocation).title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }

    }

    public void Marcadores (){

        String sede;
        Double latitud, longitud;
        LatLng ubicacion;
        int i;
        int number;

        DataBaseManager manager =new DataBaseManager(getActivity());
        Cursor cursor;

        try{
            cursor=manager.cargarCursorSedes();
            cursor.moveToFirst();
            number=cursor.getCount();
            for (i=1;i<=number;i++){
                sede=cursor.getString(cursor.getColumnIndex(manager.CN_NAME));
                latitud=cursor.getDouble(cursor.getColumnIndex(manager.CN_LATITUD));
                longitud=cursor.getDouble(cursor.getColumnIndex(manager.CN_LONGITUD));
                ubicacion=new LatLng(latitud,longitud );
                map.addMarker(new MarkerOptions().position(ubicacion).title(sede)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                cursor.moveToNext();
            }


        }catch (CursorIndexOutOfBoundsException e){
            Toast.makeText(getActivity(), "No existen datos en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}
