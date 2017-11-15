package com.jacob.www.easycar.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 张兴锐 on 2017/11/15.
 */

public class LoginViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public LoginViewPagerAdapter(FragmentManager fm,List<Fragment> lists) {
        super(fm);
        this.fragments = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
