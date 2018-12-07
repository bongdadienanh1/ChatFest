package com.chatfest.client.generator;

import com.chatfest.client.ClientStarter;
import com.chatfest.common.transport.Request;
import com.chatfest.common.transport.RequestHeader;

import java.util.Date;

public abstract class ReqGenerator {

    Request request;

    public ReqGenerator(String message, String to) {
        RequestHeader header = RequestHeader.build()
                .from(ClientStarter.username)
                .to(to)
                .timestamp(new Date().getTime())
                .contentLength(message.length());
        request = Request.build()
                .requestHeader(header)
                .message(message);
    }

    public abstract Request generate();
}
