package com.example.rec.menu_fragments.chat_fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rec.R;
import com.example.rec.menu_fragments.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class messagingAdapter extends RecyclerView.Adapter<messagingAdapter.MyViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    FirebaseUser fuser;

    Context context;
    ArrayList<Chats> chatz;
    private String senderUsername;

    public messagingAdapter(Context c, ArrayList<Chats> u)
    {
        context=c;
        chatz=u;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;

        if(i==MSG_TYPE_RIGHT){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
        }
        return new messagingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Chats chats = chatz.get(i);
        myViewHolder.show_messages.setText(chats.getMsg());
    }

    @Override
    public int getItemCount() {
        return chatz.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView show_messages;
        View view;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            show_messages =(TextView) itemView.findViewById(R.id.show_message);

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
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(chatz.get(position).getSender().equals(senderUsername))
        {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
