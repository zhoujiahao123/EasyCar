package com.jacob.www.easycar.login;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.data.UserBean;
import com.jacob.www.easycar.util.ProgressDialogUtils;

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
    public void getData() {

    }

    @Override
    public void init() {
        presenter = new LogInPresenter(this);
    }


    @Override
    public void showProgress() {
        ProgressDialogUtils.getInstance().showProgress(getContext(),getString(R.string.logining_please_waitting));
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
        //存入数据库
//        UserDao userDao = App.getDaoSession().getUserDao();
//        userDao.insert(data);
    }


    @OnClick(R.id.login_bt)
    public void onViewClicked() {
        String account = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(account.equals("") || password.equals("")){
            showMsg("您的输入不合法");
            return;
        }
        presenter.logIn(account,password);
    }
}
