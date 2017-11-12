package com.jacob.www.easycar.main;

import com.jacob.www.easycar.base.BasePresenter;
import com.jacob.www.easycar.base.BaseView;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface MainContract {
    interface View  extends BaseView<Presenter>{
        void showMsg();
    }
    interface Presenter extends BasePresenter{
        //需要presenter做的操作
        void loadSome();
    }
}
