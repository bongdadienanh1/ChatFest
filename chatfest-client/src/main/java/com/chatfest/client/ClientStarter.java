package com.chatfest.client;

import com.chatfest.client.generator.LoginReqGenerator;
import com.chatfest.client.generator.LogoutReqGenerator;
import com.chatfest.client.generator.MultipleReqGenerator;
import com.chatfest.client.generator.SingleReqGenerator;
import com.chatfest.client.handler.ReadHandler;
import com.chatfest.common.transport.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientStarter {

    // 服务器地址
    private static String remoteHost;
    // 服务器端口
    private static int remotePort;
    // 客户端套接字
    private static SocketChannel client;
    // 选择器
    private static Selector selector;
    // 此客户端用户名
    public static String username;
    // 读取用户输入线程
    private static Thread readInputThread;
    // 接收服务器响应线程
    private static Thread responseRecvThread;
    // 发送请求线程池
    private static ExecutorService handleReqPool;
    // 接收响应线程池
    private static ExecutorService handleRespPool;

    private static void init() {
        readInputThread = new Thread(new ReadInputThread());
        responseRecvThread = new Thread(new ResponseRecvThread());
        handleReqPool = Executors.newFixedThreadPool(5);
        handleRespPool = Executors.newFixedThreadPool(5);
    }

    private static class ReadInputThread implements Runnable {
        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (!Thread.currentThread().isInterrupted()) {
                String input;
                try {
                    input = br.readLine();
                    if (client == null || !client.isConnected()) {
                        if (input.equals("#login")) {
                            login();
                        } else {
                            System.out.println("Please login first!");
                        }
                    } else {
                        if (input.equals("#login")) {
                            System.out.println("You had logged in!");
                            continue;
                        }
                        if (input.equals("")) {
                            System.out.println("\u001b[31mMessage can't be empty!");
                        } else if (input.charAt(0) == '@') {
                            String[] strs = input.split(":");
                            if (strs[0].length() == 1) {
                                System.out.println("\u001b[31mUsername can't be empty!");
                            }
                            if (strs.length == 1) {
                                System.out.println("\u001b[31mMessage can't be empty!");
                                continue;
                            }
                            String to = strs[0].substring(1);
                            String message = strs[1];
                            Request request = new SingleReqGenerator(message, to).generate();
                            handleReqPool.execute(new RequestSender(request, client));
                        } else if (input.equals("#logout")) {
                            logout();
                        } else if (input.equals("#quit")) {
                            Thread.currentThread().interrupt();
                            shutdown();
                        } else {
                            Request request = new MultipleReqGenerator(input, "SYSTEM").generate();
                            handleReqPool.execute(new RequestSender(request, client));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ResponseRecvThread implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (selector.select(3000) == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if (key.isReadable()) {
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                            handleRespPool.execute(new ReadHandler(key));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void launch() {
        init();
        System.out.println("WELCOME TO CHATFEST!");
        readInputThread.start();
    }

    public static void login() {

        Scanner in = new Scanner(System.in);
        System.out.print("username:");
        username = in.nextLine();
        System.out.print("password:");
        String password = in.nextLine();
        // 建立客户端与服务器的连接
        try {
            client = SocketChannel.open();
            client.connect(new InetSocketAddress(remoteHost, remotePort));
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_READ);
            // 开始接收响应
            responseRecvThread = new Thread(new ResponseRecvThread());
            responseRecvThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Request request = new LoginReqGenerator(password, "SYSTEM").generate();
        handleReqPool.execute(new RequestSender(request, client));
        System.out.println("logging in...");
    }

    private static void logout() {
        String message = "LOGOUT";
        Request request = new LogoutReqGenerator(message, "SYSTEM").generate();
        handleReqPool.execute(new RequestSender(request, client));
    }

    public static void disconnect() {
        try {
            responseRecvThread.interrupt();
            client.close();
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        disconnect();
        handleReqPool.shutdown();
        handleRespPool.shutdown();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ClientStarter <host> <port>");
        } else {
            remoteHost = args[0];
            remotePort = Integer.parseInt(args[1]);
            launch();
        }
    }
}
