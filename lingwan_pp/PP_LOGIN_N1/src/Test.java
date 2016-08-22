import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import org.tp.comm.util.HttpClientUtil;

import com.sun.management.OperatingSystemMXBean;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2015年11月3日 下午3:20:54 
 * 类说明 
 */
public class Test  extends Thread{

	@Override
	public void run(){
		while(true){
			int i=1;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main1(String[] args) throws InterruptedException {
		
		String s="21844 root      20   0 5594m 514m  13m S  0.0  3.4   6:38.82 java";
		
		String tmp[] = s.split("S  ");
		double i = Double.parseDouble(tmp[1].split("  ")[0]);
		System.out.println(i);
		
		new Test().start();
		
		
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);
		
		while(true){
			// What % CPU load this current JVM is taking, from 0.0-1.0
			System.out.println("a==="+osBean.getProcessCpuLoad());
			
			
			// What % load the overall system is at, from 0.0-1.0
			System.out.println("b==="+osBean.getSystemCpuLoad());
			Thread.sleep(10000);
		}
		
	}
	
	
	
	public static double a(double a,double b, int num){
		if(num<=0)return a;
		a+=a*b/100;
		num--;
		return a(a, b, num);
	}
	 
	
	public static void main(String[] args) throws IOException {
		Map m=new HashMap();
		m.put("serviceName", "CheckToken");
		m.put("callPara","{'u_ppid':'1469690908044','login_name':'test123','login_type':2,'u_releaseChannel':'test','login_type':2}");
		
//		String s= HttpClientUtil.post("http://127.0.0.1:8082/api", m, false);
		
		HttpRequest q =HttpRequest
		        .post("http://127.0.0.1:8082/api").query(m);
		        //q.bodyText("{'serviceName':'startGameJson','callPara':{'u_deviceid':'ios','serverName':'heroforce.androd.en','u_deviceid':'TEST','serviceName':'startGameJson','login_name':'test123','login_type':2,'u_releaseChannel':'test','login_type':2}}");
		q.bodyText("{'serviceName':'CheckToken','callPara':{'token':'e50f8644141d24c308cd87bf6c8bfb32','u_ppid':'1469690908044'}}");
		        
		HttpResponse response =  q.send();
		String s = response.bodyText();
		
		response.close();
		
		System.out.println(s);
//		System.out.println(Test.a(100000,20,4));
		
//		Runtime.getRuntime().exec("cls");
//		while(true){
//			 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		        //或者这么写也可以：DataInputStream reader = new DataInputStream(System.in);
//			 String s =reader.readLine(); // 这样得到的是String类型的，需要转换为需要的类型
////			 System.out.println(s);
//		}
//		Process process = null;  
//        List<String> processList = new ArrayList<String>();  
//        try {  
//            process = Runtime.getRuntime().exec("cmd /c java -version ");  
//            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));  
////            String line = "";  
////            while ((line = input.readLine()) != null) {  
////                processList.add(line);  
////            }  
////            input.close();  
//            
//            // 从输入流中读取文本  
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));  
//              
//            // 构造一个写出流并指定输出文件保存路径  
//            FileWriter fw = new FileWriter(new File("e:/CmdInfo.txt"));  
//              
//            String line = null;  
//              
//            // 循环读取  
//            while ((line = reader.readLine()) != null) {  
//             // 循环写入  
//             fw.write(line + "\n");  
//            }  
//              
//            // 刷新输出流  
//            fw.flush();  
//              
//            // 关闭输出流  
//            fw.close();  
//              
//            // 关闭输出流  
//            process.getOutputStream().close();  
              
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//  
//        for (String line : processList) {  
//            System.out.println(line);  
//        }  
        
		 
	}

}
