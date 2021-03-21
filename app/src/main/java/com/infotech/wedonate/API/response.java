package com.infotech.wedonate.API;

import com.google.gson.annotations.SerializedName;

public class response {
    @SerializedName("msg")
    String msg;

    int code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "signup_response{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
