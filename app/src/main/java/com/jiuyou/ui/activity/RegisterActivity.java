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


public class RegisterActivity extends BaseActivity {
    @Bind(R.id.edt_pay_phone)
    EditText edt_register_phone;
    @Bind(R.id.tip_number)
    TextView tip_number;
    @Bind(R.id.edt_pay_yz)
    EditText edt_register_yz;
    @Bind(R.id.edt_register_pwd)
    EditText edt_register_pwd;
    @Bind(R.id.edt_register_tuijian)
    EditText edt_register_tuijian;
    @Bind(R.id.btn_register)
    Button btn_register;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.del_phone)
    ImageView del_phone;
    private String phone, code;
    Timer timer;
    public static int TIMER = 1;
    private int total = 59;
    private String verity;
    private String type = "register";
    boolean isIdentify = false, isNewPwd = false;
    CheckPhoneAndPwd checkPhoneAndPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle("注册");
        setAllClick();
    }

    public void delPhone(View view) {
        edt_register_phone.setText("");
    }

    protected void setAllClick() {
        edt_register_phone.addTextChangedListener(phoneTextWatch);
        checkPhoneAndPwd = new CheckPhoneAndPwd();
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


    }

    String pwdRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";


    private void stratNetWork() {
        getLoadingDataBar().show();
        UserUtils.getVerifyInfo(phone, AppConfig.REGISTER, new UserUtils.getLoginListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if (status) {
                    ToastUtil.show("验证码已经发送");
                    startTimer();
                    verity = info.getData().getVerify();
                    Log.e(AppConfig.TAG, "verity====" + verity);
                } else {
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
    }

    public void register(View view) {

        if (CheckInput()) {
            if (type.equals("forgotten")) {
                if (!TextUtils.isEmpty(verity)) {
                    if (verity.equals(edt_register_yz.getText().toString())) {
                        startActivity();
                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
                }
                return;
            } else {
                //執行註冊
                String s = edt_register_tuijian.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    s = MD5Utils.toMD5(s);
                }
                Log.e("gy", "推荐人：" + s);
                getLoadingDataBar().show();
                UserUtils.getRegisterInfo(code, phone, MD5Utils.md5(pwd.getBytes()), AppConfig.driver, s, new UserUtils.getRegisterListener() {
                    @Override
                    public void load(boolean status, GoodsResponse info, String message) {
                        if (status) {
                            saveRegisterUser(phone, info.getData().getToken(), pwd);
                            ToastUtil.show("注册成功!");
//                                ActivitysManager.getInstance().killActivity(LoginActivity.class);
                            finish();

                        } else {
                            ToastUtil.show(message);
                        }
                        closeProgressBar();
                    }
                });

            }
        }
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
                    tip_number.setText(total + "秒后重试");
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

    String pwd;

    private boolean CheckInput() {
        phone = edt_register_phone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        code = edt_register_yz.getText().toString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.isEmpty(code) && verity != null) {
            if (!verity.equals(code)) {
                Toast.makeText(RegisterActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(RegisterActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        pwd = edt_register_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.show("密码不能为空");
            return false;
        }

        if (pwd.length() <= 5) {
            ToastUtil.show("密码不能少于6位");
            return false;
        }

        return true;
    }

    String phoneRegex = "^1[35789]\\d{9}$";
    boolean isPhone = false, isPwd = false;
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


}
