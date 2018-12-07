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
        SocketChannel client = (SocketChannel) key.channel();
        String username = request.getHeader().getFrom();
        onlineUsers.decrementAndGet();
        new SystemMsgHandler(key).single("Log out success", ResponseType.LOGOUT_SUCCESS.getCode());
        try {
            UserManager.logout(username, key);
            String message = "\"" + username + "\" has logged out!";
            new SystemMsgHandler(key).broadcast(message, ResponseType.SYSTEM_PROMPT.getCode());
        } catch (RepeatLogoutException e) {
            new SystemMsgHandler(key).single(e.getMessage(), ResponseType.LOGOUT_FAIL.getCode());
        }
    }
}
