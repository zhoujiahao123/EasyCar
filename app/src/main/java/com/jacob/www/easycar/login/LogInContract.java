package com.jacob.www.easycar.login;

import com.jacob.www.easycar.base.BasePresenter;
import com.jacob.www.easycar.base.BaseView;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInContract {
    interface View extends BaseView{
        void logInSucceed();
        void logInFailed();
    }

    interface Presenter extends BasePresenter{
        void logIn(String phoneNum,String pas);
    }
}
