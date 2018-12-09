package com.chatfest.common.transport;

public enum Properties {
    REQUEST_HEADER_LENGTH(30),
    RESPONSE_HEADER_LENGTH(22);

    private int val;

    Properties(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
