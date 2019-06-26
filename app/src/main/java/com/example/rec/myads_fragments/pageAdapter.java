package com.example.rec.myads_fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class pageAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public pageAdapter(FragmentManager fm,int countTab) {
        super(fm);
        this.countTab = countTab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                seller Seller = new seller();
                return Seller;
            case 1:
                leaser Leaser = new leaser();
                return Leaser;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
