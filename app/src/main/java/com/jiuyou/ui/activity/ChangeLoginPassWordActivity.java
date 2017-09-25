package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jiuyou.R;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.MD5Utils;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ChangeLoginPassWordActivity extends BaseActivity {
     @Bind(R.id.edt_old_pwd)
     EditText edt_old_pwd;
    @Bind(R.id.del_pwd)
    ImageView del_pwd;
    @Bind(R.id.edt_new_pwd2)
    EditText edt_new_pwd2;
    @Bind(R.id.del_pwd2)
    ImageView del_pwd2;
    @Bind(R.id.edt_new_pwd3)
    EditText edt_new_pwd3;
    @Bind(R.id.del_pwd3)
    ImageView del_pwd3;
    @Bind(R.id.btn_register)
    Button btn_register;
    private String new_pwd1;
    private String new_pwd2;
    private String old_pwd;
    private String title;
    private boolean isPay;
    boolean isoldPwd;
    boolean isnewPwd1;
    boolean isnewPwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_pay_change);
        ButterKnife.bind(this);
        title=getIntent().getStringExtra("status");
        if(title.equals("pay")){
            isPay=true;
            setTitle("修改支付密码");
            edt_old_pwd.setHint("请输入旧密码（6位）");

            edt_old_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
            edt_new_pwd2.setHint("请输入新密码（6位）");
            edt_new_pwd2.setInputType(InputType.TYPE_CLASS_NUMBER);
            edt_new_pwd3.setHint("请再次输入新密码（6位）");
            edt_new_pwd3.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            isPay=false;
            setTitle("修改登录密码");
        }
        setAllClick();
    }

    public void exit(View view) {
        ChangeLoginPassWordActivity.this.finish();
    }
    public void deloldPwd(View view) {
        edt_old_pwd.setText("");
    }
    public void delnewPwd1(View view) {
        edt_new_pwd2.setText("");
    }
    public void delnewPwd2(View view) {
        edt_new_pwd3.setText("");
    }

    private void setAllClick(){
        edt_old_pwd.addTextChangedListener(pwdTextWatch);
        edt_new_pwd2.addTextChangedListener(pwdTextWatch2);
        edt_new_pwd3.addTextChangedListener(pwdTextWatch3);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(isPay){
                      getLoadingDataBar();
                 //修改支付密码
                      if(CheckInput()){
                      UserUtils.setPayInfo(PrefereUtils.getInstance().getToken(),new_pwd1, new UserUtils.setPayListener() {
                          @Override
                          public void load(boolean status, UserResponse info, String message) {
                              if(status){
                                       ToastUtil.show("支付密码重置成功");
                                       ChangeLoginPassWordActivity.this.finish();
                              }else{
                                  ToastUtil.show(message);
                                  ChangeLoginPassWordActivity.this.finish();
                              }
                              closeProgressBar();
                          }
                      });
                      }

                  }else{
                //修改登录密码
                    if(CheckInput()){
                        String token=PrefereUtils.getInstance().getToken();
                        String oldpwd=MD5Utils.toMD5(old_pwd.trim());
                       String new1= MD5Utils.toMD5(new_pwd1.trim());
                        String new2=MD5Utils.toMD5(new_pwd2.trim());
                        Log.e("tgh","token="+token+"oldpwd="+oldpwd+"newpwd="+new1+"repwd="+new2);

                        UserUtils.setLoginInfo(token,oldpwd , new1,new2 , new UserUtils.setLoginListener() {
                         @Override
                         public void load(boolean status, UserResponse info, String message) {
                             if(status){
                                 ToastUtil.show("登录密码重置成功");
                                 ChangeLoginPassWordActivity.this.finish();
                             }else{
                                 ToastUtil.show(message);
                                 ChangeLoginPassWordActivity.this.finish();
                             }
                             closeProgressBar();
                         }
                     });
                    }
                  }
            }
        });
    }



    TextWatcher pwdTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() >= 1) {
                isoldPwd = true;
            } else {
                isoldPwd = false;
            }

            if (s.toString().length() > 0) {
                del_pwd.setVisibility(View.GONE);
            } else {
                del_pwd.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            setResetBtnStatus();
        }
    };
    TextWatcher pwdTextWatch2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() >= 1) {
                isnewPwd1 = true;
            } else {
                isnewPwd1 = false;
            }

            if (s.toString().length() > 0) {
                del_pwd2.setVisibility(View.GONE);
            } else {
                del_pwd2.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            setResetBtnStatus();
        }
    };
    TextWatcher pwdTextWatch3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() >= 1) {
                isnewPwd2 = true;
            } else {
                isnewPwd2 = false;
            }

            if (s.toString().length() > 0) {
                del_pwd3.setVisibility(View.GONE);
            } else {
                del_pwd3.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            setResetBtnStatus();
        }
    };
    private void setResetBtnStatus() {
        if (isoldPwd && isnewPwd1&&isnewPwd2) {
            btn_register.setEnabled(true);
        } else {
            btn_register.setEnabled(false);
        }
    }





    private boolean CheckInput() {
        old_pwd =edt_old_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(old_pwd)) {
            ToastUtil.show("旧密码不能为空");
            return false;
        }

        if (old_pwd.length() <= 5) {
            ToastUtil.show("旧密码不能少于6位");
            return false;
        }
        new_pwd1 =edt_new_pwd2.getText().toString().trim();
        if (TextUtils.isEmpty(new_pwd1)) {
            ToastUtil.show("新密码不能为空");
            return false;
        }
        if (new_pwd1.length() <= 5) {
            ToastUtil.show("新密码不能少于6位");
            return false;
        }
        new_pwd2 =edt_new_pwd3.getText().toString().trim();
        if (TextUtils.isEmpty(new_pwd2)) {
            ToastUtil.show("再次输入新密码不能为空");
            return false;
        }
        if (new_pwd2.length() <= 5) {
            ToastUtil.show("再次输入新密码不能少于6位");
            return false;
        }
        if(!new_pwd1.equals(new_pwd2)){
            ToastUtil.show("两次输入的密码不相同");
            return false;
        }
        return true;
    }

}
