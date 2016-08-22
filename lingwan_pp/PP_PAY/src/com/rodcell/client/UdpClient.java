package com.rodcell.client;

import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.rodcell.comm.Constant;
import com.rodcell.comm.util.JSONUtil;
import com.rodcell.entity.PayMainRetry;
import com.rodcell.service.thread.CallPayService;
import com.rodcell.servlet.BaseAction;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年9月2日 上午10:20:29 
 * 类说明 
 */
public class UdpClient extends Thread{

	public static Logger log = Logger.getLogger(UdpClient.class);
	
	
	public static BlockingQueue<MsgObject> MsgQueue = new LinkedBlockingQueue<MsgObject>();
	
	
	
    public static void produce(MsgObject obj) throws InterruptedException {
       
//    	MsgQueue.put(obj);
    }

   
    public static MsgObject consume() throws InterruptedException {
        return MsgQueue.take();
    }
    
    
	
	public void sendMsg(MsgObject msg){
		try{
			
			Properties p=new Properties();
			InputStream is;
			is = new java.io.FileInputStream(Constant.APP_ROOT_PATH+"/propConfig/server.properties");
			p.load(is);
			int PORT = Integer.valueOf(p.getProperty("udpport"));
			String host = p.getProperty("udphost");
			msg.setServer(p.getProperty("udpserver"));
			
			DatagramSocket client = new DatagramSocket();
			
			String sendStr = JSONUtil.objectToString(msg);
			log.error("udp,sendStr:"+sendStr);
			byte[] sendBuf;
			sendBuf = sendStr.getBytes();
			InetAddress addr = InetAddress.getByName(host);
			int port = PORT;
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length,
					addr, port);
			client.send(sendPacket);
			byte[] recvBuf = new byte[100];
			DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
			client.receive(recvPacket);
			String recvStr = new String(recvPacket.getData(), 0,
					recvPacket.getLength());
//			System.out.println("收到:" + recvStr);
			client.close();
		}catch(Exception e){
			log.error("", e);
		}
	}
	
	
	
	/**
	 * 线程执行的频率 1秒查询
	 */
	private static final int step = 2000;
	@Override
	public void run(){
		long s=0;
		while(true){
			try {
				MsgObject msg =consume();
				sendMsg(msg);
				long e=System.currentTimeMillis();
				if((e-s)<step)
					Thread.sleep((step-(e-s)));
			} catch (Exception e) {				 
				log.error("", e);
			}finally{
				 
			}
			
			
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
	}

}
