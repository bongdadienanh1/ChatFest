package com.chatfest.client.generator;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

public class SingleReqGenerator extends ReqGenerator {

    public SingleReqGenerator(String message, String to) {
        super(message, to);
    }

    @Override
    public Request generate() {
        request.getHeader().type(RequestType.SINGLE.getCode());
        return request;
    }
}
