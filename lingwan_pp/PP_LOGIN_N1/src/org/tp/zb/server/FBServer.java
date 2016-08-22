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

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tp.comm.Constant;
import com.tp.service.task.FBTask;

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class FBServer {

    static  boolean SSL = System.getProperty("ssl") != null;
    static  int PORT = Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));
    
    static String host = "0.0.0.0";
    
	public static void loadMainParm() throws Exception {
		 
		Constant.APP_ROOT_PATH = FBServer.class.getProtectionDomain()
				.getCodeSource().getLocation().getFile();
		if (Constant.APP_ROOT_PATH.startsWith("/"))
			Constant.APP_ROOT_PATH = "/" + Constant.APP_ROOT_PATH;
		Constant.CTX = new FileSystemXmlApplicationContext(
				Constant.APP_ROOT_PATH + "spring/spring.xml");
				
	}
	

    public static void main(String[] args) throws Exception {
    	loadMainParm();
    	FBTask f=new FBTask();
    	f.start();
    	System.in.read();
		while (true) {
			Thread.sleep(1000);
		}
    }
}
