package com.chatfest.client.util;

public class MessagePrinter {
    public static void print(String message, String from) {
        if (from.equals("SYSTEM")) {
            System.out.println("\\033[34m[" + from + "]:" + message);
        } else {
            System.out.println("[" + from + "]:" + message);
        }
    }
}
