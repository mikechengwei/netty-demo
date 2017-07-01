package cn.edu.ujs.nio.reactor;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by chengwei on 7/1/17.
 */
public class AcceptEventHandler implements EventHandler {

    private Selector demultiplexer;
    private SelectionKey selectionKey;

    public AcceptEventHandler(Selector demultiplexer) {
        this.demultiplexer = demultiplexer;
    }

    public void setKey(SelectionKey sk) {
        this.selectionKey = sk;
    }


    @Override
    public void run() {
        try {
            System.out.println("start handle acceptEvent");
            //建立通道

            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                socketChannel.register(
                        demultiplexer, SelectionKey.OP_READ);

            }
        } catch (Exception e) {
            System.out.println("accept error");
        }
    }
}
