package com.jacob.www.easycar.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jacob.www.easycar.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    MainContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showMsg() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
    }
}
