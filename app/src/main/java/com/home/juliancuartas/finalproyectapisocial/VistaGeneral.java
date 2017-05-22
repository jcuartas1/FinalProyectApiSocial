package com.home.juliancuartas.finalproyectapisocial;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class VistaGeneral extends AppCompatActivity {

    private AccessTokenTracker accessTokenTracker;

    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_vista_general);

        MobileAds.initialize(this,"ca-app-pub-8638985907398093/3169631366");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adView = (AdView) findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());
    }

    @Override
    protected void onDestroy() {
        if(adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(adView != null){
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(adView != null){
            adView.pause();
        }
        super.onPause();
    }

    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {

        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(VistaGeneral.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            },1000);
        }
    }
}
