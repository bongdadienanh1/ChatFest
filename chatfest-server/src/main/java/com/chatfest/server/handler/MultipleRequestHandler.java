package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseType;

import java.nio.channels.SelectionKey;

public class MultipleRequestHandler extends RequestHandler {

    public MultipleRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        String from = request.getHeader().getFrom();
        String message = request.getMessage();
        // 响应
        new SystemMsgHandler(key).single("Message sent!", ResponseType.SEND_MSG_SUCCESS.getCode());
        // 群发
        new SystemMsgHandler(key).broadcast(message, from, ResponseType.RECV_MSG.getCode(), key);
    }
}
