package com.chatfest.server.Handler.MsgHandler;

import com.chatfest.common.transport.Message;
import com.chatfest.common.types.MsgType;

import java.nio.channels.SelectionKey;

public class MessageHandlerFactory {

    public static MessageHandler getMessageHandler(Message message, SelectionKey key) {
        MsgType type = message.getType();
        switch (type.getCode()) {
            case 1:
                return new LoginMessageHandler(message, key);
            case 2:
                return new LogoutMessageHandler(message, key);
            case 3:
                return new SingleMessageHandler(message, key);
            case 4:
                return new MultipleMessageHandler(message, key);
            default:
                return null;
        }
    }
}
