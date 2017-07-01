package cn.edu.ujs.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by chengwei on 6/30/17.
 */
public class Handler implements Runnable {
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;
    String clientName = "";

    Handler(Selector selector, SocketChannel c) throws IOException {
        socketChannel = c;
        c.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    public void run() {
        if (state == READING) {
            read();
        } else if (state == SENDING) {
            send();
        }
    }

    private void read() {
        System.out.println("reading ...");
    }

    private void send() {
        System.out.println("sending...");
    }
}