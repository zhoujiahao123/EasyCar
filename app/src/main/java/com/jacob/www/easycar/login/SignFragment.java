package com.jacob.www.easycar.login;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.data.UserBean;
import com.jacob.www.easycar.util.ProgressDialogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

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
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_signin;
    }

    @Override
    public void getData() {

    }

    @Override
    public void init() {
        presenter = new LogInPresenter(this);
        SMSSDK.getInstance().setIntervalTime(60*1000);//设置前后获取验证码的时间间隔,这里设置60秒
    }
    String phoneNum;
    @OnClick({R.id.send_verification, R.id.login_bt,R.id.already_have})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_verification:
                phoneNum  = usernameInput.getText().toString();
                Log.e("TAG","send_verification"+phoneNum);
                SMSSDK.getInstance().getSmsCodeAsyn(phoneNum, "1", new SmscodeListener() {
                    @Override
                    public void getCodeSuccess(String s) {
                        Log.e("TAG","获取验证码成功");
                    }

                    @Override
                    public void getCodeFail(int i, String s) {
                        Log.e("TAG",i+"获取验证码失败"+s);
                    }
                });
                break;
            case R.id.login_bt:
                String phoneNum = usernameInput.getText().toString();
                String passWord = passwordInput.getText().toString();
                if(phoneNum.equals("") || passWord.equals("")){
                    showMsg("您的输入不合法");
                    return;
                }
                SMSSDK.getInstance().checkSmsCodeAsyn(phoneNum, verifyCodeInput.getText().toString(), new SmscheckListener() {
                    @Override
                    public void checkCodeSuccess(String s) {
                        Log.e("TAG","验证码验证成功");
                        presenter.logIn(usernameInput.getText().toString(),passwordInput.getText().toString());
                    }

                    @Override
                    public void checkCodeFail(int i, String s) {
                        Log.e("TAG","验证码验证失败");
                    }
                });
                break;
            case R.id.already_have:
                
                break;
        }
    }

    @Override
    public void showProgress() {
        ProgressDialogUtils.getInstance().showProgress(getContext(),getString(R.string.please_watting));
    }

    @Override
    public void hideProgress() {
        ProgressDialogUtils.getInstance().hideProgress();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDatas(UserBean data) {
        Log.i(TAG,data.toString());
    }
}
