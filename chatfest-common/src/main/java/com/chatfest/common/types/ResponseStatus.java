package com.chatfest.common.types;

public enum ResponseStatus {

    LOGIN_SUCCESS(001, "login_success"),
    LOGIN_FAIL(002, "login_fail"),
    SEND_MSG_SUCCESS(010, "send_msg_success"),
    SEND_MSG_FAIL(020, "send_msg_fail");

    private int code;
    private String msg;

    ResponseStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
