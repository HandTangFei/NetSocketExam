package com.hand.client;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatManager {
	private ChatManager(){}
	private static final ChatManager instance = new ChatManager();
	public static ChatManager getCM(){
		return instance;
	}
	MainWindow window;   //创建一个新的MainWindow类，将进行数据收发的显示和控制
	Socket socket;
	String IP;           //因为不能再connect的线程中直接调用，申请一个本地变量

	public void setWindow(MainWindow window) {
		this.window = window;
	}
	
	public void connect(String ip){
		//呼叫连接时 要创建一个新的线程
		this.IP = ip;
		new Thread(){
			public void run() {
				try {
					socket = new Socket(IP,12346);   //建立链接,socket创建后就能获得输入、输出流了				
					InputStream is = socket.getInputStream();     //获得输入流   
	    			FileOutputStream fos = new FileOutputStream("target.pdf");  //按字节输出文件
	    			byte input[] = new byte[2];      //byte类型数组
	    			while (is.read(input)!= -1) {    //当为-1时，说明文件读取完毕
	    				fos.write(input);
	    			}
	    			window.appendText("文本已传输完毕！");
	    			is.close();
	    			fos.close();
	               System.out.println("文本已传输完毕！");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
//	public void send(String out){
//		if(writer != null){
//			writer.write(out+"\n");   //当这一行完结了，才会强制输出
//			writer.flush();             //强制刷新输出
//		}else{
//			window.appendText("当前的链接已经中断");
//		}
//	}
}
