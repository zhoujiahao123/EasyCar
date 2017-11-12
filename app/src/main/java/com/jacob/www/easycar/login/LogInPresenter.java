package com.jacob.www.easycar.login;

import android.util.Log;

import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.data.UserBean;
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
    public LogInPresenter(LogInContract.View view){
        view.setPresenter(this);
        this.view = view;
    }


    @Override
    public void logIn(String phoneNum, String pas) {
        model.logIn(new Model.LoadingCallBack() {
            @Override
            public void loaded(UserBean userBean) {
                Log.e("TAG","loaded");
                if(userBean.getCode()==200){
                    user.setIcon(userBean.getData().getIcon());
                    user.setUId(userBean.getData().getUid());
                    user.setPhoneNum(userBean.getData().getPhone());
                    user.setUserName(userBean.getData().getUsername());
                    userDao.insert(user);
                    view.logInSucceed();
                }else {
                    view.logInFailed();
                }
            }
        },phoneNum,pas);
    }

    @Override
    public void start(String... args) {
        logIn(args[0],args[1]);
        Log.e("TAG","start");
    }
}
