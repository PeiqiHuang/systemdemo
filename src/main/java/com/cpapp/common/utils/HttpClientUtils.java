package com.cpapp.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cpapp.common.constants.SysConfig;

/*******************************************************************************
 * Http 请求工具类
 * 
 * @author zengxiangtao
 * @version 2013-07-01
 ******************************************************************************/
public class HttpClientUtils {

	protected HttpClientUtils() {

	}

	/*** Apache HttpClient get请求 */
	public static String sendApacheGetRequst(String urlStr) {
		HttpEntity entity = null;
		try {
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse response = httpClient.execute(new HttpGet(urlStr));
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity,
							SysConfig.SYS_CHARTSET);
					return result;
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != entity) {
				try {
					EntityUtils.consume(entity);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/*** Apache HttpClient post请求 */
	public static String doPost(String apiUrl, Map<String, String> params) {
		String result = null;
		HttpEntity entity = null;
		try {
			// 创建HttpClientBuilder
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(apiUrl);
			// httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(
					params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(),
						entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset
					.forName(SysConfig.SYS_CHARTSET)));
			HttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity,
							SysConfig.SYS_CHARTSET);
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != entity) {
				try {
					EntityUtils.consume(entity);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
