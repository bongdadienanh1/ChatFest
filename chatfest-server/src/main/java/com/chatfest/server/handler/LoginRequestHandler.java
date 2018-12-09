package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.ResponseType;
import com.chatfest.server.Exceptions.RepeatLoginException;
import com.chatfest.server.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class LoginRequestHandler extends RequestHandler {

    private static Logger logger = LoggerFactory.getLogger(LoginRequestHandler.class);

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
            try {
                UserManager.login(username, key);
                // 登录成功
                UserManager.onlineUserNum.incrementAndGet();
                String message = "You logged in! (Online:" + UserManager.onlineUserNum + ")";
                new SystemMsgHandler(key).single(message, ResponseType.LOGIN_SUCCESS.getCode());
                // 广播登录消息
                message = "[" + username + "] logged in! (Online:" + UserManager.onlineUserNum + ")";
                new SystemMsgHandler(key).broadcast(message, ResponseType.SYSTEM_PROMPT.getCode(), key);
            } catch (RepeatLoginException e) {
                String msg = e.getMessage();
                new SystemMsgHandler(key).single(msg, ResponseType.LOGIN_FAIL.getCode());
            }
        } else {
            new SystemMsgHandler(key).single("Your username or password may be wrong.", ResponseType.LOGIN_FAIL.getCode());
        }
    }

}
