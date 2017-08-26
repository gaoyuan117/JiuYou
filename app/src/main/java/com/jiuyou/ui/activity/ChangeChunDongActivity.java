package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.text.Editable;
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


public class ChangeChunDongActivity extends BaseActivity {
    @Bind(R.id.title_bar_operate_1)
    ImageView title_bar_operate_1;
    @Bind(R.id.edt_chundong)
    EditText edt_chundong;
    @Bind(R.id.btn_save)
    Button btn_save;
    String chundong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changechundong);
        chundong=getIntent().getStringExtra("chundong");
        ButterKnife.bind(this);
        initView();
    }
    Editable etext;

    private void initView(){
        edt_chundong.setText(chundong);
        title_bar_operate_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeChunDongActivity.this.finish();
            }
        });
        edt_chundong.addTextChangedListener(nikeTextWatch);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UserUtils.editSetName(PrefereUtils.getInstance().getToken(), edt_chundong.getText().toString(), new UserUtils.setNameListener() {
                   @Override
                   public void load(boolean status, UserResponse info, String message) {
                       if(status){
                            ToastUtil.show("春东号设置成功！");
                            setResult(1000);
                            finish();
                            PrefereUtils.getInstance().fixChunDongStatus(true);
                       }else{
                           ToastUtil.show(message);
                       }
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
                btn_save.setEnabled(true);
            } else {
                btn_save.setEnabled(false);
            }
        }
    };

}
