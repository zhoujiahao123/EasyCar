package com.jacob.www.easycar.net;

/**
 *
 * @author 猿人
 * @date 2017/5/23
 */

public class ApiException extends RuntimeException {
    public ApiException(int status){
        super(getErrorDes(status));
    }
    private static String getErrorDes(int status){
        return StatusUtils.judgeStatus(status).desc;
    }
}
