package com.jacob.www.easycar.login;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseActivity;
import com.jacob.www.easycar.util.RxBus;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInActivity extends BaseActivity {


    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    PageIndicatorView indicator;
    Subscription subscription;

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
        LoginViewPagerAdapter adapter = new LoginViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        indicator.setViewPager(viewPager);

        subscription = RxBus.getDefault().toObservable(Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        viewPager.setCurrentItem(i);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription = null;
        }
    }
}
