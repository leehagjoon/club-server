package com.here.app.comn.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;

@Log4j2
public class HttpUtils {

	public static String getAthnNo(int i) {
		String athnNo = RandomStringUtils.randomNumeric(i);
		return athnNo;
	}
	
	
	public static void clientFileDownload(String filePath, HttpServletResponse response) {
		File file = new File(filePath);

		if (file.exists()) {

			OutputStream outStream = null;
			InputStream inputStream = null;

			try {
				inputStream = new FileInputStream(file);
				response.reset() ;
				response.setContentType("image/jpeg");
				response.setHeader ("Content-Disposition", "attachment; filename=\"" +
						new String(file.getName().getBytes(),"ISO8859_1")+"\"" );
				response.setHeader ("Content-Length", ""+file.length() );
				outStream = response.getOutputStream();
				byte b[] = new byte[(int)file.length()];
				int leng = 0;

				while( (leng = inputStream.read(b)) > 0 ){
					outStream.write(b,0,leng);
				}

			} catch (Exception e) {
				log.error("IGNORE : {}", e);
			} finally {
				try {
					inputStream.close();
					outStream.flush();
					outStream.close();
				} catch (IOException e) {
					log.error("IGNORE : {}", e);
				}
			}
		}
	}

	public static boolean checkServerAlive(String url,String port) {
		boolean isAlive = false;
		HttpURLConnection httpConn = null;
		try {
			URL hostUrl = new URL("http://"+url+":"+port+"/");
			httpConn = (HttpURLConnection) hostUrl.openConnection();

			if (httpConn != null) {
				httpConn.setInstanceFollowRedirects(false);
				httpConn.setRequestMethod("HEAD");
				httpConn.connect();
				isAlive = true;
			}

		} catch (java.net.ConnectException e) {
			log.error("IGNORE : {}", e);
		} catch (MalformedURLException e) {
			log.error("IGNORE : {}", e);
		} catch (IOException e) {
			log.error("IGNORE : {}", e);
		} finally {
			if (httpConn != null) {
				httpConn.disconnect();
			}
		}
		return isAlive;
	}

	public static boolean checkPingAlive(String ip) {
		boolean isAlive = false;
		try {
			InetAddress inet = InetAddress.getByName(ip);

			if (inet.isReachable(2000)) {
				isAlive = true;
			}

		} catch (UnknownHostException e) {
			log.error("IGNORE : {}", e);
		} catch (IOException e) {
			log.error("IGNORE : {}", e);
		}
		return isAlive;
	}

	public static boolean checkUrlAlive(String url,String port, String fullPath) {
		boolean isAlive = false;
		URLConnection urlConn = null;
		URL hostUrl;
		String fullUrl = "";

		if (StringUtils.isEmpty(fullPath)) {
			StringBuffer sb = new StringBuffer("http://");

			if (StringUtils.isEmpty(port)) {
				sb = sb.append(url);
				fullUrl = sb.toString();
			} else {
				sb = sb.append(url).append(":").append(port);
				fullUrl = sb.toString();
			}
		} else {
			fullUrl = fullPath;
		}

		try {
			hostUrl = new URL(fullUrl);
			urlConn = hostUrl.openConnection();
			urlConn.setConnectTimeout(5000);
			urlConn.connect();
			isAlive = true;
		} catch (MalformedURLException e) {
			log.error("IGNORE : {}", e);
		} catch (IOException e) {
			log.error("IGNORE : {}", e);
		}

		return isAlive;
	}
	
	public static void httpPost(String url, String contentType, String body) {
		PostMethod methodPost = new PostMethod(url);
		RequestEntity entity = null;

		try {
			entity = new StringRequestEntity(body,contentType,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
		}

		methodPost.setRequestEntity(entity);
		
		executeMethod(methodPost);
	}

	private static void executeMethod(HttpMethod method) {
		HttpClient client = new HttpClient();

		BufferedReader br = null;

		try {
			int respCd = client.executeMethod(method);

			if (respCd == HttpStatus.SC_OK) {
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					log.info(readLine);
				}
			} else {
				log.error("respCd: {}", respCd);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			method.releaseConnection();

			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					log.error("IGNORE : {}", e);
				}
			}
		}
	}
	
	public static String getClientIpAddr(HttpServletRequest request) {  
		String ip = request.getHeader("X-Forwarded-For");  

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();  
		}

		return ip;
	}	

	
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest();
	}
	
	/**
	 * 클라이언트 IP 를 가져온다
	 * @return 클라이언트 IP
	 */
	public static String getRequestIp() {
		HttpServletRequest req = getCurrentRequest();
		
		String[] HEADER_CLIENT_IP = { "NS-CLIENT-IP", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
		for (String name : HEADER_CLIENT_IP) {
			String clientIp = req.getHeader(name);
			if(!StringUtils.isEmpty(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
				log.debug("{}={}", name, clientIp);
				return clientIp;
			}
		}

		return req.getRemoteAddr();
	}
	
	public static String getIpFirst(String ips) {
		ips = ips.replaceAll(" ", "");
		String[] ipArr = ips.split(",");
		if(ipArr.length == 0) {
			return null;
		}
		return ipArr[0];
	}
	
	public static String getRequestIpFirst() {
		String ip = getRequestIp();
		return getIpFirst(ip);
	}
}
