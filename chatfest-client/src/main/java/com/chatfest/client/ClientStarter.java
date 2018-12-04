package com.chatfest.client;

import com.chatfest.common.transport.Request;
import com.chatfest.common.types.RequestType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientStarter {
    private static SocketChannel client;

    private ClientStarter(String host, int port) {
        init(host, port);
    }

    private void init(String host, int port) {
        try {
            client = SocketChannel.open();
            client.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() {

        // IDE的命令行会出现空指针异常
//        Console console = System.console();
//        String username = console.readLine();
//        String password = new String(console.readPassword());
//        System.out.println(username + " " + password);

        Scanner in = new Scanner(System.in);
        System.out.print("username:");
        String username = in.nextLine();
        System.out.print("password:");
        String password = in.nextLine();
        Request request = Request.build()
                .from(username)
                .to("SYSTEM")
                .type(RequestType.LOGIN)
                .body(password.getBytes());
        new RequestSender(request, client);
    }

    private void launch() {
        login();

    }

    public static void main(String[] args) {
        ClientStarter client;
        if (args.length != 2) {
            System.out.println("Usage: java ClientStarter <host> <port>");
            return;
        } else {
            client = new ClientStarter(args[0], Integer.parseInt(args[1]));
        }
    }
}
