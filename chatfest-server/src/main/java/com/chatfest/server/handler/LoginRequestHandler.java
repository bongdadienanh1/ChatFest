package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseType;
import com.chatfest.server.Exceptions.RepeatLoginException;
import com.chatfest.server.manager.UserManager;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class LoginRequestHandler extends RequestHandler {

    public LoginRequestHandler() {
    }

    public LoginRequestHandler(Request request, SelectionKey key) {
        super(request, key);
    }

    @Override
    public void handle() {
        SocketChannel client = (SocketChannel) key.channel();
        String username = request.getHeader().getFrom();
        String password = request.getMessage();
        if (UserManager.checkLogin(username, password)) {
            // 登录成功
            onlineUsers.incrementAndGet();
            new SystemMsgHandler(key).single("", ResponseType.LOGIN_SUCCESS);
            try {
                UserManager.login(username, key);
                // 广播登录消息
                String message = "\"" + username + "\" has logged in!";
                new SystemMsgHandler(key).broadcast(message);
            } catch (RepeatLoginException e) {
                String msg = e.getMessage();
                new SystemMsgHandler(key).single(msg, ResponseType.LOGIN_FAIL);
            }
        } else {
            new SystemMsgHandler(key).single("Your username or password may be wrong.", ResponseType.LOGIN_FAIL);
        }
    }

}
