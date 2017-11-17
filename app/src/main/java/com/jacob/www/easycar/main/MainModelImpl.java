package com.jacob.www.easycar.main;

import android.util.Log;

import com.jacob.www.easycar.base.BaseModelImpl;
import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.net.FilterSubscriber;
import com.jacob.www.easycar.net.LoadingCallBack;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class MainModelImpl extends BaseModelImpl implements Model {
    @Override
    public void getData(double longitude, double latitude, double distance, final LoadingCallBack callBack) {
        api.getNearGarage(longitude, latitude, distance)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GarageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG",e.getMessage());
                    }

                    @Override
                    public void onNext(GarageBean garageBeanData) {
                        callBack.loaded(garageBeanData);
                        Log.e("TAG",garageBeanData.getMessage());
                    }
                });
    }
}
