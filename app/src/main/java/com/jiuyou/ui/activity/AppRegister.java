package com.jiuyou.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiuyou.global.AppConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);

		api.registerApp(AppConfig.MXWechatAPPID);
	}
}
