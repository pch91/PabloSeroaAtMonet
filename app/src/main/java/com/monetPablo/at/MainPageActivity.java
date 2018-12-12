package com.monetPablo.at;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainPageActivity extends BaseActivity {

    private InterstitialAd mInterstitialAd;
    boolean mAdIsLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        showProgressDialog();


        AdRequest adRequest =  new AdRequest.Builder().build();
        MobileAds.initialize(this, "ca-app-pub-9404483305208909~3149168131");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9404483305208909/5897644037");
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                    hideProgressDialog();
                }
            }

            @Override
            public void onAdClosed() {
                Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
