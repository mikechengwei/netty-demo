package cn.edu.ujs.nio.reactor;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chengwei on 6/30/17.
 */
public class Reactor implements Runnable {

    private static Map<Integer, EventHandler> handlers = new ConcurrentHashMap<>();
    private Selector demultiplexer;

    public Reactor() throws Exception {
        this.demultiplexer = Selector.open();
    }

    public void registerEventHandler(
            int eventType, EventHandler eventHandler) {
        handlers.put(eventType, eventHandler);
    }

    public void registerChannel(
            int eventType, SelectableChannel channel) throws Exception {
        channel.register(demultiplexer, eventType);
    }

    public Selector getDemultiplexer() {
        return demultiplexer;
    }

    @Override
    public void run() {
        System.out.println("reactor 启动");
        while (true) {
            try {
                demultiplexer.select();
                Set<SelectionKey> readyHandles =
                        demultiplexer.selectedKeys();
                Iterator<SelectionKey> iterator = readyHandles.iterator();

                while (iterator.hasNext()) {
                    SelectionKey handle = iterator.next();
                    if (handle.isAcceptable()) {
                        System.out.println("accept");
                        EventHandler handler =
                                handlers.get(SelectionKey.OP_ACCEPT);
                        handler.setKey(handle);
                        handler.run();
                        iterator.remove();

                    }

                    if (handle.isReadable()) {
                        EventHandler handler =
                                handlers.get(SelectionKey.OP_READ);
                        handler.setKey(handle);
                        handler.run();
                        iterator.remove();
                    }

                    if (handle.isWritable()) {
                        EventHandler handler =
                                handlers.get(SelectionKey.OP_WRITE);
                        handler.setKey(handle);
                        handler.run();
                        iterator.remove();
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


}