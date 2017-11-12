package com.jacob.www.easycar.login;

import com.jacob.www.easycar.data.UserBean;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface Model {
    interface LoadingCallBack{
        void loaded(UserBean userBean);
    }

    void logIn(LoadingCallBack callBack,String phoneNum,String pas);
}
