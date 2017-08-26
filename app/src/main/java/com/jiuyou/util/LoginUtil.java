package com.jiuyou.util;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by wisely on 2016/5/16.
 */
public class LoginUtil {

    private static PopupWindow pop;
    public static void showLogin(final Activity activity, final Class cls){
        pop = PopUtil.showLoginDialog(activity, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                toNext(activity,cls);
                closePop(activity);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
//                ToastUtil.show("取消");
                closePop(activity);
            }
        });
    }

    private static void closePop(Activity activity) {
        if (pop != null) {
            pop.dismiss();
            pop = null;
            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
            params.alpha = 1f;
            activity.getWindow().setAttributes(params);
        }
    }

    private static void toNext(Activity activity, Class cls){
//        activity.startActivity(new Intent(activity,cls));
        activity.startActivityForResult(new Intent(activity,cls),100);
        
    }
}
