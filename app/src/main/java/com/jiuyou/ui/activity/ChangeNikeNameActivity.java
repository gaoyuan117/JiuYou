package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ChangeNikeNameActivity extends BaseActivity {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.edt_nikename)
    EditText edt_nikename;
    @Bind(R.id.btn_finish)
    Button btn_finish;
    String nikename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changenikename);
        ButterKnife.bind(this);
        nikename=getIntent().getStringExtra("nikename");
        initView();
    }
    Editable etext;
    private void initView(){
        title_bar_operate_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNikeNameActivity.this.finish();
            }
        });
        edt_nikename.setText(nikename);
        etext =edt_nikename.getText();
        Selection.setSelection(etext, edt_nikename.getText().length());
        edt_nikename.addTextChangedListener(nikeTextWatch);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交昵称修改
                getLoadingDataBar().show();
                UserUtils.editNikeName(PrefereUtils.getInstance().getToken(), etext.toString(), new UserUtils.getNikeNameListener() {
                    @Override
                    public void load(boolean status, UserResponse info, String message) {
                             if(status){
                                 ToastUtil.show("昵称修改成功！");
                                 setResult(1000);
                                 finish();
                             }else{
                                  ToastUtil.show(message);
                             }
                             closeProgressBar();
                    }
                });
            }
        });
    }
    TextWatcher nikeTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            if (s.length()>0) {
                btn_finish.setEnabled(true);
            } else {
                btn_finish.setEnabled(false);
            }
        }
    };

}
