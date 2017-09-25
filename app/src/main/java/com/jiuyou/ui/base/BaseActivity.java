package com.jiuyou.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.core.ActivitysManager;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.ProgressDialog;
import com.jiuyou.global.AppManager;
import com.jiuyou.network.response.JZBResponse.UserInfo;
import com.jiuyou.util.ActivityAndFragmentHelper;
import com.jiuyou.util.PrefereUtils;

import com.jiuyou.R;

public class BaseActivity extends Activity {


    public String mPageName;
    public ProgressDialog ldb;
    private ActivityAndFragmentHelper activityHelper = null;
    public static int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ldb = new com.jiuyou.customctrls.ProgressDialog(this).createDialog();
        ActivitysManager.getInstance().addActivity(this);
        activityHelper = new ActivityAndFragmentHelper(this);
        mPageName = this.getClass().getSimpleName();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

//		updateMsgBox();
    }

    public void saveUserInfo(String phone,String token,String uid){

        PrefereUtils.getInstance().saveLastPhone(phone);
        PrefereUtils.getInstance().saveToken(token);
        PrefereUtils.getInstance().saveUid(uid);
        PrefereUtils.getInstance().fixLoginStatus(true);
    }
    public void saveRegisterUser(String phone,String token,String pwd){
        PrefereUtils.getInstance().saveLastPhone(phone);
        PrefereUtils.getInstance().saveToken(token);
        PrefereUtils.getInstance().savePwd(pwd);
    }
    public void setTitle(String str) {
        try {
            ((TextView) findViewById(R.id.title)).setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void upDateUserStatus(UserInfo user) {
        PrefereUtils.getInstance().saveUser(user);
    }

    // 加载进度条
    public final ProgressDialog getLoadingDataBar() {
        return ldb;
    }

    // 打开Activity
    public final ActivityAndFragmentHelper getActivityAndFragmentHelper() {
        return activityHelper;
    }

    /**
     * 跳转到下一个activity中
     */
    public void toNext(Class<?> clazz) {
        activityHelper.startActivity(clazz);

    }

    public final <E> E getApi(Class<E> clz) {
        return AppContext.getInstance().getServerReqFactory()
                .createRequestApi(clz);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        ldb.cancel();
        ldb = null;
        activityHelper = null;
        super.onDestroy();
    }

    public void closeProgressBar() {
        if (null != ldb && ldb.isShowing()) {
            ldb.cancel();
        }
    }

    /**
     * 科目选择回调接口
     */
    public interface OnSubjectListener {
        /**
         * 点击科目操作
         */
        void onSubjectClick(String currentSubject);

    }

    /**
     * 播放是否收藏回调接口
     */
    public interface OnCollectionListener {
        /**
         * 是否收藏操作
         */
        void onCurrentCollect(boolean isCollect);

    }

    /**
     * 设置title
     */
    protected void setLeftTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
        goBack();
    }

    protected void setCenterTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.center_title_tv);
        tvTitle.setText(title);
		 goBack();
    }

    protected TextView setRightTitle(String title) {
        if (title == null) {
            return null;
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_right_text);
        tvTitle.setText(title);
        return tvTitle;
    }

    protected void setImg_right2(int id) {
        if (id == 0) {
            return;
        }
        ImageView ImageView = (android.widget.ImageView) findViewById(R.id.iv_right2);
        ImageView.setVisibility(View.VISIBLE);
        ImageView.setImageResource(id);
    }

    protected ImageView setImg_right(int id) {
        if (id == 0) {
            return null;
        }
        ImageView ImageView = (android.widget.ImageView) findViewById(R.id.img_right);
        ImageView.setVisibility(View.VISIBLE);
        ImageView.setImageResource(id);
        return ImageView;
    }

    /**
     * 设置布局
     */
    protected void goBack() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void visibilityExit() {
        findViewById(R.id.exit_layout).setVisibility(View.GONE);
    }

    /**
     * 土司
     */
    protected void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



}
