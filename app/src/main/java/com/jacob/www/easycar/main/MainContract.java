package com.jacob.www.easycar.main;

import com.jacob.www.easycar.base.BasePresenter;
import com.jacob.www.easycar.base.BaseView;
import com.jacob.www.easycar.data.GarageBean;

/**
 * @author ASUS-NB
 * @date 2017/11/12
 */

public interface MainContract {
    interface View extends BaseView {
        void showGarage(GarageBean garageBeans);

        void showLot(String lot);
        
        void changeSuccess();
        
        void getGargetSuccess(int parkId);
        
        void addUserParkPositionSuccess(int parkId,String gId);
        
        
    }

    interface Presenter extends BasePresenter {
        /**
         * 需要presenter做的操作
         */
        void getNearGarage(double longitude, double latitude, double distance);

        void start(double longitude, double latitude, double distance);

        void getGarageLot(String gId);

        void changeInfo(String uId, String type, String value);
        
        
        void getGargetResult(String uId);
        
        void addUserPosition(String uId,String garageId,String parkId);

        void deletePark(String uId);
        
    }
}
