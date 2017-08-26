package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.CheckPhoneAndPwd;
import com.jiuyou.util.MD5Utils;
import com.jiuyou.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ForgetPWDActivity extends BaseActivity {
     @Bind(R.id.edt_pay_phone)
     EditText edt_register_phone;
    @Bind(R.id.tip_number)
    TextView tip_number;
    @Bind(R.id.edt_pay_yz)
    EditText edt_register_yz;
    @Bind(R.id.edt_new_pwd)
    EditText edt_reset_pwd;
    @Bind(R.id.btn_register)
    Button btn_register;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.edt_new_pwd2)
    EditText edt_reset_pwd2;
    @Bind(R.id.del_pwd2)
    ImageView del_pwd2;
    @Bind(R.id.del_pwd)
    ImageView del_pwd;
    @Bind(R.id.del_phone)
    ImageView del_phone;

    private String phone, code;
    Timer timer;
    public static int TIMER = 1;
    private int total = 59;
    private String verity;
    private String type = "register";
    boolean isPhone = false, isPwd = false;
    CheckPhoneAndPwd checkPhoneAndPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_forget);
        ButterKnife.bind(this);
        setTitle("找回密码");
        setAllClick();
    }

    protected void setAllClick() {

        del_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_register_phone.setText("");
            }
        });
        del_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_reset_pwd.setText("");
            }
        });
        del_pwd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_reset_pwd2.setText("");
            }
        });
        checkPhoneAndPwd = new CheckPhoneAndPwd();
        edt_register_phone.addTextChangedListener(phoneTextWatch);
        edt_reset_pwd.addTextChangedListener(pwdTextWatch);
        edt_reset_pwd2.addTextChangedListener(pwdTextWatch2);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tip_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edt_register_phone.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                    ToastUtil.show("请输入正确的手机号码");
                    return;
                         }
                        stratNetWork();
                    }
                });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInput()) {
                    //密碼重置
                    getLoadingDataBar().show();
                    Log.e(AppConfig.TAG,"resertword==="+MD5Utils.md5(pwd1.toString().trim().getBytes()));
                    UserUtils.getResetPWD(MD5Utils.md5(pwd1.toString().trim().getBytes()), MD5Utils.md5(pwd2.toString().trim().getBytes()), phone, code, new UserUtils.getResetPWDListener() {
                        @Override
                        public void load(boolean status, GoodsResponse info, String message) {
                                if(status){
                                    ToastUtil.show("密码重置成功");
                                    ForgetPWDActivity.this.finish();
                                }else{
                                    ToastUtil.show(message);
                                }
                                closeProgressBar();
                        }
                    });


                    if (!TextUtils.isEmpty(verity)  ) {
                        if (verity.equals(edt_register_yz.getText().toString())) {
                            startActivity();
                        }else{
                            Toast.makeText(ForgetPWDActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ForgetPWDActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    TextWatcher phoneTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (s.toString().length() == 11 && checkPhone(s.toString())) {
                isPhone = true;
            } else {
                isPhone = false;
            }
            if (s.toString().length() > 0) {
                del_phone.setVisibility(View.GONE);
            } else {
                del_phone.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            if (checkPhoneAndPwd.checkIsPhone(s)) {
                btn_register.setEnabled(true);
            } else {
                btn_register.setEnabled(false);
                if (s.length() == 11) {
                    ToastUtil.show("请输入正确的手机号");
                }
            }
        }
    };
    TextWatcher pwdTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().length() >= 1) {
                isPwd = true;
            } else {
                isPwd = false;
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
                isPwd = true;
            } else {
                isPwd = false;
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
    private void setResetBtnStatus() {
        if (isPhone && isPwd) {
            btn_register.setEnabled(true);
        } else {
            btn_register.setEnabled(false);
        }
    }
    private void stratNetWork() {
        getLoadingDataBar().show();
        UserUtils.getVerifyInfo(phone, AppConfig.FORGOTTEN, new UserUtils.getLoginListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if(status){
                    ToastUtil.show("验证码已经发送");
                    startTimer();
                    verity=info.getData().getVerify();
                    Log.e(AppConfig.TAG, "verity===="+verity);
                }else{
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TIMER);
            }
        }, 1000, 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TIMER) {
                if (total <= 0) {
                    total = 59;
                    tip_number.setEnabled(true);
                    tip_number.setTextColor(getResources().getColor(R.color.xn_red));
                    tip_number.setText("重新发送");
                    timer.cancel();
                } else {
                    tip_number.setEnabled(false);
                    tip_number.setTextColor(getResources().getColor(R.color.hint_color));
                    total--;
                    tip_number.setText(total+"秒后重试" );
                }
            }
        }
    };
    private void startActivity() {
//        Intent intent = new Intent(RegisterActivity.this, SetPasswordActivity.class);
//        intent.putExtra("phone", phone);
//        intent.putExtra("code", code);
//        intent.putExtra("type", type);
//        startActivity(intent);
    }

    private String pwd1;
    private String pwd2;

    private boolean CheckInput() {
        phone = edt_register_phone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            Toast.makeText(ForgetPWDActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        code = edt_register_yz.getText().toString();
        if (!TextUtils.isEmpty(code) &&verity!=null ) {
            if (!verity.equals(code)) {
                Toast.makeText(ForgetPWDActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(ForgetPWDActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        pwd1 =edt_reset_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd1)) {
            ToastUtil.show("新密码不能为空");
            return false;
        }

        if (pwd1.length() <= 5) {
            ToastUtil.show("新密码不能少于6位");
            return false;
        }
        pwd2 =edt_reset_pwd2.getText().toString().trim();
        if (TextUtils.isEmpty(pwd2)) {
            ToastUtil.show("再次输入的新密码不能为空");
            return false;
        }
        if (pwd2.length() <= 5) {
            ToastUtil.show("再次输入的新密码不能少于6位");
            return false;
        }
        if(!pwd1.equals(pwd2)){
            ToastUtil.show("两次输入的密码不相同");
            return false;
        }
        return true;
    }
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

    String phoneRegex = "^1[35789]\\d{9}$";
}
