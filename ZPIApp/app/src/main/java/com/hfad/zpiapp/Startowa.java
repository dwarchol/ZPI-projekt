package com.hfad.zpiapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.sprylab.android.widget.TextureVideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import android.support.v7.app.AppCompatActivity;

public class Startowa extends AppCompatActivity{
    Dialog haveAccountDialog;
    Dialog registerDialog;
    Context ctx;
    Uzytkownik user;
    int CAMERA_PERMISSION = 1;
    int ACCESS_FINE_LOCATION_PERMISSION = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setSharedP();


        setContentView(R.layout.activity_startowa);


        Button haveAccount = (Button) findViewById(R.id.haveAccount);
        Button register = (Button) findViewById(R.id.register);

        moveButton(haveAccount);
        moveButton(register);

        haveAccountDialog = new Dialog(this);
        registerDialog = new Dialog(this);
        haveAccountDialog.setCancelable(true);
        registerDialog.setCancelable(true);
        haveAccountDialog.setCanceledOnTouchOutside(false);
        registerDialog.setCanceledOnTouchOutside(false);
        ctx = this;


        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            requestLocationPermission();
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            requestCameraPermission();
        }

        /*VideoView vv=findViewById(R.id.animation);

        MediaController mc = new MediaController(this);
        mc.setVisibility(View.GONE);
        vv.setMediaController(mc);
        vv.setVideoURI(uri);
        vv.setBackgroundColor(Color.TRANSPARENT);
        vv.start();*/
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.GONE);
        String dPath="android.resource://"+getPackageName()+"/"+R.raw.start_animation;
        Uri uri = Uri.parse(dPath);
        TextureVideoView mVideoView =(TextureVideoView)findViewById(R.id.video_view);
        mVideoView.setVideoURI(uri);

        mVideoView.setMediaController(mc);
        mVideoView.start();



    }

    private void requestCameraPermission()
    {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION);
    }

    private void requestLocationPermission()
    {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults)
    {
        if(requestCode == CAMERA_PERMISSION)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        else if(requestCode == ACCESS_FINE_LOCATION_PERMISSION)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestCameraPermission();
                }
            }
        }
    }

    public void moveButton(final Button button)
    {
        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        final float yDest = -2*dm.heightPixels/5 ;

        final int originalPos[] = new int[2];
        button.getLocationOnScreen( originalPos );

        long timeToAnimate = 2000;

        button.animate().setDuration(timeToAnimate);
        button.animate().y(yDest - originalPos[1]);
        button.animate().translationY(yDest - originalPos[1]);

    }

    public void haveAccountMethod(View view)
    {
        haveAccountDialog.setContentView(R.layout.custom_popup_have_account);
        haveAccountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText loginsi = (EditText) haveAccountDialog.findViewById(R.id.loginSI);
        final EditText passwordsi = (EditText) haveAccountDialog.findViewById(R.id.passwordSI);

        loginsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(loginsi.getText().toString().isEmpty()){
                    if(hasFocus){
                        loginsi.setHint("");
                    }else{
                        loginsi.setHint(R.string.userName);
                    }
                }
            }
        });

        passwordsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(passwordsi.getText().toString().isEmpty()){
                    if(hasFocus){
                        passwordsi.setHint("");
                    }else{
                        passwordsi.setHint(R.string.password);
                    }
                }
            }
        });

        Button signIn = (Button) haveAccountDialog.findViewById(R.id.signInButton);
        TextView closeHaveAccount = (TextView) haveAccountDialog.findViewById(R.id.closeHaveAccount);
        closeHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haveAccountDialog.dismiss();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordsi.getText().toString().equals("") || loginsi.getText().toString().equals("")||passwordsi.getText().toString().isEmpty()||loginsi.getText().toString().isEmpty() )
                {
                    Toast.makeText(getApplicationContext(),R.string.Pusta_rejestracja,Toast.LENGTH_SHORT).show();
                }
                else {
                    final FirebaseDB fbdb = new FirebaseDB();
                    fbdb.checkIfUserExistsAndLogin(loginsi.getText().toString(), passwordsi.getText().toString(), new FirebaseDB.DataStatus() {
                        @Override
                        public void dataInserted() {
                        }
                        @Override
                        public void dataUpdated() {
                        }
                        @Override
                        public void dataLoaded() {
                        }
                        @Override
                        public void dataExists() {
                           // Toast.makeText(getApplicationContext(),R.string.LogIn,Toast.LENGTH_SHORT).show();
                            final Intent MainPageIntent=new Intent(ctx, Glowna.class);
                            MainPageIntent.putExtra("Uzytkownik",fbdb.user);
                            startActivity(MainPageIntent);
                        }
                        @Override
                        public void databaseFailure() {
                        }
                        @Override
                        public void dataExistsNot() {
                            Toast.makeText(getApplicationContext(),R.string.LoginFailed,Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        haveAccountDialog.show();

    }

    public void setSharedP()
    {
        SharedPreferences preferences =getApplicationContext().getSharedPreferences("APP_SETTINGS",0);
        SharedPreferences.Editor editor= preferences.edit();
        if(!preferences.contains("notifyBool"))
        editor.putBoolean("notifyBool",true);
        if(!preferences.contains("darkModeBool"))
        editor.putBoolean("darkModeBool",true);
        else
        {
            if(preferences.getBoolean("darkModeBool",true)==true)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if(!preferences.contains("soundBool"))
            editor.putBoolean("soundBool",true);
        if(!preferences.contains("vibrationBool"))
            editor.putBoolean("vibrationBool",true);
        editor.apply();
    }



    public void registerMethod(View view)
    {
        registerDialog.setContentView(R.layout.custom_popup_register);
        registerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button register = (Button) registerDialog.findViewById(R.id.registerButton);
        final EditText loginU = (EditText) registerDialog.findViewById(R.id.login);
        final EditText password = (EditText) registerDialog.findViewById(R.id.password);

        loginU.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(loginU.getText().toString().isEmpty()){
                    if(hasFocus){
                        loginU.setHint("");
                    }else{
                        loginU.setHint(R.string.userName);
                    }
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(password.getText().toString().isEmpty()){
                    if(hasFocus){
                        password.setHint("");
                    }else{
                        password.setHint(R.string.password);
                    }
                }
            }
        });

        TextView closeRegister = (TextView) registerDialog.findViewById(R.id.closeRegister);
        closeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerDialog.dismiss();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().toString().equals("") || loginU.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),R.string.Pusta_rejestracja,Toast.LENGTH_SHORT).show();
                }
                else {

                   final FirebaseDB fbdb = new FirebaseDB();
                   fbdb.checkIfUserExistsAndRegister(loginU.getText().toString(), new FirebaseDB.DataStatus() {
                       @Override
                       public void dataInserted() {
                       }
                       @Override
                       public void dataUpdated() {
                       }
                       @Override
                       public void dataLoaded() {
                       }
                       @Override
                       public void dataExists() {
                           Toast.makeText(getApplicationContext(),R.string.uz_istnieje,Toast.LENGTH_LONG).show();
                       }
                       @Override
                       public void databaseFailure() {
                           Toast.makeText(getApplicationContext(),R.string.DataBase_failure,Toast.LENGTH_LONG).show();
                       }
                       @Override
                       public void dataExistsNot() {
                           Uzytkownik us = new Uzytkownik(loginU.getText().toString(),password.getText().toString());
                           fbdb.addUser(us, new FirebaseDB.DataStatus() {
                               @Override
                               public void dataInserted() {
                                   registerDialog.dismiss();
                                   Toast.makeText(getApplicationContext(),R.string.rejestracja,Toast.LENGTH_LONG).show();
                               }
                               @Override
                               public void dataUpdated() {
                               }
                               @Override
                               public void dataLoaded() {
                               }
                               @Override
                               public void dataExists() {
                               }
                               @Override
                               public void databaseFailure() {
                                   Toast.makeText(getApplicationContext(),R.string.DataBase_failure,Toast.LENGTH_LONG).show();
                               }
                               @Override
                               public void dataExistsNot() {

                               }
                           });
                       }
                   });
                }
            }
        });
        registerDialog.show();
    }

}
