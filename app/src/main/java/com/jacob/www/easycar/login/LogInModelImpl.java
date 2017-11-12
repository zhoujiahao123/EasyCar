package com.jacob.www.easycar.login;

import android.util.Log;

import com.jacob.www.easycar.base.BaseModelImpl;
import com.jacob.www.easycar.data.UserBean;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class LogInModelImpl extends BaseModelImpl implements Model {
    @Override
    public void logIn(final LoadingCallBack callBack, String phoneNum, String pas) {
        api.logIn(phoneNum,pas).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(),e.getMessage());
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        Log.e("TAG","onNext");
                            callBack.loaded(userBean);
                    }
                });
    }
}
