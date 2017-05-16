package at.ltd.api;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class VultrRequest {

	public VultrRequest(String key) {
		this.key = key;
	}

	private String key;

	/**
	 * URL= https://api.vultr.com/v1/ + url PARM = DCID=9&VPSPLANID=201&OSID=193
	 * PARM= &
	 * 
	 * @param url
	 */
	public synchronized VultrRequestResult sendReq(String url, String parm) {
		check();
		try {

			String urlParameters;
			byte[] postData = null;
			int postDataLength = 0;
			if (parm != null) {
				urlParameters = parm;
				postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				postDataLength = postData.length;
			}
			String request = "https://api.vultr.com/v1/" + url;
			URL curl = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) curl.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			if (parm != null) {
				conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			}
			conn.addRequestProperty("User-Agent", "Mozilla/4.76");
			if (key != null) {
				conn.addRequestProperty("API-Key", key);
			}
			conn.setUseCaches(false);

			if (parm != null) {
				try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
					wr.write(postData);
				}
			}

			int code = conn.getResponseCode();
			String res = null;
			if (code > 400 || code == 400) {
				try {
					res = convertStreamToString(conn.getErrorStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					res = convertStreamToString(conn.getInputStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return new VultrRequestResult(conn.getResponseMessage(), code, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * URL= https://api.vultr.com/v1/ + url PARM = DCID=9&VPSPLANID=201&OSID=193
	 * PARM= &
	 * 
	 * @param url
	 */
	public synchronized VultrRequestResult sendReqS(String url, Object... parm) {
		check();
		try {

			String urlParameters;
			byte[] postData = null;
			int postDataLength = 0;
			if (parm != null) {
				urlParameters = buildParm(parm);
				postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				postDataLength = postData.length;
			}
			String request = "https://api.vultr.com/v1/" + url;
			URL curl = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) curl.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			if (parm != null) {
				conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			}
			conn.addRequestProperty("User-Agent", "Mozilla/4.76");
			if (key != null) {
				conn.addRequestProperty("API-Key", key);
			}
			conn.setUseCaches(false);

			if (parm != null) {
				try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
					wr.write(postData);
				}
			}

			int code = conn.getResponseCode();
			String res = null;
			if (code > 400 || code == 400) {
				try {
					res = convertStreamToString(conn.getErrorStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					res = convertStreamToString(conn.getInputStream());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return new VultrRequestResult(conn.getResponseMessage(), code, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static String buildParm(Object... parms) {
		String key = "";
		if ((parms.length % 2) != 0) {
			throw new IllegalArgumentException("Parms have to be even.");
		}
		for (int i = 0; i < parms.length; i++) {
			String first = parms[i].toString();
			i++;
			String sec = parms[i].toString();
			if (i == 1) {
				key += first + "=" + sec;
			} else {
				key += "&" + first + "=" + sec;
			}

		}
		return key;
	}

	public static Long last;
	public static synchronized void check(){
		if(last == null){
			last = System.currentTimeMillis();
		}else{
			while((System.currentTimeMillis() - last) < 1055){
				try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			last = System.currentTimeMillis();
			
		}
		
	}

}
