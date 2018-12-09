package com.chatfest.client.handler;

import com.chatfest.client.ClientStarter;
import com.chatfest.client.util.MessagePrinter;
import com.chatfest.common.transport.Response;
import com.chatfest.common.types.ResponseType;

public class LogoutRespHandler extends ResponseHandler {
    public LogoutRespHandler(Response response) {
        super(response);
    }

    @Override
    public void handle() {
        if (response.getHeader().getType() == ResponseType.LOGOUT_SUCCESS.getCode()) {
            MessagePrinter.print(response.getMessage(), response.getHeader().getFrom());
            ClientStarter.disconnect();
        }
        if (response.getHeader().getType() == ResponseType.LOGOUT_FAIL.getCode()) {
            MessagePrinter.print(response.getMessage(), response.getHeader().getFrom());
        }
    }
}
