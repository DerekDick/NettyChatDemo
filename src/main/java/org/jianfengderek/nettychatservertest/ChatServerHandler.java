package org.jianfengderek.nettychatservertest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private List<ChannelHandlerContext> ctxList;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (null == ctxList) {
            ctxList = new ArrayList<>();
        }
        ctxList.add(ctx);

        System.out.println("channelActive(" + ctx.toString() + "): context added.");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctxList.remove(ctx);
        System.out.println("channelInactive(" + ctx.toString() + "): context removed.");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String string = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        System.out.println("Message received: \"" + string + "\".");
        for (ChannelHandlerContext c : ctxList) {
            // TODO: Ugly logic to avoid the exception associated with ReferenceCounted
            ((ByteBuf) msg).retain();

            c.writeAndFlush(msg);

            System.out.println("Message \"" + string + "\" sent to " + c.toString() + ".");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete(" + ctx.toString() + ")");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
