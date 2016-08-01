package com.hand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.JsonObject;

/**
 * Hello world!
 *
 */
public class App 
{  

	public static void main( String[] args )
	{
		String[] res_spl =null;
		System.out.println("Hello");
		res_spl = parse();
		saveAsXMl(res_spl);
		saveAsJson(res_spl);
	}
	public static String[] parse(){
		String[] res_spl =null;
		HttpClient client = HttpClients.createDefault();  //注意HttpClients 后面多了s
		HttpGet get = new HttpGet( "http://hq.sinajs.cn/list=sz300170");
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils. toString(entity, "UTF-8");
			System. out.println("解析的数据");
			System. out.println(result);
			res_spl = result.split(",");
			res_spl[0] = res_spl[0].substring(21);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();       
		}
		
		return res_spl;
	}
	
	    //转换为xml格式
	public static void saveAsXMl(String[] res_spl){
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("xml" );

			Element lan = document.createElement( "stock");

			Element name = document.createElement( "name");
			name.setTextContent(res_spl[0] );               
			Element open = document.createElement( "open");
			open.setTextContent( res_spl[1]);

			Element close = document.createElement( "close");
			close.setTextContent(res_spl[2] );               
			Element current = document.createElement( "current");
			current.setTextContent( res_spl[3]);                   
			Element high = document.createElement( "high");
			high.setTextContent(res_spl[4] );               
			Element low = document.createElement( "low");
			low.setTextContent( res_spl[5]);

			lan.appendChild(name);
			lan.appendChild(open);
			lan.appendChild(close);
			lan.appendChild(current);
			lan.appendChild(high);
			lan.appendChild(low);

			root.appendChild(lan);
			document.appendChild(root);
			TransformerFactory tfFactory = TransformerFactory.newInstance();
			Transformer tf = tfFactory.newTransformer();
			StringWriter writer = new StringWriter();
			tf.transform( new DOMSource(document), new StreamResult(writer));      //输出流
			System. out.println("\nXML格式数据");
			System. out.println(writer.toString());
			tf.transform( new DOMSource(document), new StreamResult(new File("hand.xml" ))); //保存到文件中

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} 
	}

	//保存为Json格式文件
	public static void saveAsJson(String[] res_spl){

		JsonObject lan1 = new JsonObject();
		lan1.addProperty( "name", res_spl[0]);
		lan1.addProperty( "open", res_spl[1]);
		lan1.addProperty( "close", res_spl[2]);
		lan1.addProperty( "current", res_spl[3]);
		lan1.addProperty( "high", res_spl[4]);
		lan1.addProperty( "low", res_spl[5]);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("hand.json");

			OutputStreamWriter osw = new OutputStreamWriter(fos,"gbk" );
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write( lan1.toString());                  

			bw.close();
			osw.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}    //若此时没有改文件，则自动创建一个空文件
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System. out.println("\nXML格式数据");
		System. out.println(lan1.toString());
	}




}
