package com.jiuyou.customctrls;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wisely on 2016/3/24.
 * 通过selector选择器来改变背景，其中倒计时运行时为android:state_enabled="true"，不显示倒计时时为android:state_enabled="false";
 */
public class CountDownView extends TextView {

    public interface OnCancelListener{
        void cancel();
    }

    private long totalMills = 3 * 1000;//倒计时的总时间，根据需要更改这个值
    private long interval = 1000;//倒计时的时间间隔

    public CountDownView(Context context) {
        super(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    OnCancelListener listener;
    public void setOnCancelListener(OnCancelListener listener){
        this.listener = listener;
    }

    private TimeCount mTimeCount;

    private void startCount(long totalMills, long countDownInterval) {
        if (mTimeCount == null)
            mTimeCount = new TimeCount(totalMills, countDownInterval);
        mTimeCount.start();
    }

    public void start(){
        defaultText = getText().toString();
        startCount(totalMills, interval);
    }

    public void cancel() {
        if (mTimeCount != null){
            mTimeCount.onFinish();
            mTimeCount.cancel();
        }
    }

    String defaultText = "";//获取到在点击之前的文本内容

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
//            setEnabled(false);
            setText("跳过  "+(millisUntilFinished / 1000) +"s");
        }

        @Override
        public void onFinish() {
//            setEnabled(true);
            setText("跳过  "+0 +"s");
            if(listener != null){
                listener.cancel();
            }
        }

    }

}
