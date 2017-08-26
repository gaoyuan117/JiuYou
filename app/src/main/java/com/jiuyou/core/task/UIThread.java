package com.jiuyou.core.task;

import android.os.Handler;
import android.os.Looper;

// callback在主线程执行
public class UIThread {

	private static Handler handler;
	private static Object obj = new Object();

	public static void init() {
		if (handler == null) {
			synchronized (obj) {
				if (handler == null) {
					handler = new Handler(Looper.getMainLooper());
				}
			}
		}

	}

	public static void execute(Runnable runable) {
		init();
		if (handler != null)
			handler.post(runable);
	}

	public static void execute(Runnable runable, long delayMillis) {
		init();
		if (handler != null)
			handler.post(runable);
	}

}
