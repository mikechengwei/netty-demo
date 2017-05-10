package cn.edu.ujs.netty.websocket.service;


import cn.edu.ujs.netty.websocket.dto.Response;
import cn.edu.ujs.netty.websocket.entity.Client;

public class MessageService {

    public static Response sendMessage(Client client, String message) {
        Response res = new Response();
        res.getData().put("id", client.getId());
        res.getData().put("message", message);
        res.getData().put("ts", System.currentTimeMillis());// 返回毫秒数
        return res;
    }
}
