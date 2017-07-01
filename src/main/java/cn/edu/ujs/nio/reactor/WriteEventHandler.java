package cn.edu.ujs.nio.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by chengwei on 7/1/17.
 */
public class WriteEventHandler implements EventHandler {

    private Selector demultiplexer;
    private SelectionKey handle;

    public WriteEventHandler(Selector demultiplexer, SelectionKey handle) {
        this.demultiplexer = demultiplexer;
        this.handle = handle;
    }

    public WriteEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    public void setKey(SelectionKey sk) {
        this.handle = sk;
    }


    @Override
    public void run() {
        try {
            System.out.println("===== Write Event Handler =====");

            SocketChannel socketChannel =
                    (SocketChannel) handle.channel();
            //ByteBuffer bb = ByteBuffer.wrap("Hello Client!\n".getBytes());
            ByteBuffer inputBuffer = (ByteBuffer) handle.attachment();
            socketChannel.write(inputBuffer);
            socketChannel.close();
        } catch (Exception e) {
            System.out.println("write error" + e.getMessage());
        }
    }
}
