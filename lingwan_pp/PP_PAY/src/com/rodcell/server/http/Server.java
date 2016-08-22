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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.servlet.FilterRegistration;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.IdleTimeoutFilter;

import com.rodcell.client.MsgObject;
import com.rodcell.client.UdpClient;
import com.rodcell.comm.Constant;
import com.rodcell.comm.util.FilesUtil;
import com.rodcell.comm.util.HttpClientUtil;


public class Server {
    private static final Logger LOGGER = Grizzly.logger(Server.class);
    
    private static int PORT = 8080;
	private static String host="0.0.0.0";
	private static  String mapping="/*";
	private static String mvcmapping="/pay/*";
	private static String staticfile="web/";
	
	/**
	 * 初始 spring
	 * @throws IOException
	 */
//	private void initSpring() {
//		Constant.CTX = new FileSystemXmlApplicationContext(
//				Constant.APP_ROOT_PATH+"\\spring\\spring.xml"); 
//	}
	
	
	/**
	 * 加载服务器配置
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	public static void loadMainParm() throws Exception {
		 
			Properties p=new Properties();
			InputStream is;
			is = new java.io.FileInputStream(Constant.APP_ROOT_PATH+"/propConfig/server.properties");
			p.load(is);
			PORT = Integer.valueOf(p.getProperty("port"));
			host = p.getProperty("host");
			mapping=p.getProperty("mapping");
			mvcmapping=p.getProperty("mvcmapping");
			staticfile=p.getProperty("staticfile");
			Constant.serverkey=Integer.parseInt(p.getProperty("serverkey"));
			Constant.CALL_SERVER_THREAD_SIZE=Integer.parseInt(p.getProperty("callserverThreadsize"));
		 
	}
	
	
    
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		HttpClientUtil http1= new HttpClientUtil();
//		try{
//			String res = http1.get("https://mp.molthailand.com/web-test/index.php?r=command/verify",  Constant.UTF8);
//			System.out.println("Mol mol_check_url  response:" + res);	
//		}catch(Exception e){
//			
//		}
			
		final HttpServer server = new HttpServer();
		// Prepare the low priority thread pool configuration
		final ThreadPoolConfig httpThreadPoolConfig = ThreadPoolConfig
				.defaultConfig().copy()
				.setPoolName("http-priority-thread-pool").setCorePoolSize(100)
				.setMaxPoolSize(200)
				.setMemoryManager(MemoryManager.DEFAULT_MEMORY_MANAGER)
				.setPriority(Thread.MAX_PRIORITY);

		// 获取cpu线程数，这里是demo，实际把这个放在方法外面
		int processorsBasedThreadCount = Runtime.getRuntime()
				.availableProcessors();

		// 设置工作线程池
		final DelayedExecutor timeoutExecutor = IdleTimeoutFilter
				.createDefaultIdleDelayedExecutor();
		timeoutExecutor.start();

		// Initialize the low priority thread pool
		final ExecutorService httpExecutorService = GrizzlyExecutorService
				.createInstance(httpThreadPoolConfig);
		try {
			Constant.APP_ROOT_PATH = Server.class.getProtectionDomain()
					.getCodeSource().getLocation().getFile();
			if (Constant.APP_ROOT_PATH.startsWith("/"))
				Constant.APP_ROOT_PATH = "/" + Constant.APP_ROOT_PATH;

			
			Server http = new Server();
			http.loadMainParm();

			// Initialize the high priority thread pool
			// final ExecutorService highPriorityExecutorService =
			// GrizzlyExecutorService.createInstance(highPriorityThreadPoolConfig);

			final ServerConfiguration config = server.getServerConfiguration();
			config.addHttpHandler(new MyServletHandler(httpExecutorService),
					mapping);
			config.setDefaultQueryEncoding(Charsets.UTF8_CHARSET);

			final NetworkListener networkListener = new NetworkListener(
					"listener", host, PORT);

			
			// thread pool - we have to null it.
			networkListener.getTransport().setWorkerThreadPoolConfig(null);

			

			server.addListener(networkListener);

			// static1 folder
			server.getServerConfiguration().addHttpHandler(
					// 加载静态文件
					new StaticHttpHandler(Constant.APP_ROOT_PATH + staticfile),
					"/");

			/**
			 * task 的线程
			 */
			WorkerTask workerTask = new WorkerTask();
			workerTask.start();

