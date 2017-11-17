package com.jacob.www.easycar.main;

import android.util.Log;

import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.net.LoadingCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class MainPresenter implements MainContract.Presenter {
    Model model = new MainModelImpl();
    MainContract.View view ;
    public MainPresenter(MainContract.View view){
        this.view = view;
    }

    @Override
    public void getNearGarage(double longitude, double latitude, double distance) {
        model.getData(longitude, latitude, distance, new LoadingCallBack() {
            @Override
            public void loaded(Object data) {
                Log.e("getNearGarage","搜索车库成功");
                if(data instanceof GarageBean){
                    view.showGarage((GarageBean) data);
                }
            }

            @Override
            public void error(String msg) {
                Log.e("getNearGarage","失败了");
                view.showMsg(msg);
            }
        });
    }

    @Override
    public void start(double longitude, double latitude, double distance) {
        getNearGarage(longitude,latitude,distance);
    }

    @Override
    public void start(String... args) {

    }
}
