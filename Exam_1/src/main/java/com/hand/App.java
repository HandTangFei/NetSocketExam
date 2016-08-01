package com.hand;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class App 
{
	public static void main( String[] args )
	{
		System.out.println( "Hello World!" );

		try {		     
			
			URL url = new URL( "http://files.saas.hand-china.com/java/target.pdf");   //实例化一个url
			URLConnection connection = url.openConnection();  //通过url建立连接
			InputStream is = connection.getInputStream();     //获得输入流   
			FileOutputStream fos = new FileOutputStream("target.pdf");  //按字节输出文件
			byte input[] = new byte[2];      //byte类型数组
			while (is.read(input)!= -1) {    //当为-1时，说明文件读取完毕
				fos.write(input);
			}
			fos.close();
			is.close(); 
			System.out.println( "Target.pdf has been parsed!" );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
