package com.hfad.zpiapp;
/*
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
    private String provider="";
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
/*        powiadomienie = new Powiadomienie(this);
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
 /*               doWszystkiego = new Dialog(this);
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
*//*
        doWszystkiego = new Dialog(this);
        zagadkiLista.get(ktory).showPopUp(doWszystkiego);
        return false;
    }
}*/
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.KeyguardManager;
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
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import java.io.Console;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import static android.graphics.Color.TRANSPARENT;

public class Glowna extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener, LifecycleObserver {
    Dialog coordinatesDialog;
    Odtwarzacz sound;
    Uzytkownik user;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    DatabaseReference mDatabase;
    ArrayList<Zagadka> zagadkiLista = new ArrayList<Zagadka>();
    protected LocationManager locationManager;
    private String provider;
    Dialog doWszystkiego;
    Dialog badAnswerDialog;
    Dialog congratulationsDialog;
    Dialog curiosityDialog;
    boolean popUpSemafor=false;
    static final int REQUEST_IMAGE_CAPTURE = 1; ////////////////////////////////////////////////////////////////do pobierania obrazu
    Bitmap myPhoto; ///////////////////////////////////////////////////////////////////////////////////////////trzymacz obrazu
    String obecneWspolrzedne;
    int obecnaZagadka = 0;
    boolean isInBackground;
    Powiadomienie powiadomienie;

