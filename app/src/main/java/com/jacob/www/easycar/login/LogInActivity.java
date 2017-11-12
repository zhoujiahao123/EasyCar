package com.jacob.www.easycar.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jacob.www.easycar.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInActivity extends AppCompatActivity implements LogInContract.View {


    @BindView(R.id.phone_num_text)
    TextInputEditText phoneNumText;
    @BindView(R.id.pas_num_text)
    TextInputEditText pasNumText;
    @BindView(R.id.btn_login)
    Button btnLogin;
    LogInContract.Presenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LogInPresenter(this);
    }

    @Override
    public void setPresenter(LogInContract.Presenter presenter) {

    }

    @Override
    public void logInSucceed() {
        Log.e("TAG","保存成功");
    }

    @Override
    public void logInFailed() {
        Log.e("TAG","保存失败");
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        presenter.start(phoneNumText.getText().toString(),pasNumText.getText().toString());
    }
}
