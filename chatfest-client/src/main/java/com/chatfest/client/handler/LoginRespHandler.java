package com.chatfest.client.handler;

import com.chatfest.client.ClientStarter;
import com.chatfest.client.util.MessagePrinter;
import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseType;

public class LoginRespHandler extends ResponseHandler {

    public LoginRespHandler(Response response) {
        super(response);
    }

    @Override
    public void handle() {
        if (response.getHeader().getType() == ResponseType.LOGIN_SUCCESS.getCode()) {
            MessagePrinter.print(response.getMessage(), response.getHeader().getFrom());
        }
        if (response.getHeader().getType() == ResponseType.LOGIN_FAIL.getCode()) {
            MessagePrinter.print(response.getMessage(), response.getHeader().getFrom());
            ClientStarter.disconnect();
            ClientStarter.login();
        }
    }
}
