package com.rodcell.comm.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFile {
	public static void main(String[] args) throws Exception {
		download("http://www.baidu.com/img/bdlogo.gif","d:/tmp/baidu.gif");
	}
	
	public static void download(String url,String savePath) throws Exception{
		// 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url1 = new URL(url);
        FileOutputStream fs = new FileOutputStream(savePath);

        try {
            URLConnection conn = url1.openConnection();
            InputStream inStream = conn.getInputStream();
           
            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
//                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	 fs.close();
        }
	}

}
