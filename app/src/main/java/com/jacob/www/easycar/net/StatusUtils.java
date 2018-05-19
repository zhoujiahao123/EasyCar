package com.jacob.www.easycar.net;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.App;

/**
 * Created by 猿人 on 2017/5/23.
 */

public class StatusUtils {
    public static class StatusResult{
        public int status;
        public String desc;
        public boolean isSuccess;
    }
    private static StatusResult mStatusResult = new StatusResult();
    public static StatusResult judgeStatus(int status) {
        String desc = "failed";
        boolean isSuccess = false;
        switch (status) {
            case 200:
                desc = App.getAppContext().getString(R.string.success);
                isSuccess = true;
                break;
            case 403:
                desc = App.getAppContext().getString(R.string.login_failed_phoneNum_wrong_or_passWord_wrong);
                break;
            default:
                desc = App.getAppContext().getString(R.string.failed);
                break;
        }
        mStatusResult.status = status;
        mStatusResult.desc = desc;
        mStatusResult.isSuccess = isSuccess;
        return mStatusResult;
    }
}
