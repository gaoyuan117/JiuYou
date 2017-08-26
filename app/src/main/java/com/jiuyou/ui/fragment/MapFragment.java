package com.jiuyou.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jiuyou.R;
import com.jiuyou.core.AppContext;
import com.jiuyou.global.AppConfig;
import com.jiuyou.network.interfaces.HomeApi;
import com.jiuyou.network.response.JZBResponse.Cup;
import com.jiuyou.network.response.JZBResponse.CupResponse;
import com.jiuyou.network.response.JZBResponse.NearBy;
import com.jiuyou.network.response.JZBResponse.NearByResponse;
import com.jiuyou.ui.Utils.MapUtils;
import com.jiuyou.ui.base.BaseFragment;
import com.jiuyou.util.BitmapUtil;
import com.jiuyou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.SENSOR_SERVICE;


public class MapFragment extends BaseFragment implements View.OnClickListener, SensorEventListener {
    private TextView tv_title;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private boolean isFirst = true;
    private float mCurrentAccracy;
    private View view;
    private MapView mMapView;
    BaiduMap mBaiduMap;
    private Thread thread;
    private InfoWindow mInfoWindow;

    // UI相关
    private MyLocationData locData;
    private float direction;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);
    private List<Cup> cups;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_map, container, false);
            initView(view);
        } else {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        return view;
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getActivity().getSystemService(serviceName);
//        String provider = LocationManager.GPS_PROVIDER;
        String provider = LocationManager.NETWORK_PROVIDER;
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
        // 地图初始化
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
        if (AppConfig.ismap) {
            AppConfig.ismap = false;
//            initView(view);
            initData();
        } else {
            initAll(loc);
        }
    }

    private void initData() {
        if (AppConfig.currentOrder_Id != null && !AppConfig.currentOrder_Id.equals("") && !isFirst) {
            AppContext.createRequestApi(HomeApi.class).foundCup((float) mCurrentLon, (float) mCurrentLat, AppConfig.currentOrder_Id, new Callback<CupResponse>() {
                @Override
                public void success(CupResponse cupResponse, Response response) {
                    AppConfig.currentOrder_Id = null;
                    if (!isFirst) {
                        isFirst = false;
                        mBaiduMap.clear();
                    }
                    if (cupResponse.getCode() == 200) {
                        cups = cupResponse.getData();
                        if (cups != null && cups.size() > 0) {
                            initOverlayCup();
                        } else {
                            ToastUtil.show("周边没有合适的货柜！");
                        }
                    } else {
                        mBaiduMap.clear();
                        ToastUtil.show(cupResponse.getMessage());
                    }
                    mLocClient.unRegisterLocationListener(myListener);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    mLocClient.unRegisterLocationListener(myListener);
                }
            });
        }
    }

    private void initData1(final BDLocation location) {
        Address lo = location.getAddress();
        tv_title.setText(lo.city + lo.district);
        mCurrentAccracy = location.getRadius();
        locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        /*eld("lng") float lng,@Field("lat") float lat,@Field("order_no")
                */
        Log.e("tgh", "lng=" + mCurrentLon + " lat=" + mCurrentLat + " order_no=" + AppConfig.currentOrder_Id);
        AppContext.createRequestApi(HomeApi.class).foundCup((float) mCurrentLon, (float) mCurrentLat, AppConfig.currentOrder_Id, new Callback<CupResponse>() {
            @Override
            public void success(CupResponse cupResponse, Response response) {
                AppConfig.currentOrder_Id = null;
                isFirst = false;
                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (cupResponse.getCode() == 200) {
                    cups = cupResponse.getData();
                    if (cups != null && cups.size() > 0) {
                        initOverlayCup();
                    } else {
                        ToastUtil.show("周边没有合适的货柜！");
                    }
                } else {
                    mBaiduMap.clear();
                    ToastUtil.show(cupResponse.getMessage());
                }
                mLocClient.unRegisterLocationListener(myListener);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                mLocClient.unRegisterLocationListener(myListener);
            }
        });
    }

    @Override
    public void onStop() {
//        clearOverlay(null);
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        clearOverlay(null);
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        // 回收 bitmap 资源
        bdA.recycle();
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    List<NearBy> lists;
    BDLocation loc;

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            loc = location;
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            int type = location.getLocType();
            if (AppConfig.currentOrder_Id != null && !AppConfig.currentOrder_Id.equals("") && isFirst) {
                initData1(location);
            } else if (AppConfig.currentOrder_Id != null && !AppConfig.currentOrder_Id.equals("")) {

            } else {
                initAll(location);
            }


        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void initAll(final BDLocation location) {
        if (!isFirst) {
            mBaiduMap.clear();
        }
        if (location == null) {
            return;
        }
        Address lo = location.getAddress();
        tv_title.setText(lo.city + lo.district);
        mCurrentAccracy = location.getRadius();
        locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        //获取周边商家信息
        getLoadingDataBar().show();
        MapUtils.getNearBy((float) mCurrentLon, (float) mCurrentLat, new MapUtils.getNearByListener() {
            @Override
            public void load(boolean status, NearByResponse info, String message) {
                lists = new ArrayList<NearBy>();
                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                if (status) {
                    lists = info.getData();
                } else {
                    ToastUtil.show("周边没有商家换一个地方试试吧！");
                }
                //覆盖图层
                initOverlay();
                mLocClient.unRegisterLocationListener(myListener);
//                    handler.sendEmptyMessageDelayed(1,1000);
                loadingDataBarClose();
            }
        });
        isFirst = false;
    }

    public void initOverlay() {
        if (lists != null && lists.size() > 0) {
            new MutliThread().start();
        }
    }

    public void initOverlayCup() {
        if (cups != null && cups.size() > 0) {
            new MutliThreadCup().start();
        }
    }

    class MutliThread extends Thread {

        public MutliThread() {
        }

        @Override
        public void run() {
            for (int i = 0; i < lists.size(); i++) {
                synchronized (this) {
                    Bitmap bitmap = BitmapUtil.GetBitmap(AppConfig.ENDPOINTPIC + lists.get(i).getPath(), 100);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    if (lists.size() > 0) {
                        bundle.putSerializable("currentNearBy", lists.get(i));
                        msg.setData(bundle);
                        msg.obj = bitmap;
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }
                }
            }
        }
    }

    class MutliThreadCup extends Thread {

        public MutliThreadCup() {
        }

        @Override
        public void run() {
            for (int i = 0; i < cups.size(); i++) {
                synchronized (this) {
                    Bitmap bitmap = BitmapUtil.GetBitmap(AppConfig.ENDPOINTPIC + cups.get(i).getPath(), 100);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    if (cups.size() > 0) {
                        bundle.putSerializable("currentCup", cups.get(i));
                        msg.setData(bundle);
                        msg.obj = bitmap;
                        msg.what = 3;
                        handler.sendMessage(msg);
                    }
                }
            }
        }
    }

    PopupWindow popupWindow;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mLocClient.unRegisterLocationListener(myListener);
                    break;
                case 2:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    Bundle bundle = (Bundle) msg.getData();
                    NearBy nearBy = (NearBy) bundle.getSerializable("currentNearBy");
                    bdA = BitmapDescriptorFactory.fromBitmap(bitmap);
                    Marker mMarkerA = (Marker) (mBaiduMap.addOverlay(new MarkerOptions().position(new LatLng(Double.parseDouble(nearBy.getLat()), Double.parseDouble(nearBy.getLng()))).icon(bdA)
                            .zIndex(9).draggable(true)));
                    mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                        public boolean onMarkerClick(final Marker marker) {
                            InfoWindow.OnInfoWindowClickListener listener = null;

                            View view = View.inflate(getActivity().getApplicationContext(), R.layout.pop_map, null);
                            TextView title = (TextView) view.findViewById(R.id.tv_map_pop_title);
                            TextView des = (TextView) view.findViewById(R.id.tv_map_pop_des);

//                            Button button = new Button(getActivity().getApplicationContext());
//                            button.setBackgroundResource(R.drawable.alert_bg);
//                            button.setTextColor(Color.BLACK);
//                            button.setWidth(300);
                            for (int i = 0; i < lists.size(); i++) {
                                Double la = Double.parseDouble(lists.get(i).getLat());
                                Double lo = Double.parseDouble(lists.get(i).getLng());
                                if (la == marker.getPosition().latitude && lo == marker.getPosition().longitude) {
                                    title.setText(lists.get(i).getAddress());
                                    des.setText(lists.get(i).getAddress());
                                }
                            }
                            listener = new InfoWindow.OnInfoWindowClickListener() {
                                public void onInfoWindowClick() {

                                }
                            };
                            LatLng ll = marker.getPosition();
                            mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), ll, -47, listener);
                            mBaiduMap.showInfoWindow(mInfoWindow);
                            mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    LatLng ll = marker.getPosition();
                                    LatLng llNew = new LatLng(ll.latitude,
                                            ll.longitude);
                                    marker.setPosition(llNew);
                                    mBaiduMap.hideInfoWindow();
                                }

                                @Override
                                public boolean onMapPoiClick(MapPoi mapPoi) {
                                    return false;
                                }
                            });
                            return true;
                        }
                    });

                    break;
                case 3:
                    isFirst = false;
                    Bitmap bitmap1 = (Bitmap) msg.obj;
                    Bundle bundle1 = (Bundle) msg.getData();
                    Cup cup = (Cup) bundle1.getSerializable("currentCup");
                    bdA = BitmapDescriptorFactory.fromBitmap(bitmap1);
                    Marker mMarkerB = (Marker) (mBaiduMap.addOverlay(new MarkerOptions().position(new LatLng(Double.parseDouble(cup.getLat()), Double.parseDouble(cup.getLng()))).icon(bdA)
                            .zIndex(9).draggable(true)));
                    mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                        public boolean onMarkerClick(final Marker marker) {
                            Button button = new Button(getActivity().getApplicationContext());
                            InfoWindow.OnInfoWindowClickListener listener = null;
                            button.setBackgroundResource(R.drawable.alert_bg);
                            button.setTextColor(Color.BLACK);
                            button.setWidth(300);
                            for (int i = 0; i < cups.size(); i++) {
                                Double la = Double.parseDouble(cups.get(i).getLat());
                                Double lo = Double.parseDouble(cups.get(i).getLng());
                                if (la == marker.getPosition().latitude && lo == marker.getPosition().longitude) {
                                    button.setText(cups.get(i).getAddress());
                                }
                            }
                            listener = new InfoWindow.OnInfoWindowClickListener() {
                                public void onInfoWindowClick() {

                                }
                            };
                            LatLng ll = marker.getPosition();
                            mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                            mBaiduMap.showInfoWindow(mInfoWindow);
                            mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    LatLng ll = marker.getPosition();
                                    LatLng llNew = new LatLng(ll.latitude,
                                            ll.longitude);
                                    marker.setPosition(llNew);
                                    mBaiduMap.hideInfoWindow();
                                }

                                @Override
                                public boolean onMapPoiClick(MapPoi mapPoi) {
                                    return false;
                                }
                            });
                            return true;
                        }
                    });

                    break;
            }
            return false;
        }
    });

    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
    }
}
