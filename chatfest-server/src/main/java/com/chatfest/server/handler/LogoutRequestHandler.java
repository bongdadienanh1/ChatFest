package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseType;
import com.chatfest.server.Exceptions.RepeatLogoutException;
import com.chatfest.server.manager.UserManager;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class LogoutRequestHandler extends RequestHandler {

    public LogoutRequestHandler() {
    }

    public LogoutRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        String username = request.getHeader().getFrom();
        UserManager.onlineUserNum.decrementAndGet();
        String message = "You logged out! (Online:" + UserManager.onlineUserNum + ")";
        new SystemMsgHandler(key).single(message, ResponseType.LOGOUT_SUCCESS.getCode());
        try {
            UserManager.logout(username);
            message = "[" + username + "] logged out! (Online:" + UserManager.onlineUserNum + ")";
            new SystemMsgHandler(key).broadcast(message, ResponseType.SYSTEM_PROMPT.getCode(), key);
        } catch (RepeatLogoutException e) {
            new SystemMsgHandler(key).single(e.getMessage(), ResponseType.LOGOUT_FAIL.getCode());
        }
        key.cancel();
    }
}