    @SuppressLint("WrongConstant")
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sound= new Odtwarzacz(this.getApplicationContext());
        popUpSemafor=false;
        user=(Uzytkownik)getIntent().getSerializableExtra("Uzytkownik");
        //  user.odznaki.set(0,1);
        //user.uaktualnijWBazie();
       // System.out.println(user.login);
        //Toast.makeText(this,user.login+" "+user.odznaki.size(),Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_glowna);

        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Choosing the best criteria depending on what is available.
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
            Log.println(Log.ASSERT, "Reasuming", "End");
        }
        catch(SecurityException e)
        {
            Log.println(Log.ASSERT, "Reasuming", "PermissionNot");
        }

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        coordinatesDialog = new Dialog(this);
        coordinatesDialog.setCancelable(true);
        coordinatesDialog.setCanceledOnTouchOutside(false);

        doWszystkiego = new Dialog(this);


        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        initMap();

        powiadomienie = new Powiadomienie(this);

        ZagadkaWybor zw=new ZagadkaWybor(doWszystkiego);
        ZagadkaReader zagadkaReader = new ZagadkaReader();
        zagadkaReader.readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Zagadka> zag) {
                Log.w("ktorereader", zag.get(0).getNazwa());
                zagadkiLista=zag;
              //  drawMaps();
                drawMapsStartowe();
            }
        });
        Log.w("ZAGADKI", "" +zagadkiLista.size());

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        // app moved to foreground
        isInBackground=false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        // app moved to background
        isInBackground =true;
    }


    public void drawMaps(){
        mMap.setOnMarkerClickListener(this);
        for(int i=0;i<zagadkiLista.size();i++){
           // Log.w("lista_punktow",zagadkiLista.get(i).toString());
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
    public void drawMapsStartowe(){
        mMap.setOnMarkerClickListener(this);
        Bitmap icon=BitmapFactory.decodeResource(this.getResources(),R.drawable.marker20001);
        icon=Bitmap.createScaledBitmap(icon,207,115,false);
        for(int i=0;i<zagadkiLista.size();i++) {
          //  if(user.jestWAktywnych(zagadkiLista.get(i).index)){             //Mozna usunac zeby były wszystkie zagadki////////////////////////////////
                LatLng point = new LatLng(zagadkiLista.get(i).wspolrzednaLat, zagadkiLista.get(i).wspolrzednaLng);
                MarkerOptions markerOptions = new MarkerOptions().position(point).title(zagadkiLista.get(i).nazwa).icon(BitmapDescriptorFactory.fromBitmap(icon));

                Marker marker = mMap.addMarker(markerOptions);

                marker.setTag(i);
                //}
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(point);
                circleOptions.radius(50);
                circleOptions.strokeColor(Color.BLACK);
                circleOptions.fillColor(Color.argb(75,51,153,255));
                circleOptions.strokeWidth(1);
                circleOptions.strokeColor(TRANSPARENT);
                mMap.addCircle(circleOptions);
          //  }//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        int zrobione=0;
        for (int i:user.zagadki
             ) {
            if(i==1)
                zrobione++;

        }


                userIntent.putExtra("prog",zrobione);
                userIntent.putExtra("size",user.zagadki.size());
        startActivity(userIntent);
    }

    public void coordinatesMethod(View view)
    {
        coordinatesDialog.setContentView(R.layout.custom_popup_coordinates);
        coordinatesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
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
        if(doWszystkiego!=null && doWszystkiego.isShowing())
        {
            doWszystkiego.dismiss();
            popUpSemafor=false;
            System.out.println(popUpSemafor);
        }
        if(badAnswerDialog!=null && badAnswerDialog.isShowing())
        {
            badAnswerDialog.dismiss();
            popUpSemafor=false;
            System.out.println(popUpSemafor);
        }
        if(congratulationsDialog!=null && congratulationsDialog.isShowing())
        {
            congratulationsDialog.dismiss();
            popUpSemafor=false;
            System.out.println(popUpSemafor);
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
    public void onLocationChanged(Location location) {
        float zoomLevel =14.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoomLevel));
        Toast.makeText(getApplicationContext(),"Location changed",Toast.LENGTH_SHORT).show();
        obecneWspolrzedne = location.getLatitude()+","+location.getLongitude();

        for(int i = 0; i < zagadkiLista.size(); i++)
        {
           // if(zagadkiLista.get(i).typ==5)
            if(zagadkiLista.get(i).czyNaMiejscu(location.getLatitude() + ","+ location.getLongitude())&&!popUpSemafor)
            {
                KeyguardManager myKM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
                if( (myKM.inKeyguardRestrictedInputMode() || isInBackground) && i != obecnaZagadka) {
                    powiadomienie.sendNotificationWithIntent();
                    obecnaZagadka = i;
                }
                popUpSemafor=true;
                /*LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                PopupWindow pw = zagadkiLista.get(i).showPopUp(inflater);
                pw.showAtLocation(this.findViewById(R.id.myMainLayout), Gravity.CENTER, 0, 0);*/
                Log.println(Log.ASSERT, "Reasuming", Boolean.toString(isInBackground));
                Log.println(Log.ASSERT, "Reasuming", Integer.toString(i));
                Log.println(Log.ASSERT, "obecna", Integer.toString(obecnaZagadka));
               // pierwszePokazanie = false;
                ustawDialogi();
                doWszystkiego = new Dialog(this);
                zagadkiLista.get(i).setContext(this);
                zagadkiLista.get(i).showPopUp(doWszystkiego, badAnswerDialog, congratulationsDialog,curiosityDialog);
                sound.spotSound();
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
           ustawDialogi();
       zagadkiLista.get(ktory).setContext(this);
        zagadkiLista.get(ktory).showPopUp(doWszystkiego,badAnswerDialog, congratulationsDialog,curiosityDialog);
        sound.spotSound();
        return false;
    }

    public void ustawDialogi()
    {
        badAnswerDialog = new Dialog(this);
        badAnswerDialog.setCanceledOnTouchOutside(false);
        badAnswerDialog.setCancelable(true);
        badAnswerDialog.setContentView(R.layout.popup_zla_odpowiedz);
        badAnswerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

        congratulationsDialog = new Dialog(this);
        congratulationsDialog.setCanceledOnTouchOutside(false);
        congratulationsDialog.setCancelable(true);
        congratulationsDialog.setContentView(R.layout.popup_gratulacje);
        congratulationsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

        curiosityDialog = new Dialog(this);
        curiosityDialog.setCanceledOnTouchOutside(false);
        curiosityDialog.setCancelable(true);
        curiosityDialog.setContentView(R.layout.popup_ciekawostka);
        curiosityDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
    }

    public void dispatchTakePictureIntent() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1888);
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            myPhoto = imageBitmap;
            //FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
            final Button takePhoto = (Button) doWszystkiego.findViewById(R.id.zrobZdjecieButton);
            takePhoto.setText("Wyślij");
            ImageView photo = (ImageView) doWszystkiego.findViewById(R.id.miejsceNaZdj);
            photo.setImageBitmap(imageBitmap);
            photo.setVisibility(View.VISIBLE);
        }


    }

}
