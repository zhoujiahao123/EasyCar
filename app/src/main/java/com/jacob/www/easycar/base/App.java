package com.jacob.www.easycar.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zxr.medicalaid.DaoMaster;
import com.zxr.medicalaid.DaoSession;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ASUS-NB on 2017/11/18.
 */

public class App extends Application{
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Log.e("TAG","app初始化");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ZXingLibrary.initDisplayOpinion(this);
    }
    static DaoSession daoSession;
    static DaoMaster daoMaster;
    static DaoMaster.DevOpenHelper helper;
    static SQLiteDatabase database ;
    public static DaoSession getDaoSession(){
        helper = new DaoMaster.DevOpenHelper(context,"EasyCar-DB",null);
        database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession  = daoMaster.newSession();
        return daoSession;
    }
    public static Context getAppContext(){
        return context;
    }
}
