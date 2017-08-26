package com.jiuyou.customctrls;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.jiuyou.R;


public class ProgressDialog extends Dialog {
    private Context context = null;


    public ProgressDialog(Context context) {
        super(context);
        this.context = context;

    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;


    }


    public ProgressDialog createDialog() {
        ProgressDialog customProgressDialog = new ProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.progress_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        showText = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        return customProgressDialog;
    }

//

    /**
     * [Summary]  public ProgressDialog setText(String text){
     //    	showText.setText(text);
     //    	return customProgressDialog;
     //    }
     * setTitile ????
     *
     * @param strTitle
     * @return
     */
//    public ProgressDialog setTitile(String strTitle) {
//        return customProgressDialog;
//    }


}
