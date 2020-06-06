package com.hfad.zpiapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import static android.graphics.Color.TRANSPARENT;

//import androidx.lifecycle.ProcessLifecycleOwner;

//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;

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
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Dialog doWszystkiego;
    TextView wspolrzedneUzytkownika;
    int gpsOn;
    Dialog badAnswerDialog;
    AlertDialog GPSdialog;
    Dialog congratulationsDialog;
    Dialog curiosityDialog;
    boolean popUpSemafor = false;
    float zoom = 14.0F;
    static final int REQUEST_IMAGE_CAPTURE = 1; ////////////////////////////////////////////////////////////////do pobierania obrazu
    Bitmap myPhoto; ///////////////////////////////////////////////////////////////////////////////////////////trzymacz obrazu
    String obecneWspolrzedne;
    int obecnaZagadka = 0;
    boolean isInBackground;
    Powiadomienie powiadomienie;
    //  TextView wspolrzedneUzytkownika;
    View customView;
    Dialog koniec;
    Dialog zaDaleko;

    @SuppressLint("WrongConstant")
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        checkGPS();
        preferences = getApplicationContext().getSharedPreferences("APP_SETTINGS", 0);
        editor = preferences.edit();


        sound = new Odtwarzacz(this.getApplicationContext());
        popUpSemafor = false;
        user = (Uzytkownik) getIntent().getSerializableExtra("Uzytkownik");
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
        } catch (SecurityException e) {
            Log.println(Log.ASSERT, "Reasuming", "PermissionNot");
        }

        prepareToolbar();
        //Za pierwszym razem


        if (user.pierwszyRaz == 0) {
            wyswietlFabule();
            // user.pierwszyRaz = 1;
        }


        coordinatesDialog = new Dialog(this);
        coordinatesDialog.setCancelable(true);
        coordinatesDialog.setCanceledOnTouchOutside(false);

        doWszystkiego = new Dialog(this);

        koniec = new Dialog(this);
        koniec.setCancelable(true);
        koniec.setCanceledOnTouchOutside(false);

        zaDaleko = new Dialog(this);
        zaDaleko.setCancelable(true);
        zaDaleko.setCanceledOnTouchOutside(false);

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);

        initMap();

        powiadomienie = new Powiadomienie(this);

        ZagadkaWybor zw = new ZagadkaWybor(doWszystkiego);
        ZagadkaReader zagadkaReader = new ZagadkaReader();
        zagadkaReader.readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Zagadka> zag) {
                Log.w("ktorereader", zag.get(0).getNazwa());
                zagadkiLista = zag;
                //  drawMaps();
                drawMapsStartowe();
            }
        });
        Log.w("ZAGADKI", "" + zagadkiLista.size());


    }

    private void wyswietlFabule() {
        final Dialog fabulaDialog = new Dialog(this);
        fabulaDialog.setCanceledOnTouchOutside(false);
        fabulaDialog.setCancelable(true);
        fabulaDialog.setContentView(R.layout.popup_fabula);
        fabulaDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

        final Button tak = (Button) fabulaDialog.findViewById(R.id.takFabula);
        final Button nie = (Button) fabulaDialog.findViewById(R.id.nieFabula);

        final Context cxt = this;


        tak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog fabulaDialog2 = new Dialog(cxt);
                fabulaDialog2.setCanceledOnTouchOutside(false);
                fabulaDialog2.setCancelable(true);
                fabulaDialog2.setContentView(R.layout.popup_fabula2);
                fabulaDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

                final Button ok = (Button) fabulaDialog2.findViewById(R.id.okFabula2);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.otworzone();
                        fabulaDialog.dismiss();
                        fabulaDialog2.dismiss();

                    }
                });
                fabulaDialog2.show();

            }
        });

        nie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        fabulaDialog.show();

    }


    public void actualiseCoordinatesText() {
        wspolrzedneUzytkownika.setText(user.wspolrzedne);
        wspolrzedneUzytkownika.refreshDrawableState();
        Log.println(Log.ASSERT, "userws", user.wspolrzedne);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        // app moved to foreground
        isInBackground = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onMoveToBackground() {
        // app moved to background
        isInBackground = true;
    }

    public void checkGPS() {
        try {
            gpsOn = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Exception e) {
            Log.i("GPS Failure", "Sum Ting Wong");
        }
        if (gpsOn == 0) {
            forceGPSOn();
        }
    }

    public void forceGPSOn() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS jest wyłączony!");
        builder.setMessage("Do użytkowania aplikacji niezbędna jest włączona lokalizacja, czy chcesz ją włączyć?");
        builder.setPositiveButton("Włącz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 12);

            }
        }).setNegativeButton("Nie, Dziękuję", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                checkGPS();
                dialog.dismiss();
            }
        });
        GPSdialog = builder.create();
        GPSdialog.show();
        if (GPSdialog.isShowing()) {
            System.out.println("Mudafaka working");
        }

    }


    public void drawMaps() {
        mMap.setOnMarkerClickListener(this);
        for (int i = 0; i < zagadkiLista.size(); i++) {
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

    public void drawMapsStartowe() {
        //checkGPS();
        mMap.setOnMarkerClickListener(this);
        actualiseCoordinatesText();

        //Aktualne
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker20001);
        icon = Bitmap.createScaledBitmap(icon, 190, 105, false);
        // user.zagadkiAktualne.add(999);

        for (int i = 0; i < zagadkiLista.size() - 1; i++) {
            if (user.jestWAktywnych(zagadkiLista.get(i).index)) {             //Mozna usunac zeby były wszystkie zagadki////////////////////////////////
                LatLng point = new LatLng(zagadkiLista.get(i).wspolrzednaLat, zagadkiLista.get(i).wspolrzednaLng);
                MarkerOptions markerOptions = new MarkerOptions().position(point).title(zagadkiLista.get(i).nazwa).icon(BitmapDescriptorFactory.fromBitmap(icon));

                Marker marker = mMap.addMarker(markerOptions);

                marker.setTag(i);
                //}
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(point);

                circleOptions.strokeColor(Color.BLACK);

                if (zagadkiLista.get(i).typ == 5) {
                    circleOptions.radius(60);
                    circleOptions.fillColor(Color.argb(99, 6, 37, 74));
                } else {
                    circleOptions.radius(50);
                    circleOptions.fillColor(Color.argb(75, 51, 153, 255));
                }
                circleOptions.strokeWidth(0);
                mMap.addCircle(circleOptions);
            }//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

        //Zakonczone

        if (user.zagadkiRozwiazane != null) {
            Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker20002pom);
            icon2 = Bitmap.createScaledBitmap(icon2, 190, 105, false);
            for (int i = 0; i < zagadkiLista.size(); i++) {
                if (user.jestWRozwiazanych(zagadkiLista.get(i).index) && zagadkiLista.get(i).typ != 5) {             //Mozna usunac zeby były wszystkie zagadki////////////////////////////////
                    LatLng point = new LatLng(zagadkiLista.get(i).wspolrzednaLat, zagadkiLista.get(i).wspolrzednaLng);
                    MarkerOptions markerOptions = new MarkerOptions().position(point).title(zagadkiLista.get(i).nazwa).icon(BitmapDescriptorFactory.fromBitmap(icon2));

                    Marker marker = mMap.addMarker(markerOptions);

                    marker.setTag(i);
                }
            }
        }
        if (user.zagadkiAktualne.isEmpty() && user.zagadkiRozwiazane.size() == 47) {///zeby bylo na koniec/////tu wykomentowac żeby dobrze testowac

            // user.zagadkiAktualne.add(new ZagadkaDotarcieNaMiejsce(1000))
            Log.i("ostatnia", zagadkiLista.get(zagadkiLista.size() - 1).nazwa);
            Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker20003pom);
            icon2 = Bitmap.createScaledBitmap(icon2, 190, 105, false);
            LatLng point = new LatLng(zagadkiLista.get(zagadkiLista.size() - 1).wspolrzednaLat, zagadkiLista.get(zagadkiLista.size() - 1).wspolrzednaLng);
            MarkerOptions markerOptions =
                    new MarkerOptions().position(point).title(zagadkiLista.get(zagadkiLista.size() - 1).nazwa).icon(BitmapDescriptorFactory.fromBitmap(icon2));

            Marker marker = mMap.addMarker(markerOptions);

            marker.setTag(zagadkiLista.size() - 1);
            //}
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(point);

            circleOptions.strokeColor(Color.YELLOW);

            circleOptions.strokeColor(Color.RED);
            circleOptions.radius(50);
            circleOptions.fillColor(Color.argb(75,100,10,10));
            Log.w("kolorek","yyyh");
            mMap.addCircle(circleOptions);

            mMap.addCircle(circleOptions);
       /* Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker20002pom);
        icon2 = Bitmap.createScaledBitmap(icon2, 190, 105, false);

                MarkerOptions markerOptions2 =
                        new MarkerOptions().position(point).title(zagadkiLista.get(zagadkiLista.size()-1).nazwa).icon(BitmapDescriptorFactory.fromBitmap(icon2));

                Marker marker2 = mMap.addMarker(markerOptions2);

                marker2.setTag(zagadkiLista.size()-1);*/

        }
    }

    public void pokazKoniec() {
        koniec.setContentView(R.layout.popup_koniec_gry);
        koniec.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        Button kon = (Button) koniec.findViewById(R.id.closeKoniecGry);
        kon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                koniec.dismiss();
            }
        });
        koniec.show();
    }

    public void pokazZaDaleko()
    {
        zaDaleko.setContentView(R.layout.popup_za_daleko);
        zaDaleko.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        Button zD = (Button) zaDaleko.findViewById(R.id.closeZaDaleko);
        zD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zaDaleko.dismiss();
            }
        });
        zaDaleko.show();
    }

    public void settingsMethod(View view) {
        saveZoom();
        final Intent settingsIntent = new Intent(this, Ustawienia.class);
        startActivity(settingsIntent);

    }

    public void userMethod(View view) {
        saveZoom();
        final Intent userIntent = new Intent(this, KontoUzytkownika.class);
        ArrayList<String> ciek = new ArrayList<>();
        for (int i = 0; i < zagadkiLista.size(); i++) {
            ciek.add(zagadkiLista.get(i).ciekawostka);
        }

        ArrayList<String> ciekawostkiToKonto = new ArrayList<>();

        if (user.zagadkiRozwiazane == null) {
            Log.i("empty", "xd");
            user.zagadkiRozwiazane = new ArrayList<>(11);
        }
        for (int i = 0; i < user.zagadkiRozwiazane.size(); i++) {
            for (int j = 0; j < zagadkiLista.size(); j++) {
                if (user.zagadkiRozwiazane.get(i).equals(zagadkiLista.get(j).index)) {
                    if (zagadkiLista.get(j).index % 10 != 0) {
                        ciekawostkiToKonto.add(zagadkiLista.get(j).ciekawostka);
                    }
                }
                // if((zagadkiLista.get(i).index)== user.zagadkiRozwiazane.get(j)){
                //    ciekawostkiToKonto.add(zagadkiLista.get(j).ciekawostka);
                //}
            }
        }

        userIntent.putExtra("ciekawostki", ciekawostkiToKonto);
        userIntent.putExtra("prog", user.zagadkiRozwiazane != null ? user.zagadkiRozwiazane.size() : 0);
        userIntent.putExtra("program", user.zagadkiRozwiazane);
        // userIntent.putExtra("zagadki",zagadkiLista);
        //userIntent.putExtra("ciekawostki",zagadkiLista!=null?zagadkiLista:new ArrayList());
        // userIntent.putExtra("ciek",ciek!=null?zagadkiLista:new ArrayList());
        startActivity(userIntent);
    }

    public void coordinatesMethod(View view) {
        coordinatesDialog.setContentView(R.layout.custom_popup_coordinates);
        coordinatesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));
        Button closeDialog = (Button) coordinatesDialog.findViewById(R.id.closeCoordinates);
        TextView wspolrzedneNapis = coordinatesDialog.findViewById(R.id.coordinates_text);
        StringBuilder sB = new StringBuilder("");
        sB.append(user.wspolrzedne.substring(0, 22));
        sB.append("\n");
        sB.append(user.wspolrzedne.substring(23));
        wspolrzedneNapis.setText(sB.toString());
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinatesDialog.dismiss();
            }
        });
        coordinatesDialog.show();

    }

    public void onBackPressed() {
        if (coordinatesDialog.isShowing()) {
            coordinatesDialog.dismiss();
            return;
        }
        if (doWszystkiego != null && doWszystkiego.isShowing()) {
            doWszystkiego.dismiss();
            popUpSemafor = false;
            System.out.println(popUpSemafor);
        }
        if (badAnswerDialog != null && badAnswerDialog.isShowing()) {
            badAnswerDialog.dismiss();
            popUpSemafor = false;
            System.out.println(popUpSemafor);
        }
        if (congratulationsDialog != null && congratulationsDialog.isShowing()) {
            congratulationsDialog.dismiss();
            popUpSemafor = false;
            System.out.println(popUpSemafor);

        }


        super.onBackPressed();


    }

    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapGoogle);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
        Log.d("Glowna", "onMapReady: ready");
        mMap = googleMap;
        if (checkSelfPermission("ACCESS_FINE_LOCATION")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Glowna.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            mMap.setMyLocationEnabled(true);
        }


        LatLng wroclaw = new LatLng(preferences.getFloat("latFloat", 51.105171F), preferences.getFloat("longFloat", 17.037821F));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, preferences.getFloat("zoomFloat", 16.0F)));
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        // obsluga.start(mMap);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void showNext(int kolejnaZagadkaDoPokazania) {
        boolean czyDzwiek = true;
        for (int i = 0; i < zagadkiLista.size(); i++) {
            if (zagadkiLista.get(i).index == kolejnaZagadkaDoPokazania && !popUpSemafor) {
                KeyguardManager myKM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
                if ((myKM.inKeyguardRestrictedInputMode() || isInBackground) && i != obecnaZagadka) {
                    powiadomienie.sendNotificationWithIntent();
                    obecnaZagadka = i;
                    czyDzwiek = false;
                }
                popUpSemafor = true;
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
                zagadkiLista.get(i).showPopUp(doWszystkiego, badAnswerDialog, congratulationsDialog, curiosityDialog);
                if (czyDzwiek) {
                    sound.spotSound();
                }

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), preferences.getFloat("zoomFloat", 20.0F)));
        //Toast.makeText(getApplicationContext(), "Location changed", Toast.LENGTH_SHORT).show();
        obecneWspolrzedne = location.getLatitude() + "," + location.getLongitude();
        boolean czyWLokacji = false;
        boolean czyDzwiek = true;

        for (int i = 0; i < zagadkiLista.size(); i++) {
            // if(zagadkiLista.get(i).typ==5)
            if (user.jestWAktywnych(zagadkiLista.get(i).index) && zagadkiLista.get(i).czyNaMiejscu(location.getLatitude() + "," + location.getLongitude()) && !popUpSemafor) {
                czyWLokacji = true;
                KeyguardManager myKM = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
                if ((myKM.inKeyguardRestrictedInputMode() || isInBackground) && i != obecnaZagadka) {
                    powiadomienie.sendNotificationWithIntent();
                    obecnaZagadka = i;
                    czyDzwiek = false;
                }
                popUpSemafor = true;
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
                zagadkiLista.get(i).showPopUp(doWszystkiego, badAnswerDialog, congratulationsDialog, curiosityDialog);
                if (czyDzwiek) {
                    sound.spotSound();
                }
            }
        }
        if (!czyWLokacji) {
            obecnaZagadka = 0;
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
        checkGPS();
        int ktory = (int) marker.getTag();
        //TO DO Location check
           /* LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            PopupWindow pw = zagadkiLista.get(ktory).showPopUp(inflater);
            pw.showAtLocation(this.findViewById(R.id.myMainLayout), Gravity.CENTER, 0, 0);
*/

        if (user.jestWAktywnych(zagadkiLista.get(ktory).index)) {
            Log.w("Jest w aktywnych ", ktory + "");
            ustawDialogi();
            zagadkiLista.get(ktory).setContext(this);
            zagadkiLista.get(ktory).showPopUp(doWszystkiego, badAnswerDialog, congratulationsDialog, curiosityDialog);
            sound.spotSound();
        }
        if (user.jestWRozwiazanych(zagadkiLista.get(ktory).index)) {
            Log.w("Jest w rozwiazanych ", ktory + "");
            ustawDialogi();
            //zagadkiLista.get(ktory).setContext(this);

            zagadkiLista.get(ktory).showCiekawostka(curiosityDialog,this);

        }

        return false;
    }

    public void ustawDialogi() {
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1888);
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
        if (requestCode == 12) {
            if (GPSdialog != null) {
                GPSdialog.dismiss();
            }
            checkGPS();
        }


    }

    public void saveZoom() {

        editor.putFloat("zoomFloat", mMap.getCameraPosition().zoom);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

      @SuppressLint("MissingPermission")  android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        editor.putFloat("latFloat", (float)lastKnownLocation.getLatitude());
        editor.putFloat("longFloat",(float)lastKnownLocation.getLongitude());
        editor.apply();
        System.out.println(mMap.getCameraPosition().zoom);
        System.out.println(preferences.getFloat("zoomFloat",1.0F));
    }



@SuppressLint("WrongConstant")
public void prepareToolbar()
{
    this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    getSupportActionBar().setDisplayShowCustomEnabled(true);
    customView = getLayoutInflater().inflate(R.layout.custom_action_bar,null);
    wspolrzedneUzytkownika = customView.findViewById(R.id.coordinates);
    wspolrzedneUzytkownika.setText(user.wspolrzedne);
    getSupportActionBar().setCustomView(customView);
    Toolbar parent = (Toolbar) customView.getParent();
    parent.setPadding(0,0,0,0);
    parent.setContentInsetsAbsolute(0,0);
}
@SuppressLint("WrongConstant")
@Override
public void onRestart()
{
    super.onRestart();
    prepareToolbar();

    this.drawMapsStartowe();

}
}
