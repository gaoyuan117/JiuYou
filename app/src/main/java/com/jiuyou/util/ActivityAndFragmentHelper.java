package com.jiuyou.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.lang.ref.WeakReference;

public class ActivityAndFragmentHelper {

	private WeakReference<Activity> ref;

	public ActivityAndFragmentHelper(Activity ctx) {
		ref = new WeakReference<Activity>(ctx);

	}

	public void startActivity(Class<?> cls) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			a.startActivity(intent);
			a = null;
		}
	}

	// 跳转界面时带着实现parcel的bean
	public void startActivity(Class<?> cls, String name, Parcelable parcel) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			intent.putExtra(name, parcel);
			a.startActivity(intent);
			a = null;
		}
	}

	// flag=-1 不处理flag
	public void startActivity(Class<?> cls, int flag) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			if (flag != -1)
				intent.addFlags(flag);
			a.startActivity(intent);
			a = null;
		}
	}

	public void startActivity(Class<?> cls, int flag, String key, Bundle bund) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			if (flag != -1)
				intent.addFlags(flag);
			intent.putExtra(key, bund);
			a.startActivity(intent);
			a = null;
		}
	}

	public void startActivity(Class<?> cls, String key, String value) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			intent.putExtra(key, value);
			a.startActivity(intent);
			a = null;
		}
	}

	public void startActivityForResult(Class<?> cls, int reqCode) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			a.startActivityForResult(intent, reqCode);
			a = null;
		}
	}

	// flag=-1 不处理flag
	public void startActivityForResult(Class<?> cls, int flag, int reqCode) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			if (flag != -1)
				intent.addFlags(flag);
			a.startActivityForResult(intent, reqCode);
			a = null;
		}
	}

	public void startActivityForResult(Class<?> cls, int flag, String key,
			Bundle bund, int reqCode) {
		if (!isActivityFinished()) {
			Activity a = ref.get();
			Intent intent = new Intent(a, cls);
			if (flag != -1)
				intent.addFlags(flag);
			intent.putExtra(key, bund);
			a.startActivityForResult(intent, reqCode);
			a = null;
		}
	}

	public boolean isActivityFinished() {
		return !(ref != null && ref.get() != null && !ref.get().isFinishing());
	}

}
