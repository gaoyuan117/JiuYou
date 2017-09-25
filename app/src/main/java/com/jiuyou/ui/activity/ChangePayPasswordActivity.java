package com.jiuyou.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
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
import com.jiuyou.core.AppContext;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.CheckPhoneAndPwd;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ChangePayPasswordActivity extends BaseActivity {
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
    @Bind(R.id.edt_pay_phone)
    EditText edt_pay_phone;
    @Bind(R.id.del_phone)
    ImageView del_phone;
    @Bind(R.id.edt_pay_yz)
    EditText edt_pay_yz;
    @Bind(R.id.tip_number)
    TextView tip_number;




    private String new_pwd1;
    private String new_pwd2;
    private String title;
    boolean isnewPwd1;
    boolean isnewPwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payword_change);
        ButterKnife.bind(this);
        checkPhoneAndPwd = new CheckPhoneAndPwd();
        setTitle("修改支付密码");
        edt_new_pwd2.setHint("请输入新密码（6位）");
//        edt_new_pwd2.setInputType(InputType.TYPE_CLASS_NUMBER);
        edt_new_pwd3.setHint("请再次输入新密码（6位）");
//        edt_new_pwd3.setInputType(InputType.TYPE_CLASS_NUMBER);
        setAllClick();
    }

    public void exit(View view) {
        ChangePayPasswordActivity.this.finish();
    }
    public void delPhone(View view) {
        edt_pay_phone.setText("");
    }
    public void delnewPwd1(View view) {
        edt_new_pwd2.setText("");
    }
    public void delnewPwd2(View view) {
        edt_new_pwd3.setText("");
    }

    private void setAllClick(){
        edt_pay_phone.addTextChangedListener(phoneTextWatch);
        checkPhoneAndPwd = new CheckPhoneAndPwd();
        tip_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edt_pay_phone.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                    ToastUtil.show("请输入正确的手机号码");
                    return;
                }
                stratNetWork();
            }
        });

        edt_new_pwd2.addTextChangedListener(pwdTextWatch2);
        edt_new_pwd3.addTextChangedListener(pwdTextWatch3);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      getLoadingDataBar();
                      //修改支付密码
                      if(CheckInput()){
                          String token=PrefereUtils.getInstance().getToken();
                          String pwd=edt_new_pwd2.getText().toString().trim();

                          AppContext.createRequestApi(HomeApi.class).editpay(token, phone, verity, pwd, new Callback<UserResponse>() {
                              @Override
                              public void success(UserResponse userResponse, Response response) {
                                  if(userResponse.getCode()==200){
                                      ToastUtil.show("支付密码重置成功");
                                      ChangePayPasswordActivity.this.finish();
                                  }else{
                                      ToastUtil.show(userResponse.getMessage());
                                  }

                                  closeProgressBar();
                              }

                              @Override
                              public void failure(RetrofitError retrofitError) {
                                  ToastUtil.show("获取信息失败");
                                  closeProgressBar();
                              }
                          });

                }

            }
        });
    }
    private void stratNetWork() {
        getLoadingDataBar().show();
        UserUtils.getVerifyInfo(phone, AppConfig.PAY, new UserUtils.getLoginListener() {
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
    Timer timer;
    public static int TIMER = 1;
    private int total = 59;
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
    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TIMER);
            }
        }, 1000, 1000);
    }
    boolean isPhone = false;
    CheckPhoneAndPwd checkPhoneAndPwd;
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

    String phoneRegex = "^1[35789]\\d{9}$";
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
        if (isnewPwd1&&isnewPwd2) {
            btn_register.setEnabled(true);
        } else {
            btn_register.setEnabled(false);
        }
    }



    private String phone, code;
    private String verity;

    private boolean CheckInput() {
        phone = edt_pay_phone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            Toast.makeText(ChangePayPasswordActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        code = edt_pay_yz.getText().toString();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(ChangePayPasswordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.isEmpty(code) &&verity!=null ) {
            if (!verity.equals(code)) {
                Toast.makeText(ChangePayPasswordActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else {
            Toast.makeText(ChangePayPasswordActivity.this, "请获取验证码", Toast.LENGTH_SHORT).show();
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