			// Initialize and add Spring-aware Jersey resource
			/** add spring mvc */
			WebappContext ctx = new WebappContext("WebContent", "/");// "/springmvc"
			// SpringServlet s=new SpringServlet();
			final ServletRegistration reg = ctx.addServlet("spring",
					org.springframework.web.servlet.DispatcherServlet.class);// new
																				// SpringServlet()
			reg.addMapping(mvcmapping);// "/springmvc" "/web/*"
			reg.setLoadOnStartup(1);

			Map config1 = new HashMap();
			config1.put("contextConfigLocation", "classpath*:/spring/mvc*.xml");
			reg.setInitParameters(config1);
			ctx.addContextInitParameter("contextConfigLocation",
					"classpath:spring/spring.xml");
			ctx.addListener("org.springframework.web.context.ContextLoaderListener");
			ctx.addListener("com.rodcell.listener.InitDataInApplication");

			FilterRegistration f = ctx
					.addFilter(
							"characterEncodingFilter",
							org.springframework.web.filter.CharacterEncodingFilter.class);
			Map m = new HashMap();
			m.put("encoding", "UTF-8");
			m.put("forceEncoding", "true");
			f.setInitParameters(m);
			f.addMappingForUrlPatterns(null, "/*");
			f = ctx.addFilter("sessionFilter",
					com.rodcell.filter.SessionFilter.class);
			f.addMappingForUrlPatterns(null, "/*");
			Map m1 = new HashMap();
			m1.put("sessionFilter", "/*");
			f.setInitParameters(m);

			ctx.deploy(server);

			
			/**
			 * 初始化
			 */
			AppInit app = new AppInit();
			app.init();

			// Start the server
			server.start();
			
			new UdpClient();//.start();

			String initString = HttpClientUtil.get("http://" + host + ":"
					+ PORT + mvcmapping, "utf-8");
			LOGGER.info("http://" + host + ":" + PORT + mvcmapping
					+ " ===init spring mvc servlet "+initString);
			

			
			LOGGER.info("http://" + host + ":" + PORT + mapping
					+ " is servlet");
			LOGGER.info("http://" + host + ":" + PORT
					+ "/jsp/index.jsp is static file");
			LOGGER.info("http://" + host + ":" + PORT + mvcmapping
					+ " is spring mvc");
			LOGGER.info("The server is running. Press enter to stop...");
			

			int pid =0;
			RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
	        String name = runtime.getName();
//	        System.out.println("当前进程的标识为："+name);
	        int index = name.indexOf("@");
	        if (index != -1) {
	            pid = Integer.parseInt(name.substring(0, index));
	           FilesUtil.write(pid+"", new File(Constant.APP_ROOT_PATH+"/pid.pid"), Charset.defaultCharset());
	        }
	        
	        BlockingQueue<MsgObject> MsgQueue1 = new LinkedBlockingQueue<MsgObject>();
			 MsgQueue1.take();
			System.in.read();
			LOGGER.info("The server is start");
			
			
			while (true) {
				
				Thread.sleep(1000 * 6000);

				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				// 或者这么写也可以：DataInputStream reader = new
				// DataInputStream(System.in);
				String s = reader.readLine(); // 这样得到的是String类型的，需要转换为需要的类型
//				System.out.println(s);
				
				
			}
			//System.exit(0);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
			System.exit(0);
		} finally {
			server.shutdownNow();
			httpExecutorService.shutdownNow();
			System.exit(0);
		}

	}
}
