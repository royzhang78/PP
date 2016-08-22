package org.tp.zb.server.handler;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.util.CharsetUtil;

public class SpringServletNettyHandler extends   ChannelHandlerAdapter {
//	public static int cnt=0;
	
//	public static LoadingCache<String,Object> sessionMap = CacheUtil.init(30, 10, 2000, 100000, null);
	
	public static Logger log = org.apache.log4j.Logger.getLogger(SpringServletNettyHandler.class);
	
	
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	private final Servlet servlet;

	private final ServletContext servletContext;

	public SpringServletNettyHandler(Servlet servlet) {
		this.servlet = servlet;
		this.servletContext = servlet.getServletConfig().getServletContext();
		
	}


    
    @Override
    public void disconnect(ChannelHandlerContext ctx, io.netty.channel.ChannelPromise promise) throws Exception {
//    	System.out.println(1122);
    	super.disconnect(ctx, promise);
    };
    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//    	Map<ChannelOption<?>, Object> o=ctx.channel().config().getOptions();
    	
    	
//    	if (msg instanceof HttpRequest) {
//    		AttributeKey attributeKey = AttributeKey.valueOf("counter"+ctx.channel().id()); 
//    		Attribute.addAttributeReader("", r);
//    		System.out.println(ctx.attr(attributeKey).get());
//    	}
    		 
//    	log.info(msg.getClass());
    	MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
		
