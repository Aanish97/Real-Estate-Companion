package com.example.rec.menu_fragments.settings_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.example.rec.R;
import com.example.rec.menu_fragments.fragment_settings;

public class fragment_settings_notifications extends Fragment {

    ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_notifications, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /*Intent i = getIntent();
        String actName = i.getStringExtra("actName");*/

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        TextView v1 = viewX.findViewById(R.id.heading);

        v1.setText("Notifications");

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_settings()).commit();
            }
        });

        final Switch s = (Switch) ((AppCompatActivity)getActivity()).findViewById(R.id.stw);
        final Switch s1 = (Switch) ((AppCompatActivity)getActivity()).findViewById(R.id.stw1);
        final Switch s2 = (Switch) ((AppCompatActivity)getActivity()).findViewById(R.id.stw2);


        s1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (s1.isChecked()) {
                    if(s2.isChecked() == true){
                        s.setChecked(true);
                    }
                } else {
                    if(s2.isChecked() == false){
                        s.setChecked(false);
                    }
                }
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s2.isChecked()) {
                    if(s1.isChecked() == true){
                        s.setChecked(true);
                    }
                } else {
                    if(s1.isChecked() == false){
                        s.setChecked(false);
                    }
                }
            }
        });

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.isChecked()) {
                    s1.setChecked(true);
                    s2.setChecked(true);
                } else {
                    s1.setChecked(false);
                    s2.setChecked(false);
                }
            }
        });

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /*Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class); snoozeIntent.setAction(ACTION_SNOOZE);
                    snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
                    PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID) .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .addAction(R.drawable.ic_snooze, getString(R.string.snooze), snoozePendingIntent);
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.notify(notificationId, mBuilder.build());*/
                }
                else {
                    /*PendingIntent.getBroadcast(context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT).cancel();*/
                }
            }
        });

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    /*
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                    i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                     }
                     */
                }
                else {

                }
            }
        });
    }
}
