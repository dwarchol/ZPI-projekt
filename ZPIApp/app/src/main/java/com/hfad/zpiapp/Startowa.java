package com.hfad.zpiapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Startowa extends AppCompatActivity{
    Dialog haveAccountDialog;
    Dialog registerDialog;
    Context ctx;
    Uzytkownik user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
       /* signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent MainPageIntent=new Intent(ctx, Glowna.class);
                startActivity(MainPageIntent);
            }
        });*/
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
