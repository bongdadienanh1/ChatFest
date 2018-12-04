package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseStatus;
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
        String username = request.getFrom();
        onlineUsers.decrementAndGet();
        new SystemMsgHandler(key).single("Log out success", ResponseStatus.LOGOUT_SUCCESS);
        try {
            UserManager.logout(username, key);
            String message = "\"" + username + "\" has logged out!";
            new SystemMsgHandler(key).broadcast(message);
        } catch (RepeatLogoutException e) {
            new SystemMsgHandler(key).single(e.getMessage(), ResponseStatus.LOGOUT_FAIL);
        }
    }
}
