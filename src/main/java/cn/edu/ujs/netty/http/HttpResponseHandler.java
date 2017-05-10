package cn.edu.ujs.netty.http;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * Created by chengwei on 5/10/17.
 */
public class HttpResponseHandler extends SimpleChannelInboundHandler<Object> {

    //    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
//        final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK);
//        response.headers().set("Content-Type", "Some value");
//        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//    }
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object o) throws Exception {
        final FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK);
        response.headers().set("Content-Type", "Some value");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
