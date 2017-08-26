package com.jiuyou.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunTaskUtils {

	/** 在只有一个线程的线程池中运行一个Runnable对象 */
	public static boolean runTask(Runnable runnable) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.execute(runnable);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
