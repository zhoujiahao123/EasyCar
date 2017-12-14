package com.jacob.www.easycar.net;

import com.jacob.www.easycar.data.Data;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.data.GarageLotBean;
import com.jacob.www.easycar.data.User;
import com.jacob.www.easycar.data.UserParkVO;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * @author ASUS-NB
 * @date 2017/11/12
 */

public interface Api {
    @FormUrlEncoded
    @POST("login")
    Observable<Data<User>> logIn(@Field("phone") String phoneNum, @Field("password") String pas);
    
    @FormUrlEncoded
    @POST("user")
    Observable<Data<User>> signIn(@Field("phone") String phoneNum, @Field("password") String password);

    @GET("garage/near")
    Observable<GarageBean> getNearGarage(@Query("longitude") double longitude, @Query("latitude") double latitude, @Query("distance") double distance);

    @GET("garage")
    Observable<GarageLotBean> getGarageLot(@Query("garageId")String garageId);
    
    @FormUrlEncoded
    @PUT("user/{uid}")
    Observable<Data<User>> changUserInfo(@Path("uid") String uId,@Field("type") String type,@Field("value") String value);
    
    @FormUrlEncoded
    @PUT("park/{uid}")
    Observable<Data<UserParkVO>> addUserCarkPosition(@Path("uid") String uId, @Field("garageId") String garageId, @Field("parkId") String parkId);
    
    @GET("park/{uid}")
    Observable<Data<UserParkVO>> getGargetResult(@Path("uid") String uId);
    
    
    @DELETE("park/{uid}")
    Observable<Data<Object>> deletePark(@Path("uid") String uId);
}
