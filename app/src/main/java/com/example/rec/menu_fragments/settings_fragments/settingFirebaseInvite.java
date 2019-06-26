package com.example.rec.menu_fragments.settings_fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.rec.R;
import com.example.rec.menu_fragments.fragment_settings;
import com.google.android.gms.appinvite.AppInviteInvitation;

import java.net.URI;

public class settingFirebaseInvite extends AppCompatActivity {

    ImageView backBtn;
    Button invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_firebase_invite);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View view = getSupportActionBar().getCustomView();

        backBtn = (ImageView) view.findViewById(R.id.imgBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_settings()).commit();
            }
        });

        invite = findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new AppInviteInvitation.IntentBuilder("REAL STATE APP")
                        .setMessage("This is our new real state application, please click the link to install")
                        .setDeepLink(Uri.parse("www.facebook.com"))
                        .setCallToActionText("ABCD")
                        .build();
                startActivityForResult(intent, 1);
            }
        });
    }
}
