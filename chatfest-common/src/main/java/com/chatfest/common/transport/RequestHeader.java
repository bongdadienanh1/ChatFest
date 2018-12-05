package com.chatfest.common.transport;

import com.chatfest.common.types.RequestType;

import java.util.Date;

public class RequestHeader {

    public static final byte MAGIC = 0x68;  // 1  Byte
    private byte type;                      // 1  Byte
    private String from;                    // 8  Bytes
    private String to;                      // 8  Bytes
    private long timestamp;                 // 8  Bytes
    private int contentLength;              // 4  Bytes

    private RequestHeader() {
        timestamp = new Date().getTime();
    }

    public static RequestHeader build() {
        return new RequestHeader();
    }

    public RequestHeader type(byte type) {
        this.type = type;
        return this;
    }

    public RequestHeader from(String from) {
        this.from = from;
        return this;
    }

    public RequestHeader to(String to) {
        this.to = to;
        return this;
    }

    public RequestHeader timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public RequestHeader contentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public byte getMAGIC() {
        return MAGIC;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
