package com.jiuyou.core;

import android.os.Handler;
import android.os.Looper;

public abstract class BaseRunnable<Result> implements Runnable {

	private CallBackOnUi<Result> callback;

	public BaseRunnable(CallBackOnUi<Result> c) {
		this.callback = c;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Result res = execute();
		if (callback != null) {
			Handler handler = new Handler(Looper.getMainLooper());

			CallBackOnUiRunnable<Result> c = new CallBackOnUiRunnable<Result>(
					res) {

				@Override
				public void execute(Result res) {
					// TODO Auto-generated method stub
					if (callback != null)
						callback.exceute(res);
				}
			};
			handler.post(c);
		}

	}

	// 孩子类执行
	public abstract Result execute();

}
