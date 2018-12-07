package com.chatfest.client.generator;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

public class LogoutReqGenerator extends ReqGenerator {

    public LogoutReqGenerator(String message, String to) {
        super(message, to);
    }

    @Override
    public Request generate() {
        request.getHeader().type(RequestType.LOGOUT.getCode());
        return request;
    }
}
