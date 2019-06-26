package com.example.rec.menu_fragments.settings_fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rec.R;

public class settingHomePgViewHolder extends RecyclerView.ViewHolder {
    public TextView Option;
    public ImageView Logo;

    public settingHomePgViewHolder(View v){
        super(v);
        Option = v.findViewById(R.id.txtView);
        Logo = v.findViewById(R.id.imgLogo);
    }
}
