package com.jacob.www.easycar.login;

import android.widget.EditText;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 张兴锐 on 2017/11/15.
 */

public class LoginFragment extends BaseFragment implements LogInContract.View {

    LogInContract.Presenter presenter;
    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void getActivityData() {

    }

    @Override
    public void init() {
        presenter = new LogInPresenter(this);
    }


    @OnClick(R.id.login_bt)
    public void onViewClicked() {
        String account = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (account.equals("") || password.equals("")) {
            showMsg("您的输入不合法");
            return;
        }
        presenter.logIn(account, password);
    }

    @Override
    public void success() {
        
    }
}
