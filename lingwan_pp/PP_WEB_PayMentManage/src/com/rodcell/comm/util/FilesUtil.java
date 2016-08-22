package com.rodcell.comm.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.io.Files;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年4月29日 下午3:25:52 
 * 类说明 
 */
public class FilesUtil {
	public static void main(String[] args) throws IOException {
//		Files.readLines(file, Charset.defaultCharset());
		
	}
	 
	public static void createParentDirs(String path) throws IOException{
		Files.createParentDirs(new File(path));
	}
	
	public static void copy(File from,File to) throws IOException{
		Files.copy(from, to);
	}
	
	/**
	 * File file,Charsets.UTF_8
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(File file,Charset charset) throws IOException{
		return Files.readLines(file, charset);//Charset.defaultCharset().forName(charset)
	}
	
	
	public static void move(File from,File to) throws IOException{
		Files.move(from, to);
	}
	
	/**
	 * 
	 * @param txt
	 * @param to
	 * @param charset  Charsets.UTF_8
	 * @throws IOException
	 */
	public static void write(String txt,File to,Charset charset) throws IOException{
		CharSequence from=txt;
		Files.write(from, to, charset);		 
	}
}
