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
    private ByteBuffer readBuffer = ByteBuffer.allocate(2048);
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
            System.out.println("handle readEvent");
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            while (socketChannel.read(readBuffer) != 0) {

            }
            readBuffer.flip();
            byte[] printBuffer = new byte[readBuffer.limit()];
            readBuffer.get(printBuffer);
            System.out.println("Received message from client : " +
                    new String(printBuffer));
            ByteBuffer writebuffer = ByteBuffer.allocate(2048);
            writebuffer.put(printBuffer);


            socketChannel.register(
                    demultiplexer, SelectionKey.OP_WRITE, writebuffer);

        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private boolean isFull(ByteBuffer byteBuffer) {
        return byteBuffer.position() == byteBuffer.capacity() ? true : false;
    }
}
