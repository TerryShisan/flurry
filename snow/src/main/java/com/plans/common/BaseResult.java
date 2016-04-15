package com.plans.common;

import java.io.Serializable;

public class BaseResult implements Serializable {

    private static final long serialVersionUID = -7970980981956108271L;

    private int code;
    private String message;
    private boolean success;

    public BaseResult() {
        this.code = ResultCode.SUCCESS.code;
        this.message = ResultCode.SUCCESS.message;
        this.success = ResultCode.SUCCESS.success;
    }

    public void setError(ResultCode rc, Object... args) {
        this.code = rc.code;
        this.success = false;
        if (args == null || args.length == 0) {
            this.message = rc.message;
        } else {
            this.message = String.format(rc.message, args);
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
