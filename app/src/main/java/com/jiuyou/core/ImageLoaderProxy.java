package com.jiuyou.core;

import java.io.File;

import android.widget.ImageView;

import com.jiuyou.global.BaseApp;
import com.sheyuan.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.sheyuan.universalimageloader.core.DisplayImageOptions;
import com.sheyuan.universalimageloader.core.ImageLoaderConfiguration;
import com.sheyuan.universalimageloader.core.assist.QueueProcessingType;
import com.sheyuan.universalimageloader.core.decode.BaseImageDecoder;
import com.sheyuan.universalimageloader.core.download.BaseImageDownloader;
import com.sheyuan.universalimageloader.core.listener.ImageLoadingListener;
import com.sheyuan.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.sheyuan.universalimageloader.utils.StorageUtils;
import com.sheyuan.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.sheyuan.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;

public class ImageLoaderProxy {

	private static ImageLoaderProxy imageLoader;

	private static com.sheyuan.universalimageloader.core.ImageLoader imageLoaderImp = null;

	private ImageLoaderProxy() {

		// init();

	} 
	
	public void displayImage(String uri, ImageView imageView) {
		imageLoaderImp.displayImage(uri,imageView);
	}
	
	public void displayImage(String uri, ImageView imageView, DisplayImageOptions options) {
		imageLoaderImp.displayImage(uri,imageView,options);
	}
	public void displayImage(String uri, ImageView imageView, ImageLoadingListener listener) {
		imageLoaderImp.displayImage(uri,imageView,listener);
	}
	
	public void displayImage(String uri, ImageView imageView, DisplayImageOptions options,
			ImageLoadingListener listener) {
		imageLoaderImp.displayImage(uri,imageView,options,listener);
	}
	
	public void displayImage(String uri, ImageView imageView, DisplayImageOptions options,
			ImageLoadingListener listener, ImageLoadingProgressListener progressListener) {
		imageLoaderImp.displayImage(uri,imageView,options,listener,progressListener);
	}

	public void loadImage(String uri, ImageLoadingListener listener) {
		imageLoaderImp.loadImage(uri,listener);
	}
	public static ImageLoaderProxy getInstance() {

		if (imageLoader == null||imageLoaderImp==null) {
			File cacheDir = StorageUtils.getCacheDirectory(BaseApp
					.getApplication()); // 缓存文件夹路径
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					BaseApp.getApplication())
					//.memoryCacheExtraOptions(480, 800)
					// default = device screen dimensions 内存缓存文件的最大长宽
					// .diskCacheExtraOptions(480, 800, null)
					// 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个

					.threadPoolSize(2)
					// default 线程池内加载的数量
					.threadPriority(Thread.NORM_PRIORITY - 2)
					// default 设置当前线程的优先级
					.tasksProcessingOrder(QueueProcessingType.FIFO)
					// default
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
					// 可以通过自己的内存缓存实现
					.memoryCacheSize(2 * 1024 * 1024)
					// 内存缓存的最大值
					.memoryCacheSizePercentage(13)
					// default
					.diskCache(new LimitedAgeDiskCache(cacheDir,24*60*60))
					// default 可以自定义缓存路径
					.diskCacheSize(50 * 1024 * 1024)
					// 50 Mb sd卡(本地)缓存的最大值
					//.diskCacheFileCount(100)
					// 可以缓存的文件数量
					// default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new
					// Md5FileNameGenerator())加密
					.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
					.imageDownloader(
							new BaseImageDownloader(BaseApp.getApplication())) // default
					.imageDecoder(new BaseImageDecoder(false)) // default
					.defaultDisplayImageOptions(
							DisplayImageOptions.createSimple()) // default
					// .writeDebugLogs() // 打印debug log
					.build(); // 开始构建

			com.sheyuan.universalimageloader.core.ImageLoader.getInstance()
					.init(config);

			imageLoaderImp = com.sheyuan.universalimageloader.core.ImageLoader
					.getInstance();
			imageLoader = new ImageLoaderProxy();
		}

		return imageLoader;

	}

}
