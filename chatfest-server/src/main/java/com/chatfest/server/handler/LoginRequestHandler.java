package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseStatus;
import com.chatfest.server.Exceptions.RepeatLoginException;
import com.chatfest.server.manager.UserManager;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class LoginRequestHandler extends RequestHandler {

    public LoginRequestHandler() {
    }

    public LoginRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        SocketChannel client = (SocketChannel) key.channel();
        String username = request.getFrom();
        String password = new String(request.getBody(), StandardCharsets.UTF_8);
        if (UserManager.checkLogin(username, password)) {
            // 登录成功
            onlineUsers.incrementAndGet();
            new SystemMsgHandler(key).single("", ResponseStatus.LOGIN_SUCCESS);
            try {
                UserManager.login(username, key);
                // 广播登录消息
                String message = "\"" + username + "\" has logged in!";
                new SystemMsgHandler(key).broadcast(message);
            } catch (RepeatLoginException e) {
                String msg = e.getMessage();
                new SystemMsgHandler(key).single(msg, ResponseStatus.LOGIN_FAIL);
            }
        } else {
            new SystemMsgHandler(key).single("Your username or password may be wrong.", ResponseStatus.LOGIN_FAIL);
        }
    }

}
