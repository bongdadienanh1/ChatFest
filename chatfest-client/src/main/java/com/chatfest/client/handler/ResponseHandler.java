package com.chatfest.client.handler;

import com.chatfest.common.transport.Response;

public abstract class ResponseHandler {

    Response response;

    public ResponseHandler(Response response) {
        this.response = response;
    }

    public abstract void handle();
}
