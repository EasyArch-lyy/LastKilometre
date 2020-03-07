package netty;

import dao.redis.Redis;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame/*要处理的数据类型*/> {

   /**
    * 加入webSocket连接的客户端channel线程集合
    */
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

   /**
    * @param ctx
    * @param msg
    * 接受客户端发来的消息,向客户端回消息,连接建立阻塞调用,等待消息
    * Pattern.compile(".*[@]+.*").matcher(message)表示字符串message中包含是否@,返回boolean
    *
    */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        String message=msg.text();
        Matcher m0 = Pattern.compile(".*[@]+.*").matcher(message);
        Matcher m1 = Pattern.compile(".*[#id:]+.*").matcher(message);
        if(m0.matches()){
            //私聊消息
            String[]s0=message.split("@");
            System.out.println("本条为私聊消息，发给"+s0[0]);

        }else if(m1.matches()){
            //注册私聊匹配
            String[] s1 = message.split("#id:");
            String id = s1[1];
            Redis.jediss.setChannel(id, String.valueOf(ctx.channel()));
        }else {
            //url请求消息

        }
        //String url=handshaker.uri().split("/")[3];
        //		System.out.println(url);
        //		String request = ((TextWebSocketFrame) frame).text();
        //
        //		System.out.println("服务端收到：" + request);
        //		System.out.println(ctx.handler());
        //		Class c= null;
        //		try {
        //			c = Class.forName("controller."+url);
        //		} catch (ClassNotFoundException e) {
        //			e.printStackTrace();
        //		}
        //		Object object= null;
        //		try {
        //			object = c.newInstance();
        //		} catch (InstantiationException e) {
        //			e.printStackTrace();
        //		} catch (IllegalAccessException e) {
        //			e.printStackTrace();
        //		}
        //		Method method= null;
        //		try {
        //			method = c.getMethod("Return", String.class);
        //		} catch (NoSuchMethodException e) {
        //			e.printStackTrace();
        //		}
        //		String json= null;
        //		try {
        //			json = (String) method.invoke(object,request);
        //		} catch (IllegalAccessException e) {
        //			e.printStackTrace();
        //		} catch (InvocationTargetException e) {
        //			e.printStackTrace();
        //		}
        //		// 返回应答消息
        //
        //		TextWebSocketFrame tws = new TextWebSocketFrame(json);
        Channel channel=ctx.channel();
        System.out.println(msg.text());
        channelGroup.forEach(ch->{
            //当前遍历的不是发消息的channel
            if (channel!=ch){
                ch.writeAndFlush(new TextWebSocketFrame(channel.remoteAddress()+"发送的消息"+msg+"\n"));
            }else {
                ch.writeAndFlush(new TextWebSocketFrame("[自己]"+msg+"\n"));
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
//        ctx.writeAndFlush(new TextWebSocketFrame(channel.id()+"加入连接"));
        channelGroup.writeAndFlush(new TextWebSocketFrame(channel.id()+"加入连接"));
        channelGroup.add(channel);
        System.out.println("当前在线人数:"+channelGroup.size()+"人");
//        System.out.println("handlerAdded:"+channel.id().asLongText());;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        channelGroup.writeAndFlush(new TextWebSocketFrame(channel.id()+"断开连接"));
        System.out.println("handlerRemoved:"+channel.id().asLongText());
    }

    //用户事件触发
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event= (IdleStateEvent) evt;
            String eventType=null;
            switch (event.state()){
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
            }
            System.out.println(ctx.channel().remoteAddress()+"超时事件"+eventType);
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
}
