package com.example.rec.menu_fragments.chat_fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rec.R;
import com.example.rec.User;
import com.example.rec.menu_fragments.Chats;
import com.example.rec.menu_fragments.fragment_message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class individualMessagingActivity extends AppCompatActivity {

    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    Intent intent;

    messagingAdapter msgAdapter;
    ArrayList<Chats> chats;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_messaging);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.rvc_msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //starting the legendary chat hehe
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        final String usernameReciever = intent.getStringExtra("usernameReciever");
        final String usernameSender = intent.getStringExtra("usernameSender");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if(!msg.equals(""))
                {
                    sendMessage(usernameSender, usernameReciever, msg);
                }
                else
                {
                    //Toast.makeText(individualMessagingActivity.this, "kyal hai", Toast.LENGTH_SHORT);
                }
                text_send.setText("");
            }
        });

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        //String current_uid = fuser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(usernameReciever);

                readMessages(usernameSender, usernameReciever);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void sendMessage(String sender, String receiver, String message)
    {//chat msgs uploading to firebase in Chats table
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);

    }

    private void readMessages(final String sender, final String receiver)
    {
        chats = new ArrayList<Chats>();
        reference = FirebaseDatabase.getInstance().getReference().child("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chats.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Chats chat = snapshot.getValue(Chats.class);
                    if(chat.getReceiver().equals(sender) && chat.getSender().equals(receiver)
                        ||
                        chat.getReceiver().equals(receiver) && chat.getSender().equals(sender))
                    {//adding values independently
                        System.out.println("AANISH BHAI KA MSG HAI YE   "+chat.getMsg());
                        chats.add(chat);
                    }

                }
                    msgAdapter = new messagingAdapter(individualMessagingActivity.this, chats);
                    recyclerView.setAdapter(msgAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
