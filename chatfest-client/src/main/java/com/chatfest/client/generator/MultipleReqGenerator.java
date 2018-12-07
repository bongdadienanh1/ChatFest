package com.chatfest.client.generator;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

public class MultipleReqGenerator extends ReqGenerator {

    public MultipleReqGenerator(String message, String to) {
        super(message, to);
    }

    @Override
    public Request generate() {
        request.getHeader().type(RequestType.MULTIPLE.getCode());
        return request;
    }
}
