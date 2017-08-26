package com.jiuyou.core.task;

public abstract class CallBackOnUiRunnable<Result> implements Runnable {
	// public void callBackOnui(Result res);
	Result res;

	public CallBackOnUiRunnable(Result res) {
		this.res = res;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		execute(res);

	}

	// 孩子类执行
	public abstract void execute(Result res);

}
