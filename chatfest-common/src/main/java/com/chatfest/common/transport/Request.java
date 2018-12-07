package com.chatfest.common.transport;

public class Request {

    private RequestHeader header;
    private String message;

    private Request() {
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