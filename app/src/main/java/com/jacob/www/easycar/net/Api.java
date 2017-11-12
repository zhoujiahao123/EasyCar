package com.jacob.www.easycar.net;

import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.data.UserBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Observable<Data<UserBean>> logIn(@Field("phone") String phoneNum, @Field("password") String pas);
}
