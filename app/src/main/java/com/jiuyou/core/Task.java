package com.jiuyou.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task {

	 
	
	private static ExecutorService ser;
	private static Object obj = new Object();
	//线程个数
	private static final int threadNum = 1;
	
	private  Task(){}
	 
	//创建线程池
	
	public  static void getSingleThread(){
		if(ser==null){
			synchronized(obj){
				ser = Executors.newFixedThreadPool(threadNum);
			}
		}
		//return ser;
	}
	//执行线程
	public static void excuteThread(Runnable run){
		getSingleThread();
		ser.execute(run);
	}
	
}
