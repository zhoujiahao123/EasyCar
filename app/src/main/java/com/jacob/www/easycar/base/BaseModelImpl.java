package com.jacob.www.easycar.base;

import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.net.Api;
import com.jacob.www.easycar.net.ApiException;
import com.jacob.www.easycar.net.ResponseCons;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ASUS-NB on 2017/11/12.
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
            if (tHttpBean.getCode() != 1) {
                throw new ApiException(tHttpBean.getCode());
            }
            return tHttpBean.getData();
        }
    }
}
