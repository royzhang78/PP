package org.tp.comm.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jodd.log.Logger;
import jodd.log.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Image2Base64 {
	public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Image2Base64.class);
	 public static void main(String[] args)
	    {
	        String strImg = GetImageStr();
	        logger.info(strImg);
//	        System.out.println("ddd//"+strImg+"");
//	        strImg="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";
	        GenerateImage(strImg);
	    }
	    public static String GetImageStr()
	    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        String imgFile = "E:/tmp/1.png";//待处理的图片
	        InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
	        try 
	        {
	            in = new FileInputStream(imgFile);        
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
//	        System.out.print(encoder.encode(data));
	        return encoder.encode(data);//返回Base64编码过的字节数组字符串
	    }
	    public static boolean GenerateImage(String imgStr)
	    {//对字节数组字符串进行Base64解码并生成图片
	        if (imgStr == null) //图像数据为空
	            return false;
	        BASE64Decoder decoder = new BASE64Decoder();
	        try 
	        {
	            //Base64解码
	            byte[] b = decoder.decodeBuffer(imgStr);
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {//调整异常数据
	                    b[i]+=256;
	                }
	            }
	            //生成jpeg图片
	            String imgFilePath = "E:/tmp/2.png";//新生成的图片
	            OutputStream out = new FileOutputStream(imgFilePath);    
	            out.write(b);
	            out.flush();
	            out.close();
	            return true;
	        } 
	        catch (Exception e) 
	        {
	            return false;
	        }
	    }
}
