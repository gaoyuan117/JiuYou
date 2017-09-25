package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.jiuyou.R;
import com.jiuyou.global.BaseApp;
import com.jiuyou.model.Areabean;
import com.jiuyou.model.CityBean;
import com.jiuyou.model.CommonBean;
import com.jiuyou.model.MyAdressBean;
import com.jiuyou.model.ProvinceBean;
import com.jiuyou.retrofit.BaseObjObserver;
import com.jiuyou.retrofit.HttpResult;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAdressActivity extends BaseActivity {

    @Bind(R.id.et_add_adress_name)
    EditText mAdressName;
    @Bind(R.id.et_add_adress_phone)
    EditText mAdressPhone;
    @Bind(R.id.et_add_adress_sheng)
    EditText mAdressSheng;
    @Bind(R.id.et_add_adress_shi)
    EditText mAdressShi;
    @Bind(R.id.et_add_adress_qu)
    EditText mAdressQu;
    @Bind(R.id.et_add_adress_detail)
    EditText mAdressDetail;

    private ProvinceBean provinceBean;
    private CityBean cityBean;
    private Areabean areabean;
    private GeoCoder mSearch;

    private String detail, area, city, province, phone, name, provinceId, cityId, areaId;
    private double longitude;
    private double latitude;
    private MyAdressBean bean;
    private int num;//是否有地址


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        ButterKnife.bind(this);
        setCenterTitle("新增收货地址");
        initEvent();

        bean = (MyAdressBean) getIntent().getSerializableExtra("data");
        num = getIntent().getIntExtra("num", 0);

        if (bean != null) {
            provinceId = bean.getProvince_id();
            cityId = bean.getCity_id();
            areaId = bean.getArea_id();
        }

        Log.e("gy", "有没有收货地址：" + num);
        if (bean != null) {
            setData();
        }
    }

    /**
     * 编辑地址  设置信息
     */
    private void setData() {
        mAdressName.setText(bean.getRealname());
        mAdressShi.setText(bean.getCity());
        mAdressSheng.setText(bean.getProvince());
        mAdressQu.setText(bean.getArea());
        mAdressDetail.setText(bean.getAddress());
        mAdressPhone.setText(bean.getMobile());
    }

    private void initEvent() {
        setRightTitle("保存").setOnClickListener(new View.OnClickListener() {
            //高原 凤凰路海信创智谷

            @Override
            public void onClick(View view) {
                province = mAdressSheng.getText().toString();
                city = mAdressShi.getText().toString();
                area = mAdressQu.getText().toString();
                detail = mAdressDetail.getText().toString();
                name = mAdressName.getText().toString();
                phone = mAdressPhone.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    showToastMsg("请输入收货人姓名");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    showToastMsg("请输入收货人手机号");
                    return;
                }

                if (TextUtils.isEmpty(provinceId)) {
                    showToastMsg("请选择省份");
                    return;
                }

                if (TextUtils.isEmpty(cityId)) {
                    showToastMsg("请选择市");
                    return;
                }
                if (TextUtils.isEmpty(areaId)) {
                    showToastMsg("请选择区/县");
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    showToastMsg("请输入详细地址");
                    return;
                }
                if (bean == null) {
                    addReceiveAddr();
                } else {
                    editAdress();
                }
//                getGeoPointBystr(detail, city);
            }
        });
    }

    @OnClick({R.id.et_add_adress_sheng, R.id.et_add_adress_shi, R.id.et_add_adress_qu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_add_adress_sheng:
                Intent intent = new Intent(this, SelectAdressActivity.class);
                intent.putExtra("type", "sheng");
                startActivityForResult(intent, 1);
                break;
            case R.id.et_add_adress_shi:
                if (TextUtils.isEmpty(provinceId)) {
                    showToastMsg("请先选择省份");
                    return;
                }
                Intent intent2 = new Intent(this, SelectAdressActivity.class);
                intent2.putExtra("type", "shi");
                intent2.putExtra("province_id", provinceId);
                startActivityForResult(intent2, 2);

                break;
            case R.id.et_add_adress_qu:
                if (TextUtils.isEmpty(cityId)) {
                    showToastMsg("请先选择市");
                    return;
                }
                Intent intent3 = new Intent(this, SelectAdressActivity.class);
                intent3.putExtra("type", "qu");
                intent3.putExtra("city_id", cityId);
                startActivityForResult(intent3, 3);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    provinceBean = (ProvinceBean) data.getSerializableExtra("sheng");
                    provinceId = provinceBean.getProvince_id();
                    mAdressSheng.setText(provinceBean.getProvince());
                    mAdressShi.setText("市");
                    mAdressQu.setText("区/县");
                    cityId = "";
                    areaId = "";
                    break;
                case 2:
                    cityBean = (CityBean) data.getSerializableExtra("shi");
                    cityId = cityBean.getCity_id();
                    mAdressShi.setText(cityBean.getCity());
                    mAdressQu.setText("区/县");
                    areaId = "";
                    break;

                case 3:
                    areabean = (Areabean) data.getSerializableExtra("qu");
                    areaId = areabean.getArea_id();
                    mAdressQu.setText(areabean.getArea());
                    break;
            }
        }
    }


    /**
     * 添加收获地址
     */
    private void addReceiveAddr() {
        Log.e("gy", "token:" + BaseApp.token());
        Map<String, Object> map = new HashMap<>();
        map.put("token", BaseApp.token());
        map.put("mobile", phone);
        map.put("realname", name);
        map.put("province_id", provinceBean.getProvince_id());
        map.put("city_id", cityBean.getCity_id());
        map.put("area_id", areabean.getArea_id());
        map.put("address", detail);
        map.put("lng", longitude);
        map.put("lat", latitude);
        if (num > 0) {
            map.put("is_default", 0);
        } else {
            map.put("is_default", 1);
        }
        RetrofitClient.getInstance().createApi().addReceiveAddr(map)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "创建中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        showToastMsg("新地址添加成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    /**
     * 编辑收货地址
     */
    private void editAdress() {
        Log.e("gy", "token:" + BaseApp.token());
        Map<String, Object> map = new HashMap<>();

        map.put("id", bean.getId());
        map.put("token", BaseApp.token());
        map.put("mobile", phone);
        map.put("realname", name);

        map.put("province_id", provinceId);
        map.put("city_id", cityId);
        map.put("area_id", areaId);

        map.put("address", detail);
        map.put("lng", longitude);
        map.put("lat", latitude);
        RetrofitClient.getInstance().createApi().edtReceiveAddr(map)
                .compose(RxUtils.<HttpResult<CommonBean>>io_main())
                .subscribe(new BaseObjObserver<CommonBean>(this, "修改中") {
                    @Override
                    protected void onHandleSuccess(CommonBean commonBean) {
                        showToastMsg("修改成功");
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }
}
