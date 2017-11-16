package com.jacob.www.easycar.login;

import android.view.View;
import android.widget.EditText;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.util.RxBus;
import com.jacob.www.easycar.widget.VerifyButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 张兴锐 on 2017/11/15.
 */

public class SignFragment extends BaseFragment implements LogInContract.View {
    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.verify_code_input)
    EditText verifyCodeInput;

    LogInPresenter presenter;
    @BindView(R.id.send_verification)
    VerifyButton sendVerification;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_signin;
    }

    @Override
    public void getActivityData() {

    }


    @Override
    public void init() {
        presenter = new LogInPresenter(this);
    }


    @OnClick({R.id.send_verification, R.id.login_bt, R.id.already_have})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_verification:
                sendVerification.startCount();
                break;
            case R.id.login_bt:
                String phoneNum = usernameInput.getText().toString();
                String passWord = passwordInput.getText().toString();
                if (phoneNum.equals("") || passWord.equals("")) {
                    showMsg("您的输入不合法");
                    return;
                }
                presenter.signIn(phoneNum, passWord);
                break;
            case R.id.already_have:
                RxBus.getDefault().post(new Integer(0));
                break;
        }
    }


    @Override
    public void success() {
        //清除
        usernameInput.setText("");
        passwordInput.setText("");
        RxBus.getDefault().post(new Integer(0));
    }

}
