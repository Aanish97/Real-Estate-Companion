package com.example.rec.menu_fragments.chat_fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rec.R;
import com.example.rec.User;
import com.example.rec.menu_fragments.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chatting extends Fragment {

    private RecyclerView recyclerView;
    private chatAdapter cAdapter;
    private ArrayList<Users> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private ArrayList<String> usersList;
    private String senderUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chatting, container, false);
        return view;
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<String>();

        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                senderUsername = dataSnapshot.child("username").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chats chats = snapshot.getValue(Chats.class);

                    if (chats.getSender().equals(senderUsername)) {
                        usersList.add(chats.getReceiver());
                    }
                    if (chats.getReceiver().equals(senderUsername)) {
                        usersList.add(chats.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readChats()
    {
        mUsers = new ArrayList<Users>();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Users users = dataSnapshot1.getValue(Users.class);
                    for(String id: usersList)
                    {
                        System.out.println("aanish bhai in action   "+ id);
                        System.out.println("aanish bhai in action   "+ users.getId());

                       if(users.getUsername().equals(id)) {
                           if (mUsers.size() != 0)  {
                               for (Users users1: mUsers)
                               {
                                   if (!users.getUsername().equals(users1.getUsername())) {
                                       mUsers.add(users);
                                   }
                               }
                           }
                           else {
                               mUsers.add(users);
                           }
                       }
                    }
                }
                cAdapter = new chatAdapter(getActivity(), mUsers, senderUsername);
                recyclerView.setAdapter(cAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
}
