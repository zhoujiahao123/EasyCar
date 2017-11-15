package com.jacob.www.easycar.login;

import com.jacob.www.easycar.net.LoadingCallBack;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface Model {
    void logIn(LoadingCallBack callBack, String phoneNum, String pas);
}
