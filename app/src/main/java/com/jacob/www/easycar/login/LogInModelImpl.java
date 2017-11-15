package com.jacob.www.easycar.login;

import android.util.Log;

import com.jacob.www.easycar.base.BaseModelImpl;
import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.data.UserBean;
import com.jacob.www.easycar.net.FilterSubscriber;
import com.jacob.www.easycar.net.LoadingCallBack;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInModelImpl extends BaseModelImpl implements Model {
    @Override
    public void logIn(final LoadingCallBack callBack, String phoneNum, String pas) {
        api.logIn(phoneNum, pas).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<Data<UserBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i(this.getClass().getSimpleName(), e.getMessage());
                        callBack.error(e.getMessage());
                    }

                    @Override
                    public void onNext(Data<UserBean> userBeanData) {
                        Log.e("TAG", "onNext");
                        callBack.loaded(userBeanData);
                    }
                });
    }

    @Override
    public void signIn(final LoadingCallBack<UserBean> callBack, String phoneNum, String pas) {
        filterStatus(api.signIn(phoneNum, pas)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<UserBean>() {
                    @Override
                    public void onCompleted() {
                        
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBack.error(e.getMessage());
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        callBack.loaded(userBean);
                    }

                  
                });
                 
    }
}