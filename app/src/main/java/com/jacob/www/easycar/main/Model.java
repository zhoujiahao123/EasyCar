package com.jacob.www.easycar.main;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface Model  {
    interface LoadingCallBack{
        void onLoaded();//数据加载成功
    }

    void getData(LoadingCallBack callBack);
}
