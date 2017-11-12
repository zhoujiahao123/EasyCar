package com.jacob.www.easycar.base;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface BaseView<T> {
   void showProgress();
   void hideProgress();
   void showMsg(String msg);
}
