package com.jacob.www.easycar.data;

/**
 * Created by 张兴锐 on 2017/11/12.
 */

public class Data<T> {
    private int code;
    private String message;
    private T data;

    public Data(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
