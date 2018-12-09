package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseType;
import com.chatfest.server.manager.UserManager;

import java.nio.channels.SelectionKey;

public class SingleRequestHandler extends RequestHandler {

    public SingleRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        String from = request.getHeader().getFrom();
        String to = request.getHeader().getTo();
        String message = request.getMessage();

        SelectionKey recver = UserManager.getKey(to);
        if (recver == null) {
            String msg = "\"" + to + "\" is offline！";
            new SystemMsgHandler(key).single(msg, ResponseType.SEND_MSG_FAIL.getCode());
        } else {
            new SystemMsgHandler(key).single("Message sent!", ResponseType.SEND_MSG_SUCCESS.getCode());
            new SystemMsgHandler(recver).single(message, from, ResponseType.RECV_MSG.getCode());
        }

    }
}
