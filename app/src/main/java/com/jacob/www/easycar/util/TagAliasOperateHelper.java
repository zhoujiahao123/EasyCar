package com.jacob.www.easycar.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;

/**
 * Created by ASUS-NB on 2017/7/26.
 */

public class TagAliasOperateHelper {
    public static final int ACTION_SET = 2;
    public static int sequence = 1;
    private static TagAliasOperateHelper mInstance ;
    public static TagAliasOperateHelper getInstance(){
        if(mInstance ==null){
            synchronized (TagAliasOperateHelper.class){
                if(mInstance ==null){
                    mInstance = new TagAliasOperateHelper();
                }
            }
        }
        return mInstance;
    }

    public void handleAction(Context context, int sequence, TagAliasBean tagAliasBean){
        if(tagAliasBean.isAliasAction){
            switch (tagAliasBean.action){
                case ACTION_SET:
                    Log.e("TAG","设置别名成功"+tagAliasBean.alias);
                    JPushInterface.setAlias(context,sequence,tagAliasBean.alias);
                    break;
            }
        }else {
            switch (tagAliasBean.action){
                case ACTION_SET:
                    Log.e("TAG","btn_set_alisa");
                    JPushInterface.setTags(context,sequence,tagAliasBean.tags);
                    Toast.makeText(context,"设置标签成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    public static class TagAliasBean{
        public int action;
        Set<String> tags;
        public String alias;
        public boolean isAliasAction;

        @Override
        public String toString() {
            return "TagAliasBean{"+"action="+action+",tags="+tags
                    +",alias="+alias+",isAliasAction="+isAliasAction+"}";
        }
    }
    public void onTagOperateResult(Context context, JPushMessage jPushMessage){
        Log.e("TAG",jPushMessage.getErrorCode()+"");
    }
}
