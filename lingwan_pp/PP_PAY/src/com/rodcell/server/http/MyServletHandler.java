/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.rodcell.server.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.RequestExecutorProvider;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import com.rodcell.comm.Command;

/**
 * The low-priority {@link HttpHandler} executing long lasting task.
 * 
 * @author Alexey Stashok
 */
public class MyServletHandler extends HttpHandler {
 
    
    private final RequestExecutorProvider executorProvider;
    
//    private final AtomicInteger counter = new AtomicInteger();
    
    public MyServletHandler(final ExecutorService threadPool) {
        this.executorProvider = new RequestExecutorProvider() {

            @Override
            public Executor getExecutor(Request request) {
                return threadPool;
            }
        };
    }

    /**
     * @return the {@link RequestExecutorProvider} to be used to 
     * call {@link MyServletHandler#service(org.glassfish.grizzly.http.server.Request, org.glassfish.grizzly.http.server.Response)}.
     */
    @Override
    public RequestExecutorProvider getRequestExecutorProvider() {
        return executorProvider;
    }

    
    @Override
    public void service(final Request request, final Response response)
            throws Exception {
        // sleeping for 2 seconds (simulating long lasting task)
//        Thread.sleep(2000);
//        response.getWriter().write(Thread.currentThread().getName() +
//                ": done task #" + counter.incrementAndGet());
    	
		try { 
			String o = Command.exce(request,response);
			
//			response.getWriter().write(String.valueOf(o));
			if(o==null || o.length()==0){
				response.getWriter().write("");
			}else{
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
		        
		        GZIPOutputStream gout = new GZIPOutputStream(bout);
		        gout.write(o.getBytes());
		        gout.close();
		        
		        byte b[] = bout.toByteArray();  //得到压缩后的数据
				response.setHeader("Content-Encoding", "gzip");
		        response.setHeader("Content-Length", b.length+"");
		        response.setHeader("Pragma", "no-cache");
		        response.setHeader("Cache-Control", "no-cache");
		        
	//			response.getWriter().write(String.valueOf(b));
				response.getOutputStream().write(b);
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}finally {
//	        response.resume(); 
	    }
    }    
    
    
//	@Override
//   public void service(final Request request, final Response response)throws Exception {
//		
//		
//		if(Constant.ISPHPRPC){		
//	        PHPRPC_Server p = new PHPRPC_Server();
//	        try {
//	        	p.add("exce",Command.class);
//	        	
//	        	p.add("resultMsg",Command.class);
//	        	
//	        	p.add("payGem",Command.class);
//	        	
//	        	p.add("bindEmail",Command.class);
//				p.start((HttpServletRequest)request, (HttpServletResponse)response);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else{
//				long i =System.currentTimeMillis();
//				Command command = new Command();
//				Object o = command.exce(request,(HttpServletRequest)request);
//				try {
//					response.getWriter().write(String.valueOf(o));
//				} catch (IOException e) { 
//					e.printStackTrace();
//				}
//				 
//				logger.info("time = "+(System.currentTimeMillis()-i));
//
//		}
//  }
}
