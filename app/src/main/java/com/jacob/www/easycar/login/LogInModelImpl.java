package com.jacob.www.easycar.login;

import com.jacob.www.easycar.base.BaseModelImpl;
import com.jacob.www.easycar.data.UserBean;
import com.jacob.www.easycar.net.LoadingCallBack;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInModelImpl extends BaseModelImpl implements Model {
    @Override
    public void logIn(final LoadingCallBack callBack, String phoneNum, String pas) {
        httpRequest(api.logIn(phoneNum, pas), callBack);
    }


    @Override
    public void signIn(final LoadingCallBack<UserBean> callBack, String phoneNum, String pas) {
        httpRequest(api.signIn(phoneNum, pas), callBack);
    }
}