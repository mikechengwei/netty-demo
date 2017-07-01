package cn.edu.ujs.nio.reactor;

import java.nio.channels.SelectionKey;

/**
 * Created by chengwei on 7/1/17.
 */
interface EventHandler extends Runnable {
    void setKey(SelectionKey sk);
}
