package com.how2java.bitcoin;



import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/ws/bitcoinServer")//把它标记为一个WebSocket Server
/*
 * OnOpen 表示有浏览器链接过来的时候被调用
	OnClose 表示浏览器发出关闭请求的时候被调用
	OnMessage 表示浏览器发消息的时候被调用
	OnError 表示有错误发生，比如网络断开了等等

sendMessage 用于向浏览器回发消息
 * 
 * **/
public class BitCoinServer {
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		ServerManager.add(this);     
	}
	
	//用于向浏览器回发消息
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
	}

	@OnClose
	public void onClose(){
		ServerManager.remove(this);  
	}


	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
	}

	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");
		error.printStackTrace();
	}


}
