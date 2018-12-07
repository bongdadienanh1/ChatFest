package com.chatfest.common.transport;

public class Response {

    private ResponseHeader header;
    private String message;

    private Response() {
    }

    public static Response build() {
        return new Response();
    }

    public Response responseHeader(ResponseHeader header) {
        this.header = header;
        return this;
    }

    public Response message(String message) {
        this.message = message;
        return this;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
