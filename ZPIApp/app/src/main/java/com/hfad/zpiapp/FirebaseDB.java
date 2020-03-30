package com.hfad.zpiapp;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDB {
    FirebaseDatabase database;
    DatabaseReference dbreference;
    public interface DataStatus{

        void  dataInserted();
        void dataUpdated();
        void dataLoaded();
    }

    public FirebaseDB()
    {
        database = FirebaseDatabase.getInstance();
        dbreference = database.getReference("users");
    }

    public void addUser(Uzytkownik us, final DataStatus ds)
    {
        dbreference.child(us.login).setValue(us.password).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ds.dataInserted();
                    }
                }
        ); //pewnie jeszcze failure do dodania jest
    }

}
