package com.example.aflah.tracki_master.View.Toko;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MenuAdapter extends FragmentPagerAdapter {

    int noOfMenu;

    public MenuAdapter(FragmentManager fm, int noOfMenu) {
        super(fm);
        this.noOfMenu = noOfMenu;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MakananFragment makanan = new MakananFragment();
                return makanan;
            case 1:
                MinumanFragment minuman = new MinumanFragment();
                return minuman;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.noOfMenu;
    }
}
