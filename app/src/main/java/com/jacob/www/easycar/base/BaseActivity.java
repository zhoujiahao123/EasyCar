package com.jacob.www.easycar.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by 张兴锐 on 2017/11/12.
 */

public abstract class BaseActivity extends AppCompatActivity{
    public abstract int getLayoutId();
    public abstract void init();
    protected String TAG ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        TAG = this.getClass().getSimpleName();
        init();
    }
}
