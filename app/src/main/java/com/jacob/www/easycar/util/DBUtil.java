package com.jacob.www.easycar.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jacob.www.easycar.greendao.DaoMaster;
import com.jacob.www.easycar.greendao.DaoSession;


/**
 * Created by ASUS-NB on 2017/11/18.
 */

public class DBUtil {
    static DaoSession daoSession;
    static DaoMaster daoMaster;
    static DaoMaster.DevOpenHelper helper;
    static SQLiteDatabase database ;
    public static DaoSession getDaoSession(Context context){
        helper = new DaoMaster.DevOpenHelper(context,"EasyCar-DB",null);
        database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession  = daoMaster.newSession();
        return daoSession;
    }
}
