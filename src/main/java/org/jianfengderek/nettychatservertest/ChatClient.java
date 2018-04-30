package org.jianfengderek.nettychatservertest;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class ChatClient {

    private final String host;

    private final int port;

    private ChatClientHandler chatClientHandler = new ChatClientHandler();

    public static void main(String[] args) throws Exception {
        ChatClient chatClient = new ChatClient("localhost", 8080);
        chatClient.setOnMessageReceivedListener(new OnMessageReceivedListener() {

            @Override
            public void onMessageReceived(String message) {
                System.out.println("Message received: \"" + message + "\".");
            }

        });
        chatClient.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer() {

                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(chatClientHandler);
                        }

                    });
            Channel channel = b.connect().sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(Unpooled.copiedBuffer(in.readLine(), CharsetUtil.UTF_8));
            }
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public void setOnMessageReceivedListener(OnMessageReceivedListener onMessageReceivedListener) {
        chatClientHandler.setOnMessageReceivedListener(onMessageReceivedListener);
    }

}
