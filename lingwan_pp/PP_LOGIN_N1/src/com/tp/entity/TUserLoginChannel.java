package com.tp.entity;

import java.util.Date;
import java.util.Map;

import org.tp.comm.util.JSONUtil;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年6月18日 下午3:26:55 
 * 类说明 
 */
public class TUserLoginChannel {
	
	private long id;
	private long u_ppid;
	private String login_name;
	private int login_type;
	private String login_password;
	private Date createtime;
	private String u_info;
	private long s_id;
	
	
	
	public TUserLoginChannel(){}
	
	public TUserLoginChannel(long id, long u_ppid, String login_name,
			int login_type, String login_password, Date createtime,String u_info,long s_id) {
		super();
		this.id = id;
		this.u_ppid = u_ppid;
		this.login_name = login_name;
		this.login_type = login_type;
		this.login_password = login_password;
		this.createtime = createtime;
		this.u_info=u_info;
		this.s_id= s_id;
	}
	
	
	
	public long getS_id() {
		return s_id;
	}

	public void setS_id(long s_id) {
		this.s_id = s_id;
	}

	public String getU_info() {
		return u_info;
	}

	public void setU_info(String u_info) {
		this.u_info = u_info;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getU_ppid() {
		return u_ppid;
	}
	public void setU_ppid(long u_ppid) {
		this.u_ppid = u_ppid;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public int getLogin_type() {
		return login_type;
	}
	public void setLogin_type(int login_type) {
		this.login_type = login_type;
	}
	public String getLogin_password() {
		return login_password;
	}
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	
	public static void main(String[] args) {
		TUserLoginChannel user=new TUserLoginChannel(Long.valueOf(0), 123, "10202688394586537", Integer.parseInt("1"), "", new Date(),"",11111111);	
		Map data = JSONUtil.objToMap(user);
		
		System.out.println(data);
		
	}

}
