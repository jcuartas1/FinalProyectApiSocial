package com.home.juliancuartas.finalproyectapisocial;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private CallbackManager cM;
    private LoginButton lB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        cM = CallbackManager.Factory.create();

        getFbKeyHash("wphn1lSdolXnlPoynd6mpqeggZw=");

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        lB = (LoginButton) findViewById(R.id.login_facebook);
        if(isLoggedIn()){
            Intent conectado = new Intent(MainActivity.this,VistaGeneral.class);
            startActivity(conectado);
        }else {


            lB.registerCallback(cM, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(MainActivity.this, "Se conecto con exito !!", Toast.LENGTH_SHORT).show();
                    Intent pasarIntent = new Intent(MainActivity.this, VistaGeneral.class);
                    startActivity(pasarIntent);
                }

                @Override
                public void onCancel() {
                    Toast.makeText(MainActivity.this, "Inicio de session cancelado !!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(MainActivity.this, "No se exitoso!!", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    public void getFbKeyHash(String pack){
        try{
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                    pack, PackageManager.GET_SIGNATURES);

            for(Signature signature : packageInfo.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash :", Base64.encodeToString(md.digest(),Base64.DEFAULT));
                System.out.println("KeyHash:"+Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }
    }

    public boolean isLoggedIn(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    protected  void onActivityResult(int reqCode, int resCode, Intent i){
        cM.onActivityResult(reqCode,resCode,i);
    }

}
