package com.jacob.www.easycar.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-NB on 2017/11/16.
 */

public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments  = new ArrayList<>();

    public void addFragment(Fragment fragment){
        mFragments.add(fragment);
    }
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
