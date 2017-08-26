package com.jiuyou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jiuyou.R;
import com.jiuyou.model.Areabean;
import com.jiuyou.model.CityBean;
import com.jiuyou.model.ProvinceBean;
import com.jiuyou.retrofit.BaseListObserver;
import com.jiuyou.retrofit.HttpArray;
import com.jiuyou.retrofit.RetrofitClient;
import com.jiuyou.retrofit.RxUtils;
import com.jiuyou.ui.adapter.SelectAdressAdapter;
import com.jiuyou.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectAdressActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.lv_select_adress)
    ListView mListView;

    private List<String> mList;
    private SelectAdressAdapter mAdapter;
    private String type;
    private List<ProvinceBean> provinceList;
    private List<CityBean> cityList;
    private List<Areabean> areaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_adress);
        ButterKnife.bind(this);
        setCenterTitle("选择城市");

        type = getIntent().getStringExtra("type");
        Log.e("gy", "type " + type);
        init();
        mListView.setOnItemClickListener(this);
    }

    private void init() {
        mList = new ArrayList<>();
        mAdapter = new SelectAdressAdapter(this, mList);
        mListView.setAdapter(mAdapter);

        if (type.equals("sheng")) {
            getProvinces();
        } else if (type.equals("shi")) {
            String provinceId = getIntent().getStringExtra("province_id");
            Log.e("gy", "province_id：" + provinceId);
            getCitys(provinceId);
        } else if (type.equals("qu")) {
            String cityId = getIntent().getStringExtra("city_id");
            Log.e("gy", "province_id：" + cityId);
            getArea(cityId);
        }
    }


    /**
     * 获取区县
     *
     * @param cityId
     */
    private void getArea(String cityId) {
        RetrofitClient.getInstance().createApi().getArea(cityId)
                .compose(RxUtils.<HttpArray<Areabean>>io_main())
                .subscribe(new BaseListObserver<Areabean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(List<Areabean> list) {
                        areaList = list;
                        for (int i = 0; i < list.size(); i++) {
                            mList.add(list.get(i).getArea());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 获取城市
     */
    private void getCitys(String provinceId) {
        RetrofitClient.getInstance().createApi().getCity(provinceId)
                .compose(RxUtils.<HttpArray<CityBean>>io_main())
                .subscribe(new BaseListObserver<CityBean>(this, "获取中") {
                    @Override
                    protected void onHandleSuccess(List<CityBean> list) {
                        cityList = list;
                        for (int i = 0; i < list.size(); i++) {
                            mList.add(list.get(i).getCity());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 获取省份
     */
    public void getProvinces() {
        RetrofitClient.getInstance().createApi().getProvinces("")
                .compose(RxUtils.<HttpArray<ProvinceBean>>io_main())
                .subscribe(new BaseListObserver<ProvinceBean>(this, "获取中") {

                    @Override
                    protected void onHandleSuccess(List<ProvinceBean> list) {
                        provinceList = list;
                        for (int i = 0; i < list.size(); i++) {
                            mList.add(list.get(i).getProvince());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        if (type.equals("sheng")) {
            intent.putExtra("sheng", provinceList.get(i));
        } else if (type.equals("shi")) {
            intent.putExtra("shi", cityList.get(i));

        } else if (type.equals("qu")) {
            intent.putExtra("qu", areaList.get(i));
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
