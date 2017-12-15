package com.jacob.www.easycar.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.jacob.www.easycar.data.PayInfo;
import com.jacob.www.easycar.util.RxBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ASUS-NB on 2017/12/14.
 */

public class PayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
            Log.e("TAG","收到了发送的消息");
            processCustomMessage(context,intent.getExtras());
        }
    }
    private void processCustomMessage(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        SharedPreferences spf=context.getSharedPreferences("judge",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=spf.edit();
        boolean needShowInfo = false;
        boolean isDestroy = spf.getBoolean("destroy",false);
        Log.e("TAG","消息是:"+title+" "+message);
        if(!isDestroy){
            Log.e("TAG","消息是:"+title+" "+message);
            RxBus.getDefault().post(new PayInfo(message));
        }
        else {
            needShowInfo=true;
            editor.putBoolean("needShowInfo",needShowInfo);
            editor.putString("money",message);
            editor.apply();

        }


    }
}
