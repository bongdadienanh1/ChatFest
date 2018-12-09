package com.chatfest.common.types;

public enum ResponseType {

    LOGIN_SUCCESS((byte) 1, "login_success"),
    LOGIN_FAIL((byte) 2, "login_fail"),
    SEND_MSG_SUCCESS((byte)3, "send_msg_success"),
    SEND_MSG_FAIL((byte)4, "send_msg_fail"),
    RECV_MSG((byte) 5, "rsv_msg"),
    LOGOUT_SUCCESS((byte) 6, "logout_success"),
    LOGOUT_FAIL((byte) 7, "logout_fail"),
    SYSTEM_PROMPT((byte) 8, "system_prompt");

    private byte code;
    private String description;

    ResponseType(byte code, String description) {
        this.code = code;
        this.description = description;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}