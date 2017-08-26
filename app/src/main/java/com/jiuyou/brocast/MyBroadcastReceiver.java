package com.jiuyou.brocast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.jiuyou.global.BaseApp;

/**
 * Created by zzh on 2016/5/10.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String jurisdiction = intent.getStringExtra("jurisdiction");
        String nickname = intent.getStringExtra("nickname");
        String imageString =intent.getStringExtra("imageString");
        String token = intent.getStringExtra("token");
        String imei = intent.getStringExtra("imei");
        String douid = intent.getStringExtra("douid");
        String grade = intent.getStringExtra("grade");
        if (!jurisdiction.equals("")) {
            Message msg = Message.obtain();
            msg.obj = jurisdiction;
            msg.what =1;
            BaseApp.handler.sendMessage(msg);
        }
        if (!nickname.equals("")){
            Message msg = Message.obtain();
            msg.obj = nickname;
            msg.what =2;
            BaseApp.handler.sendMessage(msg);
        }
        if (!imageString.equals("")){
            Message msg = Message.obtain();
            msg.obj = imageString;
            msg.what =3;
            BaseApp.handler.sendMessage(msg);
        }
        if (!token.equals("")){
            Message msg = Message.obtain();
            msg.obj = token;
            msg.what =4;
            BaseApp.handler.sendMessage(msg);
        }
        if (!imei.equals("")){
            Message msg = Message.obtain();
            msg.obj = imei;
            msg.what =5;
            BaseApp.handler.sendMessage(msg);
        }
        if (!douid.equals("")){
            Message msg = Message.obtain();
            msg.obj = douid;
            msg.what =6;
            BaseApp.handler.sendMessage(msg);
        }
        if (!grade.equals("")){
            Message msg = Message.obtain();
            msg.obj = grade;
            msg.what =7;
            BaseApp.handler.sendMessage(msg);
        }
    }
}