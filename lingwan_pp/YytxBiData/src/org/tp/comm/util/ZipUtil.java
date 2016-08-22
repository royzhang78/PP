package org.tp.comm.util; 

import java.io.*;
import java.util.logging.Logger;
import java.util.zip.*;

 
public class ZipUtil {
    private final static Logger logger = Logger.getLogger(ZipUtil.class.getName());
    private static final int BUFFER = 1024 * 10;
    
     
    
    
  
    
  /**
   * 压缩单个文件
   * @param srcPathName
   * @param zipfile
   */
    public  static void compress(String srcPathName,String zipfile) {  
    	File zipFile=new File(zipfile);  
        File file = new File(srcPathName);  
        if (!file.exists())  
            throw new RuntimeException(srcPathName + "不存在！");  
        try {  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,  
                    new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);  
            String basedir = "";  
//            compress(file, out, basedir);  
            compressFile(file, out, basedir);  
            out.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
    /**
     * 压缩文件夹
     * @param srcPathName
     * @param zipfile
     */
    public  static void compressDirectory(String srcPathName,String zipfile) {  
    	File zipFile=new File(zipfile);  
        File file = new File(srcPathName);  
        if (!file.exists())  
            throw new RuntimeException(srcPathName + "不存在！");  
        try {  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,  
                    new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);  
            String basedir = "";  
//            compress(file, out, basedir);  
            compressDirectory(file, out, basedir);  
            out.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 
    
  
    public static void compress(File file, ZipOutputStream out, String basedir) {  
        /* 判断是目录还是文件 */  
        if (file.isDirectory()) {  
//            System.out.println("压缩：" + basedir + file.getName());  
            compressDirectory(file, out, basedir);  
        } else {  
//            System.out.println("压缩：" + basedir + file.getName());  
            compressFile(file, out, basedir);  
        }  
    }  
  
    /** 压缩一个目录 */  
    public static void compressDirectory(File dir, ZipOutputStream out, String basedir) {  
        if (!dir.exists())  
            return;  
  
        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");  
        }  
    }  
  
    /** 压缩一个文件 */  
    public static void compressFile(File file, ZipOutputStream out, String basedir) {  
//        if (!file.exists()) {  
//        	
//        }  
        try {  
            BufferedInputStream bis = new BufferedInputStream(  
                    new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(basedir + file.getName());  
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1) {  
                out.write(data, 0, count);  
            }  
            bis.close();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 

    public static void main(String[] args) throws IOException {
        String path="D:\\log\\";
        ZipUtil.compressDirectory(path,"D:\\2.zip");
//        String zipfile ="D:\\log\\error.log";
//        File zipFile = new File(zipfile);
//        String output="D:\\temp\\c";
//        ZipUtil.unzip(zipFile, output);
    }
}

