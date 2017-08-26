package com.jiuyou.core.task;

public abstract class DoInBackground<Result> implements Runnable {

	private CallBackOnUi<Result> callback;

	public void setCallBack(CallBackOnUi<Result> c)
	{
		this.callback = c;
	}
	
	public DoInBackground() {
		
	}

	@Override
	public final void run() {
		// TODO Auto-generated method stub

		Result res = execute();
		if (callback != null) {

			CallBackOnUiRunnable<Result> c = new CallBackOnUiRunnable<Result>(
					res) {

				@Override
				public void execute(Result res) {
					// TODO Auto-generated method stub
					if (callback != null)
						callback.exceute(res);
				}
			};
			UIThread.execute(c);
		}

	}

	// 孩子类执行
	public abstract Result execute();

}
