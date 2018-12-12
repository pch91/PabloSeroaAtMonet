package com.monetPablo.at;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.util.VisibleForTesting;


public class BaseActivity extends AppCompatActivity {

// Monetização utilizando Banner , na View = " promo_list_view_houder" //

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       /* MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        (this, "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView=findViewById(R.id.AdView);
        AdRequest adRequest=new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView=new AdView(this);
        adView.setAdSize(AdSize.LARGE_BANNER);*/

    }



    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}
