package com.jiuyou.customctrls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;


public class AlertProxy {

	private WeakReference<Activity> ctx;
	private AlertDialog.Builder alertDialog;

	public AlertProxy(Activity ctx) {

		this.ctx = new WeakReference<Activity>(ctx);

	}

	private boolean isCtxNotNull() {

		return this.ctx != null && this.ctx.get() != null
				&& !(((Activity) (this.ctx.get())).isFinishing());
	}

	public void show(String title, String msg, String button1, String button2,
			OnClickListener listernr1, OnClickListener listernr2) {
		if (isCtxNotNull()) {
			alertDialog = new AlertDialog.Builder(this.ctx.get());
			alertDialog.setMessage(msg);
			alertDialog.setTitle(title);
			alertDialog.setPositiveButton("确定", listernr1);
			alertDialog.setNegativeButton("取消", listernr2);
			alertDialog.create().show();
		}
	}

	public void show(String title, String msg, String button1, 
			OnClickListener listernr1) {
		if (isCtxNotNull()) {
			alertDialog = new AlertDialog.Builder(this.ctx.get());
			alertDialog.setMessage(msg);
			alertDialog.setTitle(title);
			alertDialog.setPositiveButton("确定", listernr1);
			//alertDialog.setNegativeButton("取消", listernr2);
			alertDialog.create().show();
		}
	}
	
	
	@SuppressLint("InflateParams")
	public AlertEntity show(int layoutResId){
		AlertEntity entity = new AlertEntity();
		if (isCtxNotNull()) {
		LayoutInflater inflater = (LayoutInflater) this.ctx.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(layoutResId, null);
		alertDialog = new AlertDialog.Builder(this.ctx.get());
		AlertDialog alert = alertDialog.create();
		alert.setContentView(v);
		alert.show();
		entity.alert = alert;
		entity.v = v;
		}
		return entity;
	}
	
	/**
	 * 
	 * @describe 返回Alert的操作参数
	 */
	public class AlertEntity{
		public AlertDialog alert;
		public View v;
	}

}
