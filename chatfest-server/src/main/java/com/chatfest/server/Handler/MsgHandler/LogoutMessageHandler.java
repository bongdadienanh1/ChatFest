package com.chatfest.server.Handler.MsgHandler;

import com.chatfest.common.transport.Message;

import java.nio.channels.SelectionKey;

public class LogoutMessageHandler extends MessageHandler {

    public LogoutMessageHandler() {
    }

    public LogoutMessageHandler(Message message, SelectionKey key) {
        super(message, key);
    }

    @Override
    public void handle() {

    }
}
