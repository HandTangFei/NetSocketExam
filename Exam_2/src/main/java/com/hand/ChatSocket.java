package com.hand;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ChatSocket extends Thread {
	Socket socket;
	public ChatSocket(Socket s){
		this.socket = s;
	}
	public void run(){  
		//包装输入流	
        try {
                FileInputStream fis = new FileInputStream("../target.pdf");   //实例化一个字符型的输入流
                byte[] input = new byte[2];
                while(fis.read(input)!= -1){
                socket.getOutputStream().write(input);
                }
                fis.close();                //最后关闭输入流
                System. out.println( "The file is being transfered!");
      } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
      } catch (IOException e) {
           e.printStackTrace();
      }	
	}
}
