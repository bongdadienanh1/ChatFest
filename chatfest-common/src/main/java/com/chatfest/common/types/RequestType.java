package com.chatfest.common.types;

public enum RequestType {

    LOGIN((byte) 1, "login"),
    LOGOUT((byte) 2, "logout"),
    SINGLE((byte) 3, "single"),
    MULTIPLE((byte) 4, "multiple"),
    QUERY((byte) 5, "query");

    private byte code;
    private String description;

    RequestType(byte code, String desciption) {
        this.code = code;
        this.description = desciption;
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
