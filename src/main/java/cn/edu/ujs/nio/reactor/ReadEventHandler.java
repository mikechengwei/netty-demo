package cn.edu.ujs.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by chengwei on 7/1/17.
 */
public class ReadEventHandler implements EventHandler {
    private Selector demultiplexer;
    private ByteBuffer buffer = ByteBuffer.allocate(2048);
    private SelectionKey selectionKey;

    public ReadEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }


    public void setKey(SelectionKey sk) {
        this.selectionKey = sk;
    }


    @Override
    public void run() {
        try {
            System.out.println("read handler start");
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

            socketChannel.read(buffer);

            buffer.flip();
            byte[] readBuffer = new byte[buffer.limit()];
            buffer.get(readBuffer);
            System.out.println("Received message from client : " +
                    new String(readBuffer));
            buffer.flip();
            // Rewind the buffer to start reading from the beginning
            // Register the interest for writable readiness event for
            // this channel in order to echo back the message

            SelectionKey sk = socketChannel.register(
                    demultiplexer, SelectionKey.OP_WRITE, buffer);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
