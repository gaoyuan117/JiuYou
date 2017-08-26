package com.jiuyou.util;

import android.widget.Toast;

import com.jiuyou.global.BaseApp;

public class ToastUtil {
	public static Toast toast=null;
	
	public static  void show(String str){
		if(toast==null){
			toast=Toast.makeText(BaseApp.getApplication(), str, Toast.LENGTH_SHORT);
		}else{
			toast.setText(str);
		}
		toast.show();
	}
}
