package com.jacob.www.easycar.main;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainContract.View{

    MainContract.Presenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        presenter = new MainPresenter(this);
    }
    


    @Override
    public void showMsg(String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showProgress() {
        
    }

    @Override
    public void hideProgress() {

    }
}
