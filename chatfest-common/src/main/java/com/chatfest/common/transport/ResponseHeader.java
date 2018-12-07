package com.chatfest.common.transport;

import java.util.Date;

public class ResponseHeader {

    public static final byte MAGIC =  0x68;      // 1 Byte
    private byte type;                           // 1 Byte
    private String from;                         // 8 Byte
    private long timestamp;                      // 8 Byte
    private int contentLength;                   // 4 Byte

    private ResponseHeader() {timestamp = new Date().getTime();}

    public static ResponseHeader build() {
        return new ResponseHeader();
    }

    public ResponseHeader type(byte type) {
        this.type = type;
        return this;
    }

    public ResponseHeader from(String from) {
        this.from = from;
        return this;
    }

    public ResponseHeader timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ResponseHeader contentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }
}
