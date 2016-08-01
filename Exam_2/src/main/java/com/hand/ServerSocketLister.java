package com.hand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerSocketLister extends Thread {
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(12346);
			while(true){
				Socket socket = serverSocket.accept();   //会阻塞当前线程，已经在监听9999这个端口,建立链接
				JOptionPane.showMessageDialog(null, "客户端连接到服务器12346端口"); //在浏览器中输入127.0.0.1:9999程序结束
				new ChatSocket(socket).start(); //新的线程,每个客户端享受独立的一个窗口
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
