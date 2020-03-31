package com.hfad.zpiapp;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDB {
    FirebaseDatabase database;
    DatabaseReference dbreference;
    public interface DataStatus{

        void dataInserted();
        void dataUpdated();
        void dataLoaded();
        void dataExists();
        void databaseFailure();
        void dataExistsNot();
    }

    public FirebaseDB()
    {
        database = FirebaseDatabase.getInstance();
        dbreference = database.getReference();
    }

    public void addUser(final Uzytkownik us, final DataStatus ds)
    {
        dbreference.child(us.login).setValue(us.password).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ds.dataInserted();
                    }
                }

        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ds.databaseFailure();
            }
        });
    }
    public void checkIfUserExistsAndRegister(final String us, final DataStatus ds)
    {
        dbreference=database.getReference().child("users");
        dbreference.child(us).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ds.dataExists();
                }
                else
                {
                   ds.dataExistsNot();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ds.databaseFailure();
            }
        });
    }

}
