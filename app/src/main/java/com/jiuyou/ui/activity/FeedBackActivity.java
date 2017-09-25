package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.CommonUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FeedBackActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.et_comment_input)
    EditText et_comment_input;
    @Bind(R.id.edt_phone)
    EditText edt_phone;
    @Bind(R.id.textView)
    TextView textView;
    String phoneRegex = "^1[435789]\\d{9}$";
    boolean isPhone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title_bar_operate_1.setOnClickListener(this);
        et_comment_input.addTextChangedListener(new CommonUtil.TextNoEmojiWatcher(et_comment_input, FeedBackActivity.this));
        edt_phone.addTextChangedListener(phoneTextWatch);
        textView.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_operate_1:
                FeedBackActivity.this.finish();
                AppConfig.currentTAB = MainActivity.TAB_MINE;
                break;
            case R.id.textView:

                if (checkContent(et_comment_input.getText().toString().trim())) {
                    String phone = edt_phone.getText().toString().trim();
                    if (TextUtils.isEmpty(phone)) {
                        showToastMsg("请输入正确的手机号码");
                        return;
                    }
                    UserUtils.getFeedBask(PrefereUtils.getInstance().getToken(), et_comment_input.getText().toString().trim(), phone, new UserUtils.getFeedBaskListener() {
                        @Override
                        public void load(boolean status, GoodsResponse info, String message) {
                            if (status) {
                                ToastUtil.show("反馈提交成功");
                                finish();
                            }
                        }
                    });
                }
                break;

        }
    }

    TextWatcher phoneTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() == 11 && checkPhone(s.toString())) {
                isPhone = true;
            } else {
                isPhone = false;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * 如果手机号正常的话，返回null
     */
    public boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("手机号不能为空");
            return false;
        }
        if (!phone.matches(phoneRegex)) {
            ToastUtil.show("手机号格式不对");
            return false;
        }
        return true;
    }

    /**
     * 如果输入内容正常
     */
    public boolean checkContent(String content) {
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show("提交内容不能为空");
            return false;
        }
        if (content.length() < 10 || content.length() > 300) {
            ToastUtil.show("限10-300字");
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
            FeedBackActivity.this.finish();
            AppConfig.currentTAB = MainActivity.TAB_MINE;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
