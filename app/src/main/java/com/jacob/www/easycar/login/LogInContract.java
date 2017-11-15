package com.jacob.www.easycar.login;

import com.jacob.www.easycar.base.BasePresenter;
import com.jacob.www.easycar.base.BaseView;
import com.jacob.www.easycar.data.UserBean;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInContract {
    interface View extends BaseView<UserBean>{
    }

    interface Presenter extends BasePresenter{
        void logIn(String phoneNum,String pas);
        void signIn(String phoneNum,String pas);
    }
}
