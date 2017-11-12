package com.jacob.www.easycar.base;

import com.jacob.www.easycar.net.Api;
import com.jacob.www.easycar.net.ResponseCons;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
//                .baseUrl()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = mRetrofit.create(Api.class);
    }
}
