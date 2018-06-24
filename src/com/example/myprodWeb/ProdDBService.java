package com.example.myprodWeb;

import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ProdDBService {
	// ����httpclient get�ύ����
			public static String insertByClientGet(Account account) {
				String sId=String.valueOf(account.getId());
				String sBa=String.valueOf(account.getBalance());
				try {
					//1 ������HttpClient����
					HttpClient client = new DefaultHttpClient();
					//2��ƴװ·��,ע�⽫��������
					String path = "http://10.0.2.2:8080/myProdServ/ProdDBServlet?ACTION="
							+ URLEncoder.encode("0")
							+ "&SID="
							+ URLEncoder.encode(sId)
							+ "&SNAME="
							+ URLEncoder.encode(account.getName())
							+ "&SBA="
							+ URLEncoder.encode(sBa);
					//3��GET��ʽ����
					HttpGet httpGet = new HttpGet(path);
		              //4���õ����������ص�HttpResponse����
					HttpResponse response = client.execute(httpGet);
		              //5���õ�״̬��
					int code = response.getStatusLine().getStatusCode(); 
					if (code == 200) { 
		                   //��ȡ������
						InputStream is = response.getEntity().getContent();
		                    //��������ת�����ַ���
						String text = StreamTools.readInputStream(is);			
			              return text;
					} else {
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			public static String deleteByClientGet(long id) {
				String sId=String.valueOf(id);
				try {
					//1 ������HttpClient����
					HttpClient client = new DefaultHttpClient();
					//2��ƴװ·��,ע�⽫��������
					String path = "http://10.0.2.2:8080/myProdServ/ProdDBServlet?ACTION="
							+ URLEncoder.encode("1")
							+ "&SID="
							+ URLEncoder.encode(sId);
					//3��GET��ʽ����
					HttpGet httpGet = new HttpGet(path);
		              //4���õ����������ص�HttpResponse����
					HttpResponse response = client.execute(httpGet);
		              //5���õ�״̬��
					int code = response.getStatusLine().getStatusCode(); 
					if (code == 200) { 
		                   //��ȡ������
						InputStream is = response.getEntity().getContent();
		                    //��������ת�����ַ���
						String text = StreamTools.readInputStream(is);			
			              return text;
					} else {
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			public static String updateByClientGet(Account account) {
				String sId=String.valueOf(account.getId());
				String sBa=String.valueOf(account.getBalance());
				try {
					//1 ������HttpClient����
					HttpClient client = new DefaultHttpClient();
					//2��ƴװ·��,ע�⽫��������
					String path = "http://10.0.2.2:8080/myProdServ/ProdDBServlet?ACTION="
							+ URLEncoder.encode("2")
							+ "&SID="
							+ URLEncoder.encode(sId)
							+ "&SNAME="
							+ URLEncoder.encode(account.getName())
							+ "&SBA="
							+ URLEncoder.encode(sBa);
					//3��GET��ʽ����
					HttpGet httpGet = new HttpGet(path);
		              //4���õ����������ص�HttpResponse����
					HttpResponse response = client.execute(httpGet);
		              //5���õ�״̬��
					int code = response.getStatusLine().getStatusCode(); 
					if (code == 200) { 
		                   //��ȡ������
						InputStream is = response.getEntity().getContent();
		                    //��������ת�����ַ���
						String text = StreamTools.readInputStream(is);			
			              return text;
					} else {
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
}
