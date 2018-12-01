package com.chatfest.server.Handler.MsgHandler;

import com.chatfest.common.domain.User;
import com.chatfest.common.transport.Message;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class LoginMessageHandler extends MessageHandler {

    public LoginMessageHandler() {
    }

    public LoginMessageHandler(Message message, SelectionKey key) {
        super(message, key);
    }

    @Override
    public void handle() {
        SocketChannel client = (SocketChannel) key.channel();
        User from = message.getFrom();
        User to = message.getTo();
    }

}
