package com.hfad.zpiapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import java.io.Console;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Glowna extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    Dialog coordinatesDialog;
    Uzytkownik user;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    DatabaseReference mDatabase;
    ArrayList<Zagadka> zagadkiLista = new ArrayList<Zagadka>();
    protected LocationManager locationManager;
    private String provider;
    Dialog doWszystkiego;

    Powiadomienie powiadomienie;

    @SuppressLint("WrongConstant")
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user=(Uzytkownik)getIntent().getSerializableExtra("Uzytkownik");
      //  user.odznaki.set(0,1);
        //user.uaktualnijWBazie();
        System.out.println(user.login);
        //Toast.makeText(this,user.login+" "+user.odznaki.size(),Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_glowna);

        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Choosing the best criteria depending on what is available.
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        coordinatesDialog = new Dialog(this);
        coordinatesDialog.setCancelable(true);
        coordinatesDialog.setCanceledOnTouchOutside(false);


        initMap();
       /* SprawdzZdjecie sz = new SprawdzZdjecie(this);
        sz.execute();*/
        powiadomienie = new Powiadomienie(this);
        //powiadomienie.sendNotificationWithIntent("Tytuł","Opis");

        ZagadkaReader zagadkaReader = new ZagadkaReader();
        zagadkaReader.readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Zagadka> zag) {
                Log.w("ktorereader", zag.get(0).getNazwa());
                zagadkiLista=zag;
                drawMaps();
            }
        });
        Log.w("ZAGADKI", "" +zagadkiLista.size());

    }

    public void drawMaps(){
        mMap.setOnMarkerClickListener(this);
        for(int i=0;i<zagadkiLista.size();i++){
            Log.w("lista_punktow",zagadkiLista.get(i).toString());
            LatLng point = new LatLng(zagadkiLista.get(i).wspolrzednaLat, zagadkiLista.get(i).wspolrzednaLng);
            MarkerOptions markerOptions = new MarkerOptions().position(point).title(zagadkiLista.get(i).nazwa);

            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(i);

            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(point);
            circleOptions.radius(50);
            circleOptions.strokeColor(Color.BLACK);
            circleOptions.fillColor(0x0ff000);
            circleOptions.strokeWidth(1);
            mMap.addCircle(circleOptions);
        }
    }

    public void settingsMethod(View view)
    {
        final Intent settingsIntent=new Intent(this,Ustawienia.class);
        startActivity(settingsIntent);
    }

    public void userMethod(View view)
    {
        final Intent userIntent=new Intent(this,KontoUzytkownika.class);
        startActivity(userIntent);
    }

    public void coordinatesMethod(View view)
    {
        coordinatesDialog.setContentView(R.layout.custom_popup_coordinates);
        coordinatesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button closeDialog = (Button) coordinatesDialog.findViewById(R.id.closeCoordinates);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatesDialog.dismiss();
            }
        });
        coordinatesDialog.show();

    }

    public void onBackPressed()
    {
        if(coordinatesDialog.isShowing())
        {
            coordinatesDialog.dismiss();
            return;
        }
        super.onBackPressed();
    }

    public void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapGoogle);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
        Log.d("Glowna","onMapReady: ready");
        mMap = googleMap;
        if(checkSelfPermission("ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Glowna.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            mMap.setMyLocationEnabled(true);
        }


        LatLng wroclaw = new LatLng(51.105171, 17.037821);
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, zoomLevel));
        mMap.getUiSettings().setZoomGesturesEnabled(true);

       // obsluga.start(mMap);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
            Log.println(Log.ASSERT, "Reasuming", "End");
        }
        catch(SecurityException e)
        {
            Log.println(Log.ASSERT, "Reasuming", "PermissionNot");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        float zoomLevel =14.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoomLevel));
        Toast.makeText(getApplicationContext(),"Location changed",Toast.LENGTH_SHORT).show();


        for(int i = 0; i < zagadkiLista.size(); i++)
        {
            if(zagadkiLista.get(i).czyNaMiejscu(location.getLatitude() + ","+ location.getLongitude()))
            {
                /*LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                PopupWindow pw = zagadkiLista.get(i).showPopUp(inflater);
                pw.showAtLocation(this.findViewById(R.id.myMainLayout), Gravity.CENTER, 0, 0);*/
                doWszystkiego = new Dialog(this);
                zagadkiLista.get(i).showPopUp(doWszystkiego);
            }
        }
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
    public boolean onMarkerClick(Marker marker) {
        int ktory = (int) marker.getTag();
        //TO DO Location check
           /* LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            PopupWindow pw = zagadkiLista.get(ktory).showPopUp(inflater);
            pw.showAtLocation(this.findViewById(R.id.myMainLayout), Gravity.CENTER, 0, 0);
*/
        doWszystkiego = new Dialog(this);
        zagadkiLista.get(ktory).showPopUp(doWszystkiego);
        return false;
    }
}
