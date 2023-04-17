package org.example;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.IntegerRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import io.netty.util.ReferenceCountUtil;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

// 받은 메시지를 처리
public class RedisServerHandler extends ChannelInboundHandlerAdapter {

    private final ConcurrentHashMap<String, String> map;
    private final CountDownLatch shutdownLatch;

    public RedisServerHandler(ConcurrentHashMap<String, String> map, CountDownLatch shutdownLatch) {
        this.map = map;
        this.shutdownLatch = shutdownLatch;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof ArrayRedisMessage)) {
            reject(ctx, "ERR client request bust be an array of bulk strings.");
            return;
        }
        final ArrayRedisMessage req = (ArrayRedisMessage) msg;
        final List<RedisMessage> args = req.children();
        for (RedisMessage a : args) {
            if (!(a instanceof FullBulkStringRedisMessage)) {
                reject(ctx, "ERR Request error");
                return;
            }
        }

        final List<String> strArgs =
            args.stream().map(a -> {
                final FullBulkStringRedisMessage bulkStr = (FullBulkStringRedisMessage) a;
                if (bulkStr.isNull()) {
                    return null;
                }
                return bulkStr.content().toString(StandardCharsets.UTF_8);
            }).collect(Collectors.toList());

        System.err.println(ctx.channel() + "RCVD: " + strArgs);
        String command = strArgs.get(0);
        switch (command) {
            case "COMMAND":
                ctx.writeAndFlush(ArrayRedisMessage.EMPTY_INSTANCE);
                break;
            case "GET": {
                if (strArgs.size() < 2) {
                    reject(ctx, "ERR A GET command requires a key argument.");
                    return;
                }
                final String key = strArgs.get(1);
                if (key == null) {
                    reject(ctx, "ERR A nil key is not allowed.");
                    return;
                }
                final String value = map.get(key);
                final FullBulkStringRedisMessage reply;
                if (value != null) {
                    reply = new FullBulkStringRedisMessage(
                        Unpooled.copiedBuffer(value, StandardCharsets.UTF_8));
                } else {
                    reply = FullBulkStringRedisMessage.NULL_INSTANCE;
                }
                ctx.writeAndFlush(reply);
                break;
            }
            case "SET": {
                if (strArgs.size() < 3) {
                    reject(ctx, "ERR A Get command requires a key argument.");
                    return;
                }
                final String key = strArgs.get(1);

                if (key == null) {
                    reject(ctx, "ERR A nil key is not allowed.");
                    return;
                }

                final String value = strArgs.get(2);
                if (value == null) {
                    reject(ctx, "ERR A nil value is not allowed.");
                    return;
                }

                final boolean shouldReplyOldValue = strArgs.size() > 3 &&
                    "GET".equals(strArgs.get(3));

                final String oldValue = map.put(key, value);
                final RedisMessage reply;
                if (shouldReplyOldValue) {
                    if (oldValue != null) {
                        reply = new FullBulkStringRedisMessage(
                            Unpooled.copiedBuffer(oldValue, StandardCharsets.UTF_8));
                    } else {
                        reply = FullBulkStringRedisMessage.NULL_INSTANCE;
                    }
                } else {
                    reply = new SimpleStringRedisMessage("OK");
                }
                ctx.writeAndFlush(reply);
                break;
            }
            case "DEL": {
                if (strArgs.size() < 2) {
                    reject(ctx, "ERR A DEL command requires at least one key argument.");
                    return;
                }
                int removedEntries = 0;
                for (int i = 1; i < strArgs.size(); i++) {
                    final String key = strArgs.get(i);
                    if (key == null) {
                        continue;
                    }
                    if (map.remove(key) != null) {
                        removedEntries++;
                    }
                }
                ctx.writeAndFlush(new IntegerRedisMessage(removedEntries));
                break;
            }
            case "SHUTDOWN":
                ctx.writeAndFlush(new SimpleStringRedisMessage("OK"))
                    .addListener(((ChannelFutureListener) f -> {
                        f.channel().close();
                        shutdownLatch.countDown();
                    }));
                break;
            default:
                reject(ctx, "ERR Unsupported command");
        }
    }

    private void reject(ChannelHandlerContext ctx, String error) {
        ctx.writeAndFlush(new ErrorRedisMessage(error));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("Unexpected exception handling " + ctx.channel());
        cause.printStackTrace(System.err);
        ctx.close();
    }
}
