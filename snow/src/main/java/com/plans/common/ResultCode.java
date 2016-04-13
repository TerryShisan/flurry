package com.plans.common;

import java.io.Serializable;

public enum ResultCode implements Serializable {

    SUCCESS(200,"successful",true),
    ILLEGAL_PARAM(-101,"parameter error,details: %s,paramater %s is %s",false),
    EXCEPTION(500,"server exception,details: %s",false),
    FAILED(-1,"request failed,details: %s",false);
    
    public final int     code;
    public final String  message;
    public final boolean success;

    ResultCode(int code, String message, boolean success){
        this.code = code;
        this.message = message;
        this.success = success;
    }

}
