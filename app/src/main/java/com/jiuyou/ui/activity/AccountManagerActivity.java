package com.jiuyou.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.NearByResponse;
import com.jiuyou.ui.Utils.UserUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sheyuan.universalimageloader.core.assist.ImageSize;
import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.customctrls.CircleImageView;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.response.JZBResponse.UserInfo;
import com.jiuyou.network.response.JZBResponse.UserResponse;
import com.jiuyou.ui.base.BaseActivity;
import com.jiuyou.util.ACache;
import com.jiuyou.util.PopUtil;
import com.jiuyou.util.PrefereUtils;
import com.jiuyou.util.ToastUtil;
import com.jiuyou.util.UtilFileDB;
import com.jiuyou.util.UtilImags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AccountManagerActivity extends BaseActivity {
        @Bind(R.id.title_bar_operate_1)
        ImageView title_bar_operate_1;
        @Bind(R.id.rl_head)
        RelativeLayout rl_head;
        @Bind(R.id.img_head)
        CircleImageView img_head;
        @Bind(R.id.rl_nick_name)
        RelativeLayout rl_nick_name;
        @Bind(R.id.txt_nick_name)
        TextView txt_nick_name;
        @Bind(R.id.rl_chundong)
        RelativeLayout rl_chundong;
        @Bind(R.id.txt_chundong)
        TextView txt_chundong;
        @Bind(R.id.rl_changeword)
        RelativeLayout rl_changeword;

        public static String ImageName;
        String urlsf = "";
        Uri uri = null, cutUri = null;
        public static final int NONE = 40;
        public static final int PHOTOHRAPH = 41;// 拍照
        public static final int PHOTOZOOM = 42; // 缩放
        public static final int PHOTORESOULT = 43;// 结果
        public static final String IMAGE_UNSPECIFIED = "image/*";
        UserInfo user;
        ACache aCache;
        int img = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manager);
        ButterKnife.bind(this);
        aCache = ACache.get(AccountManagerActivity.this);
        initview();
        init();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void initview(){
        title_bar_operate_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.currentTAB=MainActivity.TAB_MINE;
                AccountManagerActivity.this.finish();
            }
        });
    }
    public void fixHeader(View view) {
        PopUtil.showPhtotUpload(this, "Header");
    }
    public void cancelLoginOut(View v) {
        PopUtil.dismissPop();
    }
    public void fixNickName(View view){
        Intent intent=new Intent(AccountManagerActivity.this,ChangeNikeNameActivity.class);
        intent.putExtra("nikename",txt_nick_name.getText().toString().trim());
        startActivityForResult(intent,100);
    }

    public void fixChunDong(View view){
        //修改春東號
        AppContext.createRequestApi(HomeApi.class).isset_cd_acc(PrefereUtils.getInstance().getToken(), new Callback<NearByResponse>() {
            @Override
            public void success(NearByResponse nearByResponse, Response response) {
                  if(nearByResponse.getCode()==200){
                      Intent chundong=new Intent(AccountManagerActivity.this,ChangeChunDongActivity.class);
                      String cd=txt_chundong.getText().toString().trim();
                      if(cd.contains("未设置")){
                          cd="";
                      }
                      chundong.putExtra("chundong",cd);
//            startActivity(chundong);
                      startActivityForResult(chundong,100);
                  }else{
                      ToastUtil.show(nearByResponse.getMessage());
                  }
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });

    }

    public  void fixPwd(View view){

        toNext(ChangePWDANDPAYActivity.class);


    }
    public void getAvatarByCamera(View view) {
        PopUtil.dismissPop();
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, 1);
    }
    public void getAvatarFromAlbum(View view) {
        PopUtil.dismissPop();
        Intent picture = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1000){
              init();
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK
                && null != data) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss",
                    Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            // 获取相机返回的数据，并转换为图片格式
            Bitmap bmp = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            String filename = null;
            try {
                filename = UtilImags.SHOWFILEURL(AccountManagerActivity.this) + "/" + name;
            } catch (IOException e) {
            }
            try {
                fout = new FileOutputStream(filename);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            } catch (FileNotFoundException e) {
                showToastShort("上传失败");
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    showToastShort("上传失败");
                }
            }
            img_head.setImageBitmap(bmp);
            staffFileupload(new File(filename));
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = this.getContentResolver().query(selectedImage,
                        filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                Log.e(AppConfig.TAG,"picturePath="+picturePath);
                c.close();

                Bitmap bmp = BitmapFactory.decodeFile(picturePath);
                // 获取图片并显示
                img_head.setImageBitmap(bmp);
                saveBitmapFile(UtilImags.compressScale(bmp), UtilImags.SHOWFILEURL(AccountManagerActivity.this) + "/stscname.jpg");
                staffFileupload(new File(UtilImags.SHOWFILEURL(AccountManagerActivity.this) + "/stscname.jpg"));
            } catch (Exception e) {
                showToastShort("上传失败");
            }
        }
    }
    public void saveBitmapFile(Bitmap bitmap, String path) {
        File file = new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setUI() {
        if (TextUtils.isEmpty(user.getMobile())) {
            txt_nick_name.setTextColor(getResources().getColor(R.color.xn_red));
            txt_nick_name.setText("绑定手机号");
        } else {
            txt_nick_name.setTextColor(getResources().getColor(R.color.account_manager_txt_color));
            txt_nick_name.setText(user.getMobile());
        }
        if(user.getCd_acc()!=null&&!user.getCd_acc().equals("")){
            txt_chundong.setText(user.getCd_acc());
        }else{
            txt_chundong.setText("未设置");
        }

//        tags.setText(user.getUserInfo().getTags());
        setHead(user.getAvatar());
    }
    private void setHead(String headImg) {
        ImageSize imageSize = new com.sheyuan.universalimageloader.core.assist.ImageSize(80, 80);
    }
    private void modifyHead(final String headPath) {

        PopUtil.dismissPop();
        List<String> mSelectPath = new ArrayList<String>();
        mSelectPath.add(headPath);

    }
    private void sendHeadImages(List<String> fileNames) {
        if (fileNames == null || fileNames.size() == 0) {
            ToastUtil.show("上传失败");
            return;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fileNames.size(); i++) {
            if (i != fileNames.size() - 1) {
                sb.append(fileNames.get(i)).append(",");
            } else if (i == fileNames.size() - 1) {
                sb.append(fileNames.get(i));
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        getLoadingDataBar().show();
        UserUtils.getUserInfo(PrefereUtils.getInstance().getToken(), new UserUtils.getUserInfoListener() {
            @Override
            public void load(boolean status, UserResponse info, String message) {
                if(status){
                    //保存用户信息
                    upDateUserStatus(info.getUserInfo());
                    //显示用户信息详情
                    showUserInfo(info.getUserInfo());

                }else{
                    ToastUtil.show(message);
                }
                closeProgressBar();
            }
        });

    }
    private void showUserInfo(UserInfo info){
        AppContext.getImageLoaderProxy().displayImage(AppConfig.ENDPOINTPIC+info.getAvatar(),img_head);
        txt_nick_name.setText(info.getNickname());
        if(info.getCd_acc()!=null&&!info.getCd_acc().equals("")){
            txt_chundong.setText(info.getCd_acc());
        }else{
            txt_chundong.setText("未设置");
        }
    }
    public void staffFileupload(File file) {
        if (false) {
            showToastShort("网络未连接");
            return;
        }
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, "http://cupboard.jzbwlkj.com/index.php/api/user/Updateavatar", MYUPDATEIMG(file),
                new RequestCallBack<String>() {

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        JSONObject jsonobj;
                        try {
                            jsonobj = new JSONObject(arg0.result.toString());
                            String errno = jsonobj.getString("errno");
                            String error = jsonobj.getString("error");
                            if (errno.equals("0") && error.equals("success")) {
                                JSONArray jsonarray = jsonobj.getJSONArray("data");
                                JSONObject jsonobjq = jsonarray.getJSONObject(0);
                                urlsf = jsonobjq.getString("url");
                                UtilFileDB.ADDFile(aCache, "stscimage", urlsf);
                                img = 3;
                                showToastShort("头像修改成功");

                            } else {
                                showToastShort("头像修改失败");
                            }
                        } catch (JSONException e) {
                            return;
                        }
                    }

                });
    }
    /***
     * 修改头像
     *
     * @return
     */
    public static final RequestParams MYUPDATEIMG(File file) {
        RequestParams params = new RequestParams();
        String token=PrefereUtils.getInstance().getToken();
        params.addBodyParameter("token", token);
        if (file != null) {
            params.addBodyParameter("avatar", file);
        }
        return params;
    }

    private void showToastShort(String string) {
        Toast.makeText(AccountManagerActivity.this, string, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            AccountManagerActivity.this.finish();
            AppConfig.currentTAB=MainActivity.TAB_MINE;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}