package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseStatus;

import java.nio.channels.SelectionKey;

public class MultipleRequestHandler extends RequestHandler {

    public MultipleRequestHandler() {
    }

    public MultipleRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        String from = request.getFrom();
        byte[] body = request.getBody();
        // 响应
        new SystemMsgHandler(key).single("send msg success", ResponseStatus.SEND_MSG_SUCCESS);
        // 群发
        new SystemMsgHandler(key).broadcast(new String(body), from);
    }
}
