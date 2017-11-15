package com.jacob.www.easycar.net;

/**
 * Created by 张兴锐 on 2017/11/12.
 */

public interface LoadingCallBack<T> {
    void loaded(T userBean);
    void error(String msg);
}
