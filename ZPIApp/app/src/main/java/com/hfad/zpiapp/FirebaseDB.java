package com.hfad.zpiapp;

//import android.support.annotation.NonNull;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;

public class FirebaseDB {
    FirebaseDatabase database;
    DatabaseReference dbreference;
    Uzytkownik user;

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
    public FirebaseDB(Uzytkownik user)
    {
        database=FirebaseDatabase.getInstance();
        dbreference=database.getReference();
        this.user=user;
    }


    public void addUser(final Uzytkownik us, final DataStatus ds)
    {
        dbreference=database.getReference().child("users");
        dbreference.child(us.login).setValue(us).addOnSuccessListener(
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
public void checkIfUserExistsAndLogin(final String l, final String p, final DataStatus ds)
{
    dbreference=database.getReference().child("users");
    dbreference.child(l).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists() && p.equals(dataSnapshot.getValue(Uzytkownik.class).password) )
            {
                user = dataSnapshot.getValue(Uzytkownik.class);
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

public void updateUser()
{
    dbreference=database.getReference().child("users");
    dbreference.child(user.login).setValue(user).addOnSuccessListener(
            new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                   System.out.println("Zmieniono uzytkownika");
                }
            }

    ).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            System.out.println("Nie zmieniono uzytkownika");
        }
    });
}
}
