/*
 * Copyright 2013 The Netty Project
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
package org.tp.zb.server.handler;

import javax.servlet.ServletException;

import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.tp.comm.Constant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;

public class TPServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    
    
    private final DispatcherServlet dispatcherServlet;

    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    public TPServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
        
        MockServletContext servletContext = new MockServletContext();
    	MockServletConfig servletConfig = new MockServletConfig(servletContext);
    	
//    	Dynamic d =servletContext.addFilter("characterEncodingFilter", new org.springframework.web.filter.CharacterEncodingFilter());
//    	Map smap=new HashMap();
//    	smap.put("encoding", "UTF-8");
//    	smap.put("forceEncoding", "true");
//    	d.setInitParameters(smap);
//    	d.addMappingForUrlPatterns(null,true, "/*");
    	
    	
    	 
    	
    	
//        servletConfig.addInitParameter("contextConfigLocation","classpath:/root-context.xml");
//        servletContext.addInitParameter("contextConfigLocation","classpath:/root-context.xml");

    	//AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
        XmlWebApplicationContext wac = new XmlWebApplicationContext();

        //ClassPathXmlApplicationContext wac = new ClassPathXmlApplicationContext();
		wac.setServletContext(servletContext);
		wac.setServletConfig(servletConfig);
        wac.setConfigLocation("classpath:/spring/spring.xml");
        
        
       
    	//wac.register(WebConfig.class);
    	wac.refresh();
    	
    	Constant.CTX	 =   wac;//.get.getWebApplicationContext(wac);

    	this.dispatcherServlet = new DispatcherServlet(wac);
    	try {
//    		 wac.getServletContext().addServlet("DruidStatView", com.alibaba.druid.support.http.StatViewServlet.class).addMapping("/druid/*");
    	    	
			this.dispatcherServlet.init(servletConfig);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
       
//        p.addLast(new HttpDemoServerHandler());
        
        
//        p.addLast("decoder", new HttpRequestDecoder()); 
//        p.addLast("frameDecoder", new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
//        p.addLast(new HttpServerCodec());
//        p.addLast( new HttpPostStandardRequestDecoder()); 
        
        
        p.addLast("decoder", new HttpRequestDecoder()); 
        p.addLast("aggregator", new HttpObjectAggregator(1048576)); 
		p.addLast("encoder", new HttpResponseEncoder()); 
		p.addLast("chunkedWriter", new ChunkedWriteHandler()); 

		
        p.addLast(new SpringServletNettyHandler(this.dispatcherServlet)); 
//        try {
//			p.addLast(new DispatcherServletChannelInitializer());
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
}
