package com.jacob.www.easycar.login;

import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.widget.Toast;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseActivity;
import com.jacob.www.easycar.util.ProgressDialogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInActivity extends BaseActivity implements LogInContract.View {


    @BindView(R.id.phone_num_text)
    TextInputEditText phoneNumText;
    @BindView(R.id.pas_num_text)
    TextInputEditText pasNumText;

    LogInContract.Presenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        presenter = new LogInPresenter(this);
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

    @Override
    public void showProgress() {
        ProgressDialogUtils.getInstance().showProgress(this,getString(R.string.loading));
    }

    @Override
    public void hideProgress() {
        ProgressDialogUtils.getInstance().hideProgress();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
   

}