    	if (msg instanceof HttpRequest) {
    		HttpRequest request = (HttpRequest) msg;
//    		servletRequest=createServletRequest(request);
//    		this.channelRead2(ctx, msg);
    		
            Iterator<CharSequence> ii= request.headers().names().iterator();
            while(ii.hasNext()){
            	CharSequence c=ii.next();
            	servletRequest.addHeader(c.toString(), request.headers().get(c).toString());
//            	System.out.println(c.toString()+"  "+ request.headers().get(c).toString());
            	log.info(c.toString()+"  "+ request.headers().get(c).toString());
            }
    	}
    	if (msg instanceof HttpContent) {
    		
//    		System.out.println(JSONUtil.objectToString(ctx.channel()));
    		servletRequest.addHeader("x-forwarded-for", ctx.channel().remoteAddress());
    		servletRequest.setRequestURI("/api");
    		servletRequest.setPathInfo("/api");
    		servletRequest.setMethod("POST");

//    		if (uriComponents.getScheme() != null) {
    			servletRequest.setScheme("api");
//    		}
//    		if (uriComponents.getHost() != null) {
//    			servletRequest.setServerName(uriComponents.getHost());
//    		}
//    		if (uriComponents.getPort() != -1) {
//    			servletRequest.setServerPort(uriComponents.getPort());
//    		}

    		
    	    HttpContent httpContent = (HttpContent) msg;
    	    ByteBuf content = httpContent.content();    	    
    	    final StringBuilder buf = new StringBuilder();
    	    buf.append(content.toString(CharsetUtil.UTF_8));
    	    
    	    servletRequest.setContent(buf.toString().getBytes());
    	    
    	    MockHttpServletResponse servletResponse = new MockHttpServletResponse();

			try {
				this.servlet.service(servletRequest, servletResponse);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}

			HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
			FullHttpResponse response=null;
			try {

				ByteBuf buf1 =Unpooled.copiedBuffer(servletResponse.getContentAsString(), CharsetUtil.UTF_8);
				response = new DefaultFullHttpResponse(HTTP_1_1, status, buf1);
				
//				Cookie c=(Cookie) new jodd.http.Cookie("JSESSION", ctx.channel().id().asLongText());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//	            response.headers().set(CONTENT_TYPE, "text/plain");
			 	response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
//	            Content-Type:text/html;charset=UTF-8
	            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
	            
//	            Iterator<CharSequence> ii= request.headers().names().iterator();
//	            while(ii.hasNext()){
//	            	System.out.println(ii.next().toString());
//	            }
	           
	            
	          
	           
	            
	            
	    		
//	            boolean keepAlive = HttpHeaderUtil.isKeepAlive(req);
//	            if (!keepAlive) {
//	                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//	            } else {
//	                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
	                ctx.write(response);
//	            }
    	    
    		
    	}
    	if (msg instanceof HttpRequest) {
//    		this.channelRead2(ctx, msg);
    	}
    	
    }
    
    
//    @Override
    public void channelRead1(ChannelHandlerContext ctx, Object msg) {
//    	System.out.println(ctx.channel().id().asShortText()+"===");
//    	System.out.println(ctx.channel().id().asLongText()+"===");
//    	System.out.println(ctx.channel().id()+"===");
    	
    	if (msg instanceof HttpRequest) {
//    		cnt++;
//    		if(cnt==1){
//    			
//    			try {
//					Thread.sleep(50000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//    		}
    		
//        	new jodd.madvoc.MadvocServletFilter().doFilter(arg0, arg1, arg2);
            
//            if (HttpHeaderUtil.is100ContinueExpected(req)) {
//                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
//            }
            HttpRequest request = (HttpRequest) msg;
            
            
            
            
			if (!request.decoderResult().isSuccess()) {
//				sendError(ctx, BAD_REQUEST);
				return;
			}

			MockHttpServletRequest servletRequest = createServletRequest(request);
			MockHttpServletResponse servletResponse = new MockHttpServletResponse();

			try {
				this.servlet.service(servletRequest, servletResponse);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
			FullHttpResponse response=null;
			try {

				ByteBuf buf =Unpooled.copiedBuffer(servletResponse.getContentAsString(), CharsetUtil.UTF_8);
				response = new DefaultFullHttpResponse(HTTP_1_1, status, buf);
				
//				Cookie c=(Cookie) new jodd.http.Cookie("JSESSION", ctx.channel().id().asLongText());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//	            response.headers().set(CONTENT_TYPE, "text/plain");
			 response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");
//	            Content-Type:text/html;charset=UTF-8
	            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
	            
//	            Iterator<CharSequence> ii= request.headers().names().iterator();
//	            while(ii.hasNext()){
//	            	System.out.println(ii.next().toString());
//	            }
	           
	            
	          
	             
	            int day = 24 * 60 * 60;//1澶�24 * 60 * 60
	    		io.netty.handler.codec.http.Cookie cookie = new io.netty.handler.codec.http.DefaultCookie("JSESSION", ctx.channel().id().asShortText());
	    		cookie.setMaxAge(30);
	    		String cookiestr= io.netty.handler.codec.http.ServerCookieEncoder.encode(cookie);
//	    		response.headers().add( "Cookie",cookiestr);
	    		 response.headers().add( io.netty.handler.codec.http.HttpHeaderNames.SET_COOKIE,cookiestr);
//	    		 request.headers().add( io.netty.handler.codec.http.HttpHeaderNames.COOKIE,cookiestr);
	    		 
	            
	    	 
	            Set<io.netty.handler.codec.http.Cookie> cookies;
	            String value = (String) servletRequest.getHeader("Cookie");
	            if (value == null) {
	                cookies = Collections.emptySet();
	            } else {
	            	cookies= io.netty.handler.codec.http.ServerCookieDecoder.decode(value);
	            }
//	            for (io.netty.handler.codec.http.Cookie cookie1 : cookies) {
//	            	log.info("COOKIE: " + cookie1.toString() + "\r\n");
//	            }
//	            log.info(" ");

	            
	            
	            
	            
	    		
	            boolean keepAlive = HttpHeaderUtil.isKeepAlive(request);
	            if (!keepAlive) {
	                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
	            } else {
	                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
	                ctx.write(response);
	            }
        }
    }
    


    
    public void channelRead2(ChannelHandlerContext ctx, Object msg) {
 
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
 
            if (HttpHeaderUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, io.netty.handler.codec.http.HttpResponseStatus.CONTINUE));
            }
            boolean keepAlive = HttpHeaderUtil.isKeepAlive(req);
 
            String uri = req.uri();
 
            if(uri.equals("/favicon.ico")){
                return;
            } 
            
            if (req.method().equals(HttpMethod.POST)) {
            	QueryStringDecoder decoderQuery = new QueryStringDecoder(req.uri());
            	 
                 Map<String, List<String>> uriAttributes = decoderQuery.parameters();
                 for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                     for (String attrVal : attr.getValue()) {
                    	 System.out.println("URI: " + attr.getKey() + '=' + attrVal + "\r\n");
                     }
                 }
            }
            if (req.method().equals(HttpMethod.GET)) {
 
            	 QueryStringDecoder decoderQuery = new QueryStringDecoder(req.uri());
            	 
                 Map<String, List<String>> uriAttributes = decoderQuery.parameters();
                 for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                     for (String attrVal : attr.getValue()) {
                    	 System.out.println("URI: " + attr.getKey() + '=' + attrVal + "\r\n");
                     }
                 }
//            	InterfaceHttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), req);
//                try{
//                	
//                    List<InterfaceHttpData> postList = decoder.getBodyHttpDatas();
//                    // 璇诲彇浠庡鎴风浼犺繃鏉ョ殑鍙傛暟
//                    for (InterfaceHttpData data : postList) {
//                        String name = data.getName();
//                        System.out.println(data.toString());
//                        String value = null;
//                        if (InterfaceHttpData.HttpDataType.Attribute == data.getHttpDataType()) {
//                            MemoryAttribute attribute = (MemoryAttribute) data;
//                            attribute.setCharset(CharsetUtil.UTF_8);
//                            value = attribute.getValue();
//                            System.out.println("name:"+name+",value:"+value);
//                            
//                        }
//                    }
// 
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
            }
 
 
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("ok".getBytes()));
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
 
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }
   

	private MockHttpServletRequest createServletRequest(HttpRequest httpRequest) {
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(httpRequest.uri()).build();
//		JspConfigDescriptor e=new JspConfigDescriptor() {
//			
//			@Override
//			public Collection<TaglibDescriptor> getTaglibs() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		};
//		this.servletContext.getJspConfigDescriptor().getJspPropertyGroups().add((JspPropertyGroupDescriptor) e);
		MockHttpServletRequest servletRequest = new MockHttpServletRequest(this.servletContext);
//		System.out.println(servletRequest.getSession());
		servletRequest.setRequestURI(uriComponents.getPath());
		servletRequest.setPathInfo(uriComponents.getPath());
		servletRequest.setMethod(httpRequest.method().name().toString());

		if (uriComponents.getScheme() != null) {
			servletRequest.setScheme(uriComponents.getScheme());
		}
		if (uriComponents.getHost() != null) {
			servletRequest.setServerName(uriComponents.getHost());
		}
		if (uriComponents.getPort() != -1) {
			servletRequest.setServerPort(uriComponents.getPort());
		}

		 for (CharSequence name : httpRequest.headers().names()) {
//		 for (String value : name.toString()) {
		 servletRequest.addHeader((String) name, httpRequest.headers().get(name));
//		 }
		 }

		 try {
			 String s =uriComponents.getQuery();
			 if(s!=null&&!"".equals(s))
				 servletRequest.setContent(uriComponents.getQuery().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		try {
			if (uriComponents.getQuery() != null) {
				String query = UriUtils.decode(uriComponents.getQuery(), "UTF-8");
				servletRequest.setQueryString(query);
			}

			for (Entry<String, List<String>> entry : uriComponents.getQueryParams().entrySet()) {
				for (String value : entry.getValue()) {
					servletRequest.addParameter(UriUtils.decode(entry.getKey(), "UTF-8"),
							UriUtils.decode(value, "UTF-8"));
				}
			}
		} catch (UnsupportedEncodingException ex) {
			// shouldn't happen
		}

		
//         InterfaceHttpData postData = decoder.; // //  
//                                                                     // 璇诲彇浠庡鎴风浼犺繃鏉ョ殑鍙傛暟  
//         String question = "";  
//         if (postData.getHttpDataType() == HttpDataType.Attribute) {  
//             Attribute attribute = (Attribute) postData;  
//             question = attribute.getValue();  
////             System.out.println("q:" + question);  
//
//         }  
//         if (question != null && !question.equals("")) {  
//
//             HttpResponse res = new DefaultHttpResponse(HTTP_1_1, OK);  
//             String data = "浣犲ソ锛孭OST";  
//             ChannelBuffer content = ChannelBuffers.copiedBuffer(data,  
//                     CharsetUtil.UTF_8);  
//             res.setHeader(CONTENT_TYPE, "text/html; charset=UTF-8");  
//             res.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");  
//             setContentLength(res, content.readableBytes());  
//             res.setContent(content);  
//             sendHttpResponse(ctx, req, res);  
//
//         }  
         
		return servletRequest;
	}
	
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 娣诲姞

		// Global.group.add(ctx.channel());
//		System.out.println("瀹㈡埛绔笌鏈嶅姟绔繛鎺ュ紑鍚�);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		ctx.channel().config().get
		// 绉婚櫎
		// Global.group.remove(ctx.channel());
//		log.info("瀹㈡埛绔笌鏈嶅姟绔繛鎺ュ叧闂�);
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//		System.out.println(11111);
//		cause.printStackTrace();
		
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

		ctx.flush();
//		System.out.println("瀹㈡埛绔敹鍒版湇鍔″櫒鍝嶅簲鏁版嵁澶勭悊瀹屾垚");
	}

//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		cause.printStackTrace();
//		if (ctx.channel().isActive()) {
//			sendError(ctx, INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
//		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
//		// response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
//		// response.setContent(Unpooled.copiedBuffer(
//		// "Failure: " + status.toString() + "\r\n",
//		// CharsetUtil.UTF_8));
//
//		// Close the connection as soon as the error message is sent.
//		ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//	}

}
