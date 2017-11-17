package com.jacob.www.easycar.main;

import com.jacob.www.easycar.base.BaseModelImpl;
import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.net.FilterSubscriber;
import com.jacob.www.easycar.net.LoadingCallBack;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class MainModelImpl extends BaseModelImpl implements Model {
    @Override
    public void getData(double longitude, double latitude, double distance, final LoadingCallBack callBack) {
        httpRequest(api.getNearGarage(longitude, latitude, distance),callBack);
    }
}
