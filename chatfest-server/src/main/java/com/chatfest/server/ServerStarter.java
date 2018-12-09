package com.chatfest.server;

import com.chatfest.server.handler.AcceptHandler;
import com.chatfest.server.handler.ReadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerStarter {
    private static Logger logger = LoggerFactory.getLogger(ServerStarter.class);
    // 服务器默认端口
    public static final int DEFAULT_PORT = 10010;
    // 服务器监听套接字
    private ServerSocketChannel serverSocketChannel;
    // 选择器
    private Selector selector;
    // 接收请求线程
    private Thread requestRecvThread;
    // 处理用户消息线程池
    private ExecutorService handleMsgPool;

    public ServerStarter(int port) {
        init(port);
    }

    private void init(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            requestRecvThread = new Thread(new RequestRecvThread());
            handleMsgPool = Executors.newFixedThreadPool(10);
            logger.info("Server is running on {}", serverSocketChannel.getLocalAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启服务器
     */
    private void launch() {
        requestRecvThread.start();
    }

    /**
     * 关闭服务器
     */
    private void shutdown() {
        requestRecvThread.interrupt();
    }

    private class RequestRecvThread implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (selector.select(1000) == 0) {
                        continue;
                    }
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        // 必须删除已处理的key
                        it.remove();
                        if (key.isAcceptable()) {
                            // 处理ACCEPT,新建客户端通道并注册到selector上
                            SocketChannel client = new AcceptHandler(key).handle();
                            if (client != null) {
                                logger.info("connect from {}", client);
                            }
                        } else if (key.isReadable()) {
                            // 处理READ
                            // 因为是新开线程处理READ，所以要将此key的READ标记去掉，在线程结束时重新加上（避免重复处理）
                            key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                            handleMsgPool.execute(new ReadHandler(key));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerStarter server;
        if (args.length == 0) {
            server = new ServerStarter(DEFAULT_PORT);
        } else {
            server = new ServerStarter(Integer.parseInt(args[0]));
        }
        server.launch();
        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (next.equalsIgnoreCase("q")) {
                server.shutdown();
                logger.info("server is shutdown now");
            }
        }
    }
}
