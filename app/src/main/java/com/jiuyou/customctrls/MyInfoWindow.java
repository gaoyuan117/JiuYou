package com.jiuyou.customctrls;

import android.view.View;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;


public class MyInfoWindow extends InfoWindow {

    public MyInfoWindow(View view, LatLng latLng, int i) {
        super(view, latLng, i);
    }

    public MyInfoWindow(BitmapDescriptor bitmapDescriptor, LatLng latLng, int i, OnInfoWindowClickListener onInfoWindowClickListener) {
        super(bitmapDescriptor, latLng, i, onInfoWindowClickListener);
    }
}
