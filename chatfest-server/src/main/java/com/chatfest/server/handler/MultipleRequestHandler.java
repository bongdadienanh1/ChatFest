package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;

import java.nio.channels.SelectionKey;

public class MultipleRequestHandler extends RequestHandler {

    public MultipleRequestHandler() {
    }

    public MultipleRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {

    }
}
