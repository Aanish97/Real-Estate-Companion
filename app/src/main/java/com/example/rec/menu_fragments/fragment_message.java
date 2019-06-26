package com.example.rec.menu_fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.rec.R;
import com.example.rec.menu_fragments.chat_fragments.chatting;
import com.example.rec.menu_fragments.chat_fragments.usersOfchatting;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class fragment_message extends Fragment {

    ImageView backBtn;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(R.layout.fragment_settings_actionbarlayout);

        View viewX = ((AppCompatActivity) getActivity()).getSupportActionBar().getCustomView();

        backBtn = (ImageView) viewX.findViewById(R.id.imgBack);

        TextView textView = (TextView) viewX.findViewById(R.id.heading);
        textView.setText("Inbox");

        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_home()).commit();
            }
        });

        aanishCode();

    }

    public void aanishCode()
    {
        final TabLayout tabLayout =(TabLayout) ((AppCompatActivity)getActivity()).findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPagerz=(ViewPager) ((AppCompatActivity)getActivity()).findViewById(R.id.viewPager);
        pageAdapterz pagerAdapterz=new pageAdapterz(((AppCompatActivity)getActivity()).getSupportFragmentManager(),tabLayout.getTabCount());
        viewPagerz.setAdapter(pagerAdapterz);
        viewPagerz.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tabLayout.setScrollPosition(tab.getPosition(),0f,true);
                //viewPagerz.setCurrentItem(tab.getPosition());
                //((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                  //      new usersOfchatting()).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public class pageAdapterz extends FragmentStatePagerAdapter {
        int countTab;
        public pageAdapterz(FragmentManager fm,int countTab) {
            super(fm);
            this.countTab = countTab;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    chatting Chatting = new chatting();
                    return Chatting;
                case 1:
                    usersOfchatting UserOfChatting = new usersOfchatting();
                    return UserOfChatting;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return countTab;
        }
    }
}
