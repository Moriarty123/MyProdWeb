package com.example.myprodWeb;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class GetBackService {

	// ʹ��HttpURLConnection GET��ʽ�ύ����
	public static String GetBackByGet(String username) {
		try {
			// ƴװURL ע��Ϊ�˷�ֹ���� ������Ҫ���������б���
			String path = "http://10.0.2.2:8080/myProdServ/GetBackServlet?NAME="
					+ URLEncoder.encode(username, "UTF-8");

			// ����URLʵ��
			URL url = new URL(path);
			// ��ȡHttpURLConnection����
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000); // ���ó�ʱʱ��
			conn.setRequestMethod("GET"); // ���÷��ʷ�ʽ
			int code = conn.getResponseCode(); // �õ����ص�״̬��
			if (code == 200) { // ����ɹ�
				InputStream is = conn.getInputStream();
				String text = StreamTools.readInputStream(is);
				System.out.println("text:"+text);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ʹ��HttpURLConnection POST��ʽ�ύ����
	public static String GetBackByPost(String username) {
		try {
			// Ҫ���ʵ���Դ·��
			String path = "http://10.0.2.2:8080/myProdServ/GetBackServlet";
			// ����URL��ʵ��
			URL url = new URL(path);
			// ��ȡHttpURLConnection����
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// ���ó�ʱʱ��
			conn.setConnectTimeout(5000);
			// ָ������ʽ
			conn.setRequestMethod("POST");
			// ׼������ ����������
			String data = "NAME=" + URLEncoder.encode(username);
			// ��������ͷ
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", data.length() + "");
			// ������д��������
			conn.setDoOutput(true);
			// �õ������
			OutputStream os = conn.getOutputStream();
			os.write(data.getBytes()); // ������д���������
			int code = conn.getResponseCode(); // �ǵ����������ص�״̬��
			if (code == 200) {
				// �õ����������ص�������
				InputStream is = conn.getInputStream();
				// ��������ת�����ַ���
				String text = StreamTools.readInputStream(is);
				System.out.println("text:"+text);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ����httpclient get�ύ����
	public static String GetBackByClientGet(String username) {
		try {
			// 1 ������HttpClient����
			HttpClient client = new DefaultHttpClient();
			// 2��ƴװ·��,ע�⽫��������
			String path = "http://10.0.2.2:8080/myProdServ/GetBackServlet?NAME="
					+ URLEncoder.encode(username);
			// 3��GET��ʽ����
			HttpGet httpGet = new HttpGet(path);
			// 4���õ����������ص�HttpResponse����
			HttpResponse response = client.execute(httpGet);
			// 5���õ�״̬��
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				// ��ȡ������
				InputStream is = response.getEntity().getContent();
				// ��������ת�����ַ���
				String text = StreamTools.readInputStream(is);
				System.out.println("text:"+text);
				return text;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ����httpclient post�ύ����
	public static String GetBackByClientPost(String username) {
		try {
			// 1����ȡHttpClient����
			HttpClient client = new DefaultHttpClient();
			// 2��ָ�����ʵ�ַ
			String path = "http://10.0.2.2:8080/myProdServ/GetBackServlet";
			// 3��POST��ʽ��������
			HttpPost httpPost = new HttpPost(path);
			// 4��ָ��Ҫ�ύ������ʵ��
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("NAME", username));
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			// 5��������������õ����������ص���Ϣ
			HttpResponse response = client.execute(httpPost);
			int code = response.getStatusLine().getStatusCode(); // �õ�״̬��
			if (code == 200) { // ���ʳɹ�
				InputStream is = response.getEntity().getContent();
				// ��������ת�����ַ���
				String text = StreamTools.readInputStream(is);
				System.out.println("text:"+text);
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
