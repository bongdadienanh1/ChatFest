package com.chatfest.client.generator;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

public class LoginReqGenerator extends ReqGenerator {

    public LoginReqGenerator(String message, String to) {
        super(message, to);
    }

    @Override
    public Request generate() {
        request.getHeader().type(RequestType.LOGIN.getCode());
        return request;
    }
}
