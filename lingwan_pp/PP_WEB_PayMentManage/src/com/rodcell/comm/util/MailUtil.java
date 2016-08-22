package com.rodcell.comm.util;

import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SimpleAuthenticator;
import jodd.mail.SmtpServer;

public class MailUtil {
	
	
	
	public void sendMail(String smtp,String username,String pwd,String mailText){
		SmtpServer smtpServer =new SmtpServer(smtp, username,pwd); 
		    SendMailSession session = smtpServer.createSession();
		    session.open();
		    Email m=new Email();
		    m.addText(mailText);
		    session.sendMail(m);
		    session.close();
	}
}
