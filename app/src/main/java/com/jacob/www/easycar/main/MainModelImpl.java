package com.jacob.www.easycar.main;

import android.util.Log;

import com.jacob.www.easycar.base.BaseModelImpl;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.data.GarageLotBean;
import com.jacob.www.easycar.net.LoadingCallBack;
import com.jacob.www.easycar.net.ResponseCons;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author ASUS-NB
 * @date 2017/11/12
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
                        Log.e("TAG", e.getMessage());
                    }

                    @Override
                    public void onNext(GarageBean garageBeanData) {
                        callBack.loaded(garageBeanData);
                        Log.e("TAG", garageBeanData.getMessage());
                    }
                });
    }

    @Override
    public void getLot(String gId, final LoadingCallBack callBack) {
        api.getGarageLot(gId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GarageLotBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error", e.getMessage());
                        if (ResponseCons.HTTP400.equals(e.getMessage()) || ResponseCons.HTTP404.equals(e.getMessage())) {
                            callBack.error("选择车库后，才能看到车库的详细信息哦");
                        } else {
                            callBack.error(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(GarageLotBean garageLotBean) {
                        callBack.loaded(garageLotBean);
                    }
                });
    }

    @Override
    public void changeInfo(String uId, String type, String value, LoadingCallBack callBack) {
        httpRequest(api.changUserInfo(uId,type,value),callBack);
    }
}
