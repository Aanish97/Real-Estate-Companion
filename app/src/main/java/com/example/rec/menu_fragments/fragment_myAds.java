package com.example.rec.menu_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rec.R;
import com.example.rec.myads_fragments.pageAdapter;

public class fragment_myAds extends Fragment {

    ImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myads, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView();

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        TextView textView =(TextView) viewX.findViewById(R.id.heading);
        textView.setText("MyAds");

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });

        tayyabCode();
    }

    void tayyabCode()
    {
        TabLayout tabLayout =(TabLayout) ((AppCompatActivity)getActivity()).findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Seller"));
        tabLayout.addTab(tabLayout.newTab().setText("Leaser"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager=(ViewPager) ((AppCompatActivity)getActivity()).findViewById(R.id.pager);
        pageAdapter pagerAdapter=new pageAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            viewPager.setCurrentItem(tab.getPosition());

        }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

        }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

        }
        });
    }
}
