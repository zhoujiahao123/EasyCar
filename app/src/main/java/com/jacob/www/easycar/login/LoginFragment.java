package com.jacob.www.easycar.login;

import android.Manifest;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.main.MainActivity;
import com.jacob.www.easycar.util.RxBus;
import com.jacob.www.easycar.util.SpUtil;
import com.jacob.www.easycar.util.ToActivityUtil;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 *
 * @author 张兴锐
 * @date 2017/11/15
 */

@RuntimePermissions
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
        getPermissions();
    }


    @OnClick({R.id.login_bt, R.id.not_registered})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_bt:
                String account = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                if (account.equals("") || password.equals("")) {
                    showMsg("您的输入不合法");
                    return;
                }
                presenter.logIn(account, password);
                break;
            case R.id.not_registered:
                RxBus.getDefault().post(new Integer(1));
                break;
        }

    }

    @Override
    public void success() {
        SpUtil.putBoolean(getContext(), "has_login", true);
        ToActivityUtil.toNextActivityAndFinish(getContext(), MainActivity.class);
    }

    
    private void getPermissions(){
        LoginFragmentPermissionsDispatcher.locationNeedsWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void locationNeeds() {
        
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
