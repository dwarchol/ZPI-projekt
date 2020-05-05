package com.hfad.zpiapp;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ZagadkaReader {
    final ArrayList<Zagadka> zagadkas = new ArrayList<>();
    private CountDownLatch countDownLatch;
    public void readData(final MyCallback myCallback){

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                   // Log.w("xxxx", snapshot.child("typ").getValue().toString());
                    if((int)snapshot.child("typ").getValue()==1){
                        ZagadkaPytanie zagadka = snapshot.getValue(ZagadkaPytanie.class);
                        zagadkas.add(zagadka);
                    }
                    if((int)snapshot.child("typ").getValue()==2){
                        ZagadkaWybor zagadka = snapshot.getValue(ZagadkaWybor.class);
                        zagadkas.add(zagadka);
                    }
                    if((int)snapshot.child("typ").getValue()==3){
                        ZagadkaMLObiekty zagadka = snapshot.getValue(ZagadkaMLObiekty.class);
                        zagadkas.add(zagadka);
                    }
                    if((int)snapshot.child("typ").getValue()==4){
                        ZagadkaMLTekst zagadka = snapshot.getValue(ZagadkaMLTekst.class);
                        zagadkas.add(zagadka);
                    }
                    if((int)snapshot.child("typ").getValue()==5){
                        ZagadkaDotarcieNaMiejsce zagadka = snapshot.getValue(ZagadkaDotarcieNaMiejsce.class);
                        zagadkas.add(zagadka);
                    }

                    ZagadkaPytanie zagadka = snapshot.getValue(ZagadkaPytanie.class);
             //       Log.w("ktorereader", zagadka.getNazwa());
                    zagadkas.add(zagadka);
                }
                myCallback.onCallback(zagadkas);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        DatabaseReference zagadkiRef = FirebaseDatabase.getInstance().getReference();
        zagadkiRef.child("zagadki").addListenerForSingleValueEvent(listener);
    }
}
