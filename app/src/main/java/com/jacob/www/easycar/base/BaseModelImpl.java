package com.jacob.www.easycar.base;

import android.util.Log;

import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.net.Api;
import com.jacob.www.easycar.net.ApiException;
import com.jacob.www.easycar.net.FilterSubscriber;
import com.jacob.www.easycar.net.LoadingCallBack;
import com.jacob.www.easycar.net.ResponseCons;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 * @author ASUS-NB
 * @date 2017/11/12
 */

public class BaseModelImpl {
    private OkHttpClient client = new OkHttpClient.Builder()
            .build();
    private Retrofit mRetrofit;
    protected Api api;

    public BaseModelImpl() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ResponseCons.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = mRetrofit.create(Api.class);
    }

    public Observable filterStatus(Observable observable) {
        return observable.map(new ResultFilter());
    }

    private class ResultFilter<T> implements Func1<Data<T>, T> {
        @Override
        public T call(Data<T> tHttpBean) {
            if (tHttpBean.getCode() != 200) {
                Log.e("ResultFilter","这里失败");
                throw new ApiException(tHttpBean.getCode());

            }
            return tHttpBean.getData();
        }
    }

    protected <T> void httpRequest(Observable<Data<T>> observable, final LoadingCallBack callBack) {
        filterStatus(observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FilterSubscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                         super.onError(e);
                        callBack.error(error);
                        Log.e("httpRequest","这里失败");
                    }

                    @Override
                    public void onNext(T data) {
                        callBack.loaded(data);
                        Log.e("httpRequest","这里完成");
                    }
                });
        
    }
}
