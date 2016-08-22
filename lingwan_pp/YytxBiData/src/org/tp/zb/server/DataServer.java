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

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.tp.comm.Constant;
import com.tp.service.task.ItemFlowTask;

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class DataServer {

   
    
	public static void loadMainParm() throws Exception {
		 
		Constant.APP_ROOT_PATH = DataServer.class.getProtectionDomain()
				.getCodeSource().getLocation().getFile();
		if (Constant.APP_ROOT_PATH.startsWith("/"))
			Constant.APP_ROOT_PATH = "/" + Constant.APP_ROOT_PATH;
		
		
		Properties p=new Properties();
		InputStream is;
		is = new java.io.FileInputStream(Constant.APP_ROOT_PATH+"/propConfig/config.properties");
		p.load(is);
		String logPath1= String.valueOf(p.getProperty("logPath1"));
		String logPath2= String.valueOf(p.getProperty("logPath2"));
		String logPath3= String.valueOf(p.getProperty("logPath3"));
		String logPath4= String.valueOf(p.getProperty("logPath4"));
		String logPath5= String.valueOf(p.getProperty("logPath5"));
		String logPath6= String.valueOf(p.getProperty("logPath6"));		
		
		
		Constant.CTX = new FileSystemXmlApplicationContext(
				Constant.APP_ROOT_PATH + "spring/spring.xml");
		
		
		
	}
	

    public static void main(String[] args) throws Exception {
    	loadMainParm();
    	ItemFlowTask f=new ItemFlowTask();
    	f.start();
    	System.in.read();
		while (true) {
			Thread.sleep(1000);
		}
    }
}
