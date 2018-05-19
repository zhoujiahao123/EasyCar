package com.jacob.www.easycar.login;

import android.util.Log;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.data.User;
import com.jacob.www.easycar.greendao.DaoSession;
import com.jacob.www.easycar.greendao.UserDao;
import com.jacob.www.easycar.net.LoadingCallBack;

import com.jacob.www.easycar.util.TagAliasOperateHelper;


/**
 * @author ASUS-NB
 * @date 2017/11/12
 */

public class LogInPresenter implements LogInContract.Presenter {

    Model model;
    LogInContract.View view;

    public LogInPresenter(LogInContract.View view) {
        this.view = view;
        model = new LogInModelImpl();
        DaoSession daoSession = App.getDaoSession();
        UserDao userDao = daoSession.getUserDao();
    }


    @Override
    public void logIn(String phoneNum, String pas) {
        view.showProgress();
        model.logIn(new LoadingCallBack<User>() {
            @Override
            public void loaded(User user) {
                Log.e("TAG", "loaded");
                view.hideProgress();

                //这里将id作为别名设置
                int action = -1;
                boolean isAliasAction = true;
                TagAliasOperateHelper.TagAliasBean tagAliasBean  = new TagAliasOperateHelper.TagAliasBean();
                TagAliasOperateHelper.sequence++;
                action = TagAliasOperateHelper.ACTION_SET;
                tagAliasBean.action = action;
                tagAliasBean.alias= user.getUid();
                Log.e("TAG", tagAliasBean.alias);
                tagAliasBean.isAliasAction = isAliasAction;
                TagAliasOperateHelper.getInstance().handleAction(App.getAppContext(),TagAliasOperateHelper.sequence,tagAliasBean);

                UserDao userDao = App.getDaoSession().getUserDao();
                userDao.deleteAll();

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
        model.signIn(new LoadingCallBack<User>() {
            @Override
            public void loaded(User User) {
                view.hideProgress();
                view.showMsg(App.getAppContext().getString(R.string.sign_in_success));
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
    public void start(String... args) {
        logIn(args[0], args[1]);
        Log.e("TAG", "start");
    }
}
