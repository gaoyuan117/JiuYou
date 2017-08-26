package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuyou.R;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.ui.Utils.SharedPreference;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.CheckPhoneAndPwd;
import com.jiuyou.util.MD5Utils;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

public class LoginActivity extends BaseActivity {

    String phoneRegex = "^1[35789]\\d{9}$";
    EditText nameEdt, pwdEdt;
    Button loginBtn;
    CheckPhoneAndPwd checkPhoneAndPwd;
    boolean isPhone = false, isPwd = false;
    ImageView delPhone, delPwd;
    ImageView btn_back;
    TextView find_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getString(R.string.login));

    }

    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    private void init() {
        btn_back = (ImageView) findViewById(R.id.btn_back);
        find_pwd = (TextView) findViewById(R.id.find_pwd);
        find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext(ForgetPWDActivity.class);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        checkPhoneAndPwd = new CheckPhoneAndPwd();
        loginBtn = (Button) findViewById(R.id.btn_login);
        nameEdt = (EditText) findViewById(R.id.edt_login_user);
        pwdEdt = (EditText) findViewById(R.id.edt_reset_pwd);
        nameEdt.addTextChangedListener(phoneTextWatch);
        pwdEdt.addTextChangedListener(pwdTextWatch);

        delPhone = (ImageView) findViewById(R.id.del_phone);
        delPwd = (ImageView) findViewById(R.id.del_pwd);
        String lastPhone = PrefereUtils.getInstance().getLastPhone();
        String pwd = PrefereUtils.getInstance().getPwd();
        nameEdt.setText(lastPhone);
        pwdEdt.setText(pwd);
        if (!TextUtils.isEmpty(lastPhone)) {
            pwdEdt.requestFocus();
        } else {
            nameEdt.setSelection(lastPhone.length());
        }
        if (!TextUtils.isEmpty(pwd)) {
            pwdEdt.requestFocus();
        } else {
            pwdEdt.setSelection(pwd.length());
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
            if (s.toString().length() > 0) {
                delPhone.setVisibility(View.GONE);
            } else {
                delPhone.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            setLoginBtnStatus();
        }
    };

    private void setLoginBtnStatus() {
        if (isPhone && isPwd) {
            loginBtn.setEnabled(true);
        } else {
            loginBtn.setEnabled(false);
        }
    }


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
                delPwd.setVisibility(View.GONE);
            } else {
                delPwd.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            setLoginBtnStatus();
        }
    };

    public void register(View view) {
        toNext(RegisterActivity.class);
    }


    public void forgetPwd(View view) {
        //  toNext(RetrievePwdActivity.class);
//        Intent intent = new Intent(LoginActivity.this, RetrievePwdActivity.class);
//        intent.putExtra("phone", nameEdt.getText().toString());
//        startActivity(intent);
    }


    public void delPwd(View view) {
        pwdEdt.setText("");
    }

    public void delPhone(View view) {
        nameEdt.setText("");
    }


    public void login(View view) {
        final String phone = nameEdt.getText().toString();
        String pwd = pwdEdt.getText().toString();
        if (!checkPhone(phone)) {
            return;
        }
        if (!checkPwd(pwd)) {
            return;
        }
        getLoadingDataBar().show();
        Log.e(AppConfig.TAG, "loginpassword===" + MD5Utils.md5(pwd.getBytes()));
        UserUtils.getLoginInfo(phone, MD5Utils.md5(pwd.getBytes()), AppConfig.driver, new UserUtils.getLoginListener() {
            @Override
            public void load(boolean status, GoodsResponse info, String message) {
                if (status) {
                    saveUserInfo(phone, info.getData().getToken(), info.getData().getUid());
                    Log.e("gy", "token：" + info.getData().getToken());

                    new SharedPreference("token").edit()
                            .putString("token", info.getData().getToken())
                            .putString("uid", info.getData().getUid())
                            .commit();
                    LoginActivity.this.finish();
                } else {
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });
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

    /**
     * 如果密码正常的话，返回null
     */
    public boolean checkPwd(String password) {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
