package com.example.rec.menu_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rec.MainActivity;
import com.example.rec.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class fragment_notification extends Fragment {

    ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        TextView textView =(TextView)viewX.findViewById(R.id.heading);
        textView.setText("Notifications");

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });

        //yahan chat k msgs ayein ga
//tayyab ka code hai
        //tayyabCode();

    }

/*    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        mInterstitialAd = new InterstitialAd(MainActivity.this);
// Insert the Ad Unit ID
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    private InterstitialAd mInterstitialAd ;
    void tayyabCode()
    {
        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        displayInterstitial();
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {

                        }
                        displayInterstitial();
                    }
                });
            }
        }, 60, 60, TimeUnit.SECONDS);

    }*/
}
