package com.jiuyou.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jiuyou.global.BaseApp;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * @describe 判断当前网络是否可用
 */
public class NetworkUtil {

	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			android.util.Log.v("error", e.toString());
		}
		return false;
	}

	/**
	 * 检查当前网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					System.out.println(i + "===状态===" + networkInfo[i].getState());
					System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/** 判断网络是否可用 */
	public static boolean isConnected() {
		ConnectivityManager manager = (ConnectivityManager) BaseApp.getContext()
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (mobile.isConnected() || wifi.isConnected())
			return true;
		return false;
	}


	/** 获取字符串，其中包含html网页，json数据 */
	public static String loadString(String htmlUrl) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(htmlUrl);

		String html = "";
		try {
			html = client.execute(get, new BasicResponseHandler());// 这是一个极具拓展性的方法，实现ResponseHandler即可
		} catch (Exception e) {
		}

		return html;
	}

}
