package com.jacob.www.easycar.login;

import android.util.Log;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.data.UserBean;
import com.jacob.www.easycar.net.LoadingCallBack;
import com.zxr.medicalaid.DaoSession;
import com.zxr.medicalaid.User;
import com.zxr.medicalaid.UserDao;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInPresenter implements LogInContract.Presenter {
    DaoSession daoSession = App.getDaoSession();
    UserDao userDao = daoSession.getUserDao();
    User user = new User();
    Model model = new LogInModelImpl();
    LogInContract.View view;

    public LogInPresenter(LogInContract.View view) {
        this.view = view;
    }


    @Override
    public void logIn(String phoneNum, String pas) {
        view.showProgress();
        model.logIn(new LoadingCallBack<UserBean>() {
            @Override
            public void loaded(UserBean userBean) {
                Log.e("TAG", "loaded");
                view.hideProgress();
                user.setIcon(userBean.getIcon());
                user.setUId(userBean.getUid());
                user.setPhoneNum(userBean.getPhone());
                user.setUserName(userBean.getUsername());
                userDao.insert(user);
                view.showMsg(App.getAppContext().getString(R.string.login_success));
                view.success();
            }

            @Override
            public void error(String msg) {
                view.hideProgress();
                view.showMsg(msg);
            }
        }, phoneNum, pas);
    }

    @Override
    public void signIn(String phoneNum, String pas) {
        view.showProgress();
        model.signIn(new LoadingCallBack<UserBean>() {
            @Override
            public void loaded(UserBean userBean) {
                view.hideProgress();
                view.success();
            }

            @Override
            public void error(String msg) {
                view.hideProgress();
                view.showMsg(msg);
            }
        },phoneNum,pas);
    }

    @Override
    public void start(String... args) {
        logIn(args[0], args[1]);
        Log.e("TAG", "start");
    }
}
