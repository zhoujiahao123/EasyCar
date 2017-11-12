package com.jacob.www.easycar.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zxr.medicalaid.DaoMaster;
import com.zxr.medicalaid.DaoSession;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class App extends Application{
    private static Context context;
    public static Context getAppContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    static DaoSession daoSession;
    static DaoMaster daoMaster;
    static DaoMaster.DevOpenHelper helper;
    static SQLiteDatabase database ;
    public static DaoSession getDaoSession(){
       helper = new DaoMaster.DevOpenHelper(App.getAppContext(),"EasyCar-DB",null);
        database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession  = daoMaster.newSession();
        return daoSession;
    }
}
