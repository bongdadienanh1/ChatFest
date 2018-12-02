package com.chatfest.common.transport;

import com.chatfest.common.types.ResponseStatus;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 7624285367892945735L;

    private ResponseStatus status;
    private byte[] body;

    public Response() {}

    public Response(ResponseStatus status, byte[] body) {
        this.status = status;
        this.body = body;
    }

    public static Response build() {
        return new Response();
    }

    public Response responseStatus(ResponseStatus status) {
        this.status = status;
        return this;
    }

    public Response body(byte[] body) {
        this.body = body;

        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
