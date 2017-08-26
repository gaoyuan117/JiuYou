package com.jiuyou.core;


// app上下文，负责承载核心业务对象 
public class AppContext {

	private static ServerReqFactory serverReqFactory;
	private static AppContext instance = null;
	private static ImageLoaderProxy imageLoader = null;

	public static AppContext getInstance() {
		synchronized (AppContext.class) {
			if (instance == null)
				instance = new AppContext();
		}
		return instance;
	}

	private AppContext() {
		init();

	}

	public void init() {
		if (serverReqFactory == null) {
			serverReqFactory = new ServerReqFactory();
		}
		if (imageLoader == null) {
			imageLoader = ImageLoaderProxy.getInstance();
		}
	}

	// 获取服务请求工厂
	public ServerReqFactory getServerReqFactory() {
		return serverReqFactory;
	}

	// 图片加载器,三级缓存机制
	public ImageLoaderProxy getImageLoader() {
		return imageLoader;
	}

	// 执行一些耗时任务
	public void executeTask(BaseRunnable r) {

		Task.excuteThread(r);

	}
	/** 生成一个访问网络的接口的实例对象 */
	public static <T> T createRequestApi(Class<T> cls) {
		return getInstance().getServerReqFactory().createRequestApi(cls);
	}
	/** 获取ImageLoader的实例对象 */
	public static ImageLoaderProxy getImageLoaderProxy(){
		return getInstance().getImageLoader();
	}
}
