package com.example.rec.menu_fragments.chat_fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rec.MainActivity;
import com.example.rec.R;
import com.example.rec.User;
import com.example.rec.menu_fragments.fragment_message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder> {

    Context context;
    ArrayList<Users> userz;
    String acountHolder;

    public chatAdapter(Context c, ArrayList<Users> u, String accountHolder)
    {
        context=c;
        userz=u;
        this.acountHolder=accountHolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_chat_viewholder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final Users user = userz.get(i);
        myViewHolder.name.setText(user.getUsername());
        myViewHolder.contact.setText(user.getContact());
        final String sender = acountHolder;

        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, individualMessagingActivity.class);
                intent.putExtra("usernameReciever",user.getUsername());
                intent.putExtra("usernameSender",sender);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userz.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, contact;
        View view;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            view = itemView;
            name =(TextView) itemView.findViewById(R.id.displayName);
            contact =(TextView) itemView.findViewById(R.id.displayContact);
        }
    }
}
