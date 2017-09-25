package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.QuickResponse;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.CommonUtil;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class EvaluateActivity extends BaseActivity {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.et_baby_problem)
    EditText et_baby_problem;
    @Bind(R.id.submit_bt)
    Button submit_bt;
    private String order_id;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate);
        ButterKnife.bind(this);
        order_id = getIntent().getStringExtra("order_id");
        Log.e("gy", "order_id" + order_id + "");
        position = getIntent().getIntExtra("position", -1);
        initView();
    }

    private void initView() {
        title_bar_operate_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!et_baby_problem.getText().toString().trim().equals("")) {
                    PopUtil.showDialog(EvaluateActivity.this, "退出确认", "确认取消发布吗？", "取消", "确定退出", null, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EvaluateActivity.this.finish();
                        }
                    });
//                } else {
//                    EvaluateActivity.this.finish();
//                }
            }
        });
        et_baby_problem.addTextChangedListener(new CommonUtil.TextNoEmojiWatcher(et_baby_problem, EvaluateActivity.this));

        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkContent(et_baby_problem.getText().toString().trim())) {
                    AppContext.createRequestApi(HomeApi.class).add_comment(PrefereUtils.getInstance().getToken(), et_baby_problem.getText().toString().trim(), order_id, new Callback<QuickResponse>() {
                        @Override
                        public void success(QuickResponse quickResponse, Response response) {
                            if (quickResponse.getCode() == 200) {
                                ToastUtil.show("评论成功");
                                Intent intent = new Intent();
                                intent.putExtra("position", position);
                                intent.putExtra("status", "11");
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                ToastUtil.show(quickResponse.getMessage());
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {

                        }
                    });
                }
            }
        });
    }

    /**
     * 如果输入内容正常
     */
    public boolean checkContent(String content) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show("提交内容不能为空");
            return false;
        }
        if (content.length() < 0) {
            ToastUtil.show("请检查输入内容");
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (!et_baby_problem.getText().toString().trim().equals("")) {
                PopUtil.showDialog(EvaluateActivity.this, "退出确认", "确认取消发布吗？", "取消", "确定退出", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EvaluateActivity.this.finish();
                    }
                });
//            } else {
//                EvaluateActivity.this.finish();
//            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
