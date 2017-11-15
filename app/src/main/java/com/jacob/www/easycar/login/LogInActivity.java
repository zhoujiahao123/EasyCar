package com.jacob.www.easycar.login;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseActivity;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    PageIndicatorView indicator;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        //加入Fragment
        Fragment loginFragment = new LoginFragment();
        Fragment signFragment = new SignFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(loginFragment);
        fragments.add(signFragment);
        LoginViewPagerAdapter adapter = new LoginViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        indicator.setViewPager(viewPager);
    }

    
}
