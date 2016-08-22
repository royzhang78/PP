package com.rodcell.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;
import org.springframework.stereotype.Service;

/** 
 * @author zhang bin 
 * @Email zhangbin@rodcell.com
 * @version 创建时间：2014年5月16日 下午7:30:29 
 * 类说明  回调充值服务器
 */
@Service(value="callServerMQTemplate") 
public class MQMessageSender {
	private  String queueOutName = "com.rodcell.pay.server.in";

	@Autowired
	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendTextMsg(final String msg) {

		this.getJmsTemplate().send(queueOutName, new MessageCreator() {
			// 这里创建了一个 message 对象,然后可以对该对象进行 各种属性的定义
			private Message message;

			public Message createMessage(Session session) throws JMSException {
				message = session.createTextMessage(msg);

				return message;
			}
		});
	}

	public void getTextMsg() {
		while (true) {
			TextMessage txtmsg = (TextMessage) jmsTemplate
					.receive(queueOutName);
			if (null != txtmsg) {
				System.out.println("[DB Proxy] " + txtmsg);
				try {
					System.out.println("[DB Proxy] 收到消息内容为: "
							+ txtmsg.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}