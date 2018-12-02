package com.chatfest.server.handler;

import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseStatus;
import com.chatfest.common.util.codec.FastJsonCodec;
import com.chatfest.server.manager.UserManager;

import java.io.IOException;
import java.nio.ByteBuffer;
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
            // 在线人数加一
            onlineUsers.incrementAndGet();

            Response response = Response.build()
                    .responseStatus(ResponseStatus.LOGIN_SUCCESS)
                    .body(null)
                    .from("SYSTEM");
            byte[] bytes = FastJsonCodec.serialize(response);
            try {
                client.write(ByteBuffer.wrap(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 广播登录消息
            String message = "\"" + username + "\" has logged in!";
            new SystemMsgHandler(key.selector()).broadcast(message);
        } else {
            Response response = Response.build()
                    .responseStatus(ResponseStatus.LOGIN_FAIL)
                    .body(null);
            byte[] bytes = FastJsonCodec.serialize(response);
            try {
                client.write(ByteBuffer.wrap(bytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}