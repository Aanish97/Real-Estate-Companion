package com.example.rec.menu_fragments.chat_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.rec.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class usersOfchatting extends Fragment {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    ArrayList<Users> userz;
    chatAdapter adapter;
    FirebaseUser fuser;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private String accountHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("aanish BHAI is awseome ");
        return inflater.inflate(R.layout.fragment_users_ofchatting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView)  ((AppCompatActivity) getActivity()).findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userz = new ArrayList<Users>();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accountHolder = dataSnapshot.child("username").getValue().toString();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Users l = dataSnapshot1.getValue(Users.class);
                    userz.add(l);
                    System.out.println("aanish BHAI KA MSG HAI YE   "+l.getContact());
                }
                System.out.println("aanish BHAI KA MSG HAI YE   "+accountHolder);
                adapter = new chatAdapter(getActivity(), userz, accountHolder);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(((AppCompatActivity) getActivity()), "Opsss.... something is wrong", Toast.LENGTH_SHORT);
            }
        });
    }
}
