package com.chatfest.client.handler;

import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseType;

public class ResponseHandlerFactory {

    public static ResponseHandler getResponseHandler(Response response) {
        byte type = response.getHeader().getType();
        if (type == ResponseType.LOGIN_SUCCESS.getCode() || type == ResponseType.LOGIN_FAIL.getCode()) {
            return new LoginRespHandler(response);
        } else if (type == ResponseType.LOGOUT_SUCCESS.getCode() || type == ResponseType.LOGOUT_FAIL.getCode()) {
            return new LogoutRespHandler(response);
        } else {
            return new RecvMsgHandler(response);
        }
    }
}
