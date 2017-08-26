package com.jiuyou.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageService {
	public static byte[] getImage(String path) throws IOException {
//		System.out.println("ImageService path=" + path);// http://wbq896990.oss-cn-hangzhou.aliyuncs.com/1407384531451
		// http://192.168.168.101/res/1406627342150.jpg 个人图像
		//  /mnt/sdcard/vikaa/image/IMG_20140413_191256.jpg

		

		URL url = new URL(path);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET"); // 设置请求方法为GET
		conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
		// android.os.NetworkOnMainThreadException
		InputStream inputStream = conn.getInputStream(); // ---通过输入流获得图片数据

		byte[] data = StreamTool.readInputStream(inputStream); // 获得图片的二进制数据

		return data;

	}
}
