package com.example.rec.menu_fragments.settings_fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class settingHomePgAdapter extends RecyclerView.Adapter<settingHomePgViewHolder> {
    private List<String> Options;
    private List<Bitmap> Logos;
    private int itemLayout;
    private settingHomePgViewHolder H;

    public settingHomePgAdapter(List<String> O, List<Bitmap> I, int itemLayout){
        this.Options = O;
        this.Logos = I;
        this.itemLayout = itemLayout;
    }

    @Override
    public settingHomePgViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        return new settingHomePgViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull settingHomePgViewHolder holder, int position) {
        H = holder;
        holder.Option.setText(Options.get(position));
        if(Options.get(position).contentEquals("Logout")){
            holder.Option.setTextColor(Color.parseColor("#3b5998"));
        }
        holder.Logo.setImageBitmap(Logos.get(position));
    }

    public settingHomePgViewHolder getHolder(){
        return H;
    }

    @Override
    public int getItemCount() {
        return Options.size();
    }
}
