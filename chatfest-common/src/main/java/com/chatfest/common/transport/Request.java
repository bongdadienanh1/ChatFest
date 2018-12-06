package com.chatfest.common.transport;

import com.chatfest.common.types.RequestType;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private static final long serialVersionUID = -2821427368528745569L;
    private RequestHeader header;
    private String message;

    public Request() {
    }

    public Request(RequestHeader header, String message) {
        this.header = header;
        this.message = message;
    }

    public static Request build() {
        return new Request();
    }

    public Request requestHeader(RequestHeader header) {
        this.header = header;
        return this;
    }

    public Request message(String message) {
        this.message = message;
        return this;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}