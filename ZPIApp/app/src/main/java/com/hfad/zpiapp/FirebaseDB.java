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
    }

    public FirebaseDB()
    {
        database = FirebaseDatabase.getInstance();
        dbreference = database.getReference();
    }


    public void checkIfUserExistsAndRegister(final Uzytkownik us, final DataStatus ds)
    {
        dbreference=database.getReference().child("users");
        dbreference.child(us.login).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ds.dataExists();
                }
                else
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                ds.databaseFailure();
            }
        });
    }

}
