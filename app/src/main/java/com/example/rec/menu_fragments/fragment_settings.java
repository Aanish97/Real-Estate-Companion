package com.example.rec.menu_fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rec.MainActivity;
import com.example.rec.R;
import com.example.rec.menu_fragments.settings_fragments.RecyclerItemClickListener;
import com.example.rec.menu_fragments.settings_fragments.fragment_settings_notifications;
import com.example.rec.menu_fragments.settings_fragments.settingFirebaseInvite;
import com.example.rec.menu_fragments.settings_fragments.settingHomePgAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class fragment_settings extends Fragment {

    ImageView backBtn;
    ArrayList<Bitmap> logos;
    ArrayList<String> Options;
    settingHomePgAdapter Adapter;
    RecyclerView rcv;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        TextView textView =(TextView)viewX.findViewById(R.id.heading);
        textView.setText("Settings");

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });

        Options = new ArrayList<>();
        Options.add("Notifications");
        Options.add("Terms of Service");
        Options.add("Privacy policy");
        Options.add("Promotions");
        Options.add("Logout");

        Bitmap i1 = BitmapFactory.decodeResource(getResources(), R.drawable.icons8_notification_100);
        Bitmap i2 = BitmapFactory.decodeResource(getResources(), R.drawable.icons8_terms_and_conditions_240);
        Bitmap i3 = BitmapFactory.decodeResource(getResources(), R.drawable.icons8_privacy_64);
        Bitmap i31 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_record_voice_over_black_24dp);
        Bitmap i4 = BitmapFactory.decodeResource(getResources(), R.drawable.icons8_shutdown_100);

        logos = new ArrayList<>();
        logos.add(i1);
        logos.add(i2);
        logos.add(i3);
        logos.add(i31);
        logos.add(i4);

        Adapter = new settingHomePgAdapter(Options, logos, R.layout.fragment_settings_viewholder);

        rcv = view.findViewById(R.id.settingRecyclerV);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setAdapter(Adapter);

        rcv.addOnItemTouchListener(
                new RecyclerItemClickListener(((AppCompatActivity)getActivity()).getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        if(position == 0){
                            ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new fragment_settings_notifications()).commit();
                        }

                        else if(position == 1){
/*                            Intent i = new Intent(((AppCompatActivity)getActivity()).getBaseContext(), settingPg2.class);
                            i.putExtra("text", "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla");
                            i.putExtra("actName", "Terms of Service");
                            startActivityForResult(i,1);*/
                        }

                        else if(position == 2){
/*                          Intent i = new Intent(getBaseContext(), settingPg2.class);
                            i.putExtra("text", "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla");
                            i.putExtra("actName", "Privacy Policy");
                            startActivityForResult(i,2)*/;
                        }

                        else if(position == 3){/*
                            Intent i1 = new Intent(((AppCompatActivity) getActivity()), settingFirebaseInvite.class);
                            ((AppCompatActivity) getActivity()).startActivity(i1);*/
                        }

                        else if(position == 4){
                            Toast.makeText(getContext(), "Logout", Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent i1 = new Intent(((AppCompatActivity) getActivity()), MainActivity.class);
                            ((AppCompatActivity) getActivity()).startActivity(i1);
                        }
                    }
                })
        );

    }
}