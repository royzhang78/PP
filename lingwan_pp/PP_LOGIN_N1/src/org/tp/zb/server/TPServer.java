/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.tp.zb.server;

import java.io.InputStream;
import java.util.Properties;

import org.tp.zb.server.handler.TPServerInitializer;

import com.tp.comm.Constant;
import com.tp.service.impl.LogServiceQueueImpl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
 
/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class TPServer {

    static  boolean SSL = System.getProperty("ssl") != null;
    static  int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));
    
    static String host = "0.0.0.0";
    
	public static void loadMainParm() throws Exception {
		 
		Constant.APP_ROOT_PATH = TPServer.class.getProtectionDomain()
				.getCodeSource().getLocation().getFile();
		if (Constant.APP_ROOT_PATH.startsWith("/"))
			Constant.APP_ROOT_PATH = "/" + Constant.APP_ROOT_PATH;
		
		Properties p=new Properties();
		InputStream is;
		is = new java.io.FileInputStream(Constant.APP_ROOT_PATH+"/propConfig/server.properties");
		p.load(is);
		SSL= Boolean.valueOf(p.getProperty("isSSL"));
		PORT = Integer.valueOf(p.getProperty("port"));
		host = p.getProperty("host");
		
	}
	

    public static void main(String[] args) throws Exception {
    	loadMainParm();
        // Configure SSL.
        final SslContext sslCtx;
//        SSL=true;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }
        
        
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
     // 获取cpu线程数，这里是demo，实际把这个放在方法外面
     		int processorsBasedThreadCount = Runtime.getRuntime()
     				.availableProcessors();
     		
//     		int memorycount = (int) (Runtime.getRuntime().freeMemory()/1024/1024/4);//可使用内存开辟1/3内存启动线程
//     		System.out.println(memorycount);
//     		System.out.println(processorsBasedThreadCount);
//     		System.out.println(processorsBasedThreadCount*2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        
        try {
            ServerBootstrap b = new ServerBootstrap();
            
            b.option(ChannelOption.SO_BACKLOG, 100)//服务端处理线程全忙后，允许多少个新请求进入等待
              .option(ChannelOption.SO_TIMEOUT, 1000*30)  //45秒超时
              .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000*15)  //15秒 连接超时
            
             .childOption(ChannelOption.SO_KEEPALIVE, true)//是否启用心跳机制
             .childOption(ChannelOption.SO_REUSEADDR, true)
             .option(ChannelOption.TCP_NODELAY, true)//设置封包 使用一次大数据的写操作，而不是多次小数据的写操作
             .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))// 接收缓冲器
             .childOption(ChannelOption.SO_RCVBUF, 1024*32)
             .childOption(ChannelOption.SO_SNDBUF, 1024*32)
             .childOption(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
            ;
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new TPServerInitializer(sslCtx))
             ;
//            new LogServiceQueueImpl();
//            for (int i = 0; i < 5; i++) {
//				new LogServiceQueueImpl().start();
//			}
            
            Channel ch = b.bind("0.0.0.0",PORT).sync().channel();
            
            

            System.err.println("Open your web browser and navigate to " +
                    (SSL? "https" : "http") + "://127.0.0.1:" + PORT + '/');

            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
