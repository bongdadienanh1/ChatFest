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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStarter {
    private static Logger logger = LoggerFactory.getLogger(ServerStarter.class);
    private static final int PORT = 10010;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private Thread listenThread;
    private ExecutorService handleReadPool;

    public ServerStarter() {
        logger.info("server is starting...");
        init();
    }

    private void init() {
        try {
            // 服务器通道
            serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(PORT));
            // 非阻塞模式
            serverSocketChannel.configureBlocking(false);
            // 选择器
            selector = Selector.open();
            // 注册到选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // listen线程
            listenThread = new Thread(new ListenThread());
            // read线程池
            handleReadPool = Executors.newFixedThreadPool(10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启服务器
     */
    private void lunch() {
        listenThread.start();
    }

    /**
     * 关闭服务器
     */
    private void shundown() {

        listenThread.interrupt();
    }

    private class ListenThread implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // 每次阻塞1000ms
                    if (selector.select(1000) == 0) {
                        System.out.print(".");
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
                            handleReadPool.execute(new ReadHandler(key));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}