package com.jiuyou.ui.Utils;


import android.util.Log;

import com.jiuyou.core.AppContext;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.AboutResponse;
import com.jiuyou.network.response.JZBResponse.AmountResponse;
import com.jiuyou.network.response.JZBResponse.GoodsResponse;
import com.jiuyou.network.response.JZBResponse.UserResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserUtils {
    
    private static HomeApi getHomeApi() {
        return AppContext.createRequestApi(HomeApi.class);
    }

    //获取登录监听
    public interface getLoginListener {
        void load(boolean status, GoodsResponse info, String message);
    }
    //获取登录监听
    public interface getRegisterListener {
        void load(boolean status, GoodsResponse info, String message);
    }
    //获取短信监听
    public interface getVerifyListener {
        void load(boolean status, GoodsResponse info, String message);
    }
    //获取密码重置监听
    public interface getResetPWDListener {
        void load(boolean status, GoodsResponse info, String message);
    }

    //获取充值列表监听
    public interface getRechargeListListener {
        void load(boolean status, GoodsResponse info, String message);
    }
    //获取购买历史列表监听
    public interface getBuyListListener {
        void load(boolean status, GoodsResponse info, String message);
    }

    //提交反馈
    public interface getFeedBaskListener {
        void load(boolean status, GoodsResponse info, String message);
    }

    //用户信息监听
    public interface getUserInfoListener {
        void load(boolean status, UserResponse info, String message);
    }

    //昵称修改监听
    public interface getNikeNameListener {
        void load(boolean status, UserResponse info, String message);
    }
    //春东号设置
    public interface setNameListener {
        void load(boolean status, UserResponse info, String message);
    }
    //修改支付设置
    public interface setPayListener {
        void load(boolean status, UserResponse info, String message);
    }
    //修改登录密码设置
    public interface setLoginListener {
        void load(boolean status, UserResponse info, String message);
    }
    //下单设置
    public interface reChargeListener {
        void load(boolean status, AmountResponse info, String message);
    }
    //关于
    public interface aboutListener {
        void load(boolean status, AboutResponse info, String message);
    }
    /**
     *关于
     */
    public static void about(String token,final aboutListener listener) {
        HomeApi api = getHomeApi();
        api.about(token,new Callback<AboutResponse>() {
            @Override
            public void success(AboutResponse aboutResponse, Response response) {
                if (aboutResponse.getCode()==200) {
                    listener.load(true, aboutResponse, "充值成功");
                } else {
                    listener.load(false, null, aboutResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "充值失败");

            }
        });
    }
    /**
     *充值下单
     */
    public static void reChargeOrder(String token,double amount,String pay_channel,final reChargeListener listener) {
        HomeApi api = getHomeApi();
        api.rechargeOrder(token,amount,pay_channel, new Callback<AmountResponse>() {
            @Override
            public void success(AmountResponse userResponse, Response response) {
                if (userResponse.getCode()==200) {
                    listener.load(true, userResponse, "充值成功");
                } else {
                    listener.load(false, null, userResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "充值失败");

            }
        });
    }
    /**
     *修改登录密码
     */
    public static void setLoginInfo(String token,String oldpwd,String newpwd,String repwd,final setLoginListener listener) {
        HomeApi api = getHomeApi();
        api.setLoginPwd(token,oldpwd,newpwd,repwd, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if (userResponse.getCode()==200) {
                    listener.load(true, userResponse, "登录密码重置成功");
                } else {
                    listener.load(false, null, userResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "登录密码重置失败");

            }
        });
    }


    /**
     *设置修改支付密码
     */
    public static void setPayInfo(String token,String pwd,final setPayListener listener) {
        HomeApi api = getHomeApi();
        api.setPayPwd(token,pwd, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if (userResponse.getCode()==200) {
                    listener.load(true, userResponse, "支付密码重置成功");
                } else {
                    listener.load(false, null, userResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "支付密码重置失败");

            }
        });
    }

    /**
     *获取登录信息
     */
    public static void getUserInfo(String token,final getUserInfoListener listener) {
        HomeApi api = getHomeApi();
        api.getUserInfo(token, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if (userResponse.getCode()==200) {
                    listener.load(true, userResponse, "获取信息成功");
                } else {
                    listener.load(false, null, userResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

    /**
     *获取登录信息
     */
    public static void getLoginInfo(String user,String pwd,String driver,final getLoginListener listener) {
        HomeApi api = getHomeApi();
        api.getlogin(user,pwd,driver, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

    /**
     *获取登录信息
     */
    public static void getRegisterInfo(String code,String user,String pwd,String driver,String referrer,final getRegisterListener listener) {
        HomeApi api = getHomeApi();
        api.getRegister(code,user,pwd,driver,referrer, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }
    /**
     *获取密码重置信息
     */
    public static void getResetPWD(String pass,String repass,String user,String code,final getResetPWDListener listener) {
        HomeApi api = getHomeApi();
        api.getResetPassword(pass,repass,user,code, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }
    /**
     *昵称修改
     */
    public static void editNikeName(String token,String nickname,final getNikeNameListener listener) {
        HomeApi api = getHomeApi();
        api.editNickname(token,nickname, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if (userResponse.getCode()==200) {
                    listener.load(true, userResponse, "获取信息成功");
                } else {
                    listener.load(false, null, userResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }
    /**
     *昵称修改
     */
    public static void editSetName(String token,String cd_acc,final setNameListener listener) {
        HomeApi api = getHomeApi();
        api.editSetname(token,cd_acc, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {
                if (userResponse.getCode()==200) {
                    listener.load(true, userResponse, "获取信息成功");
                } else {
                    listener.load(false, null, userResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }
    /**
     *获取短信信息
     */
    public static void getVerifyInfo(String user,String type,final getLoginListener listener) {
        HomeApi api = getHomeApi();
        api.getVerify(user,type, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

    /**
     *获取充值列表信息/获取购买历史信息
     */
    public static void getRechargeListInfo(String token,String Page,String pageSize,final getRechargeListListener listener) {
        HomeApi api = getHomeApi();
        Log.e("tgh", "token="+token);
        api.getRechargeList(token,Page,pageSize, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }
    /**
     *获取充值列表信息/获取购买历史信息
     */
    public static void getBuyHistoryList(String token,String Page,String pageSize,final getRechargeListListener listener) {
        HomeApi api = getHomeApi();
        api.getBuyHistoryList(token,Page,pageSize, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

    /**
     *提交反馈
     */
    public static void getFeedBask(String token,String content,String phone,final getFeedBaskListener listener) {
        HomeApi api = getHomeApi();
        api.getFeedBack(token,content,phone, new Callback<GoodsResponse>() {
            @Override
            public void success(GoodsResponse goodsResponse, Response response) {
                if (goodsResponse.getCode()==200) {
                    listener.load(true, goodsResponse, "获取信息成功");
                } else {
                    listener.load(false, null, goodsResponse.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                listener.load(false, null, "获取信息失败");

            }
        });
    }

}
