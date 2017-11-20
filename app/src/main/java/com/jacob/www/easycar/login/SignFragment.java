package com.jacob.www.easycar.login;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.util.RxBus;
import com.jacob.www.easycar.widget.VerifyButton;
import com.mob.MobSDK;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

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

    private String countryCode;
    private static final String code = "42";
    EventHandler eh;

    @Override
    public void init() {
        presenter = new LogInPresenter(this);
        MobSDK.init(getContext().getApplicationContext(), "2275146daf5e5", "018a726083da6b9b83aa8d05a0971c50");
        eh = InitSDK();
        SMSSDK.registerEventHandler(eh); //注册短信回调
        countryCode = SMSSDK.getCountry(code)[1];
    }

    String phoneNum;

    @OnClick({R.id.send_verification, R.id.login_bt, R.id.already_have})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.send_verification:
                Log.e(TAG, "发送验证码");
                phoneNum = usernameInput.getText().toString();
                SMSSDK.getVerificationCode(countryCode, phoneNum, new OnSendMessageHandler() {
                    @Override
                    public boolean onSendMessage(String s, String s1) {
                        Log.e(TAG, "onSendMessage");
                        return false;
                    }
                });
                sendVerification.startCount();
                break;
            case R.id.login_bt:
                String phoneNum = usernameInput.getText().toString();
                String passWord = passwordInput.getText().toString();
                if ("".equals(phoneNum) || "".equals(passWord)) {
                    showMsg("您的输入不合法");
                    return;
                }
                SMSSDK.submitVerificationCode(SMSSDK.getCountry("42")[1], phoneNum, verifyCodeInput.getText().toString());
                break;
            case R.id.already_have:
                RxBus.getDefault().post(new Integer(0));
                break;
            default:
                break;
        }
    }

    @NonNull
    private EventHandler InitSDK() {
        return new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Log.e("回调完成", "回调完成");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.e("提交验证码成", "成功");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                presenter.signIn(phoneNum, passwordInput.getText().toString());
                            }
                        });
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.e("获取验证码成功", "成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    Log.e(TAG, "回调失败" + data.toString() + " " + event + " " + result);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMsg(getString(R.string.verify_code_wrong));
                        }
                    });

                }
            }
        };
    }

    @Override
    public void success() {
        //清除
        usernameInput.setText("");
        passwordInput.setText("");
        RxBus.getDefault().post(new Integer(0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注册短信回调
        SMSSDK.unregisterEventHandler(eh);
    }
}
