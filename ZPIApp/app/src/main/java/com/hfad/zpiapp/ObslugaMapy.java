package com.hfad.zpiapp;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ObslugaMapy {
    GoogleMap mMap;
    final ArrayList<Zagadka> zagadki = new ArrayList<>();

    public ArrayList<Zagadka> start(GoogleMap mMap){
        this.mMap=mMap;
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  Log.i(TAG, " asdff " + dataSnapshot.toString());
//
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Log.w("sss",snapshot.toString());
                    ZagadkaPytanie zagadka = snapshot.getValue(ZagadkaPytanie.class);
                    zagadki.add(zagadka);
                    //Log.w("asdqwe", zagadki.get(0).toString());
                }
                //Log.w("tag", "asd " + zagadki.get(0).index + " "+ zagadki.get(0).typ + " " + zagadki.get(0).wspolrzednaLat + " " + zagadki.get(0).wspolrzednaLng);
                wyswietlPunkty();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        DatabaseReference zagadkiRef = FirebaseDatabase.getInstance().getReference();
        zagadkiRef.child("zagadki").addListenerForSingleValueEvent(listener);

        return zagadki;
    }

    public void wyswietlPunkty(){
        for(int i=0;i<zagadki.size();i++){
    //        Log.w("w forze",zagadki.get(i).wspolrzednaLat + " " + zagadki.get(i).wspolrzednaLng + " " + zagadki.get(i).index + " " + mMap);
            LatLng wroclaw = new LatLng(zagadki.get(i).wspolrzednaLat, zagadki.get(i).wspolrzednaLng);
            mMap.addMarker(new MarkerOptions().position(wroclaw).title(zagadki.get(i).nazwa));
        }
    }

}
