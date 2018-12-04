package com.chatfest.common.types;

public enum ResponseStatus {

    LOGIN_SUCCESS(681000, "login_success"),
    LOGIN_FAIL(682000, "login_fail"),
    SEND_MSG_SUCCESS(680100, "send_msg_success"),
    SEND_MSG_FAIL(680200, "send_msg_fail"),
    RCV_MSG(680300, "rsv_msg"),
    LOGOUT_SUCCESS(680010, "logout_success"),
    LOGOUT_FAIL(680020, "logout_fail"),
    SYSTEM_PROMPT(680001, "system_prompt");

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