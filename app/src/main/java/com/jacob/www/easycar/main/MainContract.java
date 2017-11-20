package com.jacob.www.easycar.main;

import com.jacob.www.easycar.base.BasePresenter;
import com.jacob.www.easycar.base.BaseView;
import com.jacob.www.easycar.data.GarageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface MainContract {
    interface View  extends BaseView{
        void showGarage(GarageBean garageBeans);

        void showLot(String lot);
    }
    interface Presenter extends BasePresenter{
        //需要presenter做的操作
        void getNearGarage(double longitude,double latitude,double distance);

        void start(double longitude,double latitude,double distance);

        void getGarageLot(String gId);
    }
}
