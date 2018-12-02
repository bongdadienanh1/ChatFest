package com.chatfest.common.types;

public enum RequestType {
    LOGIN(1, "login"),
    LOGOUT(2, "logout"),
    SINGLE(3, "single"),
    MULTIPLE(4, "multiple");

    private int code;
    private String description;

    RequestType(int code, String desciption) {
        this.code = code;
        this.description = desciption;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
