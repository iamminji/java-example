package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class RedisServer {

    private static final int PORT = Integer.parseInt(System.getProperty("port", "6379"));

    public static void main(String[] args) throws Exception {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        final CountDownLatch shutdownLatch = new CountDownLatch(1);
        final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        try {
            final ServerBootstrap b = new ServerBootstrap();
            b.channel(NioServerSocketChannel.class);
            b.group(bossGroup, workerGroup);

            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    final ChannelPipeline p = ch.pipeline();
                    p.addLast(new RedisDecoder());
                    p.addLast(new RedisBulkStringAggregator());
                    p.addLast(new RedisArrayAggregator());
                    p.addLast(new RedisEncoder());
                    // map 은 바깥에서 만들어준다.
                    // 안에서 만들면 커넥션마다 map이 생성된다.
                    // map 공유와 GC를 위해서 밖에다가 만들어준다.
                    p.addLast(new RedisServerHandler(map, shutdownLatch));
                }
            });

            // netty 는 기본이 비동기
            final Channel ch = b.bind(PORT).sync().channel();
            System.out.println("An example Redis server now listening at .." + ch.localAddress());
            shutdownLatch.await();
            System.err.println("Received a SHUTDOWN command; shutting down...");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
