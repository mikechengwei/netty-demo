package cn.edu.ujs.nio.reactor;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by chengwei on 7/1/17.
 */
public class ReactorManager {
    private static final int SERVER_PORT = 7070;

    public void start(int port) throws Exception {

        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        Reactor reactor = new Reactor();
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);
        reactor.registerEventHandler(
                SelectionKey.OP_ACCEPT, new AcceptEventHandler(reactor.getDemultiplexer()));

        reactor.registerEventHandler(
                SelectionKey.OP_READ, new ReadEventHandler(
                        reactor.getDemultiplexer()));

        reactor.registerEventHandler(
                SelectionKey.OP_WRITE, new WriteEventHandler(reactor.getDemultiplexer()));

        reactor.run(); // Run the dispatcher loop


    }

    public static void main(String[] args) {
        System.out.println("Server Started at port : " + SERVER_PORT);
        try {
            new ReactorManager().start(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
