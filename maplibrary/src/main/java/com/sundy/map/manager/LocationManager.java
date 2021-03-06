package com.sundy.map.manager;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 定位管理类
 */
public class LocationManager implements AMapLocationListener {
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mlocationClient = null;
    private Context mContext;
    private OnLocationListener listener;
    //间隔时间默认为2秒
    private int intervalTime=2000;
    //是否定位一次 true是一次 false为轮训
    private boolean isOnceLocation;
    public LocationManager(Context mContext) {
        this.mContext = mContext;
        initLocation();
    }


    /**
     * 初始化地图定位
     */
    private void initLocation() {
        mlocationClient = new AMapLocationClient(mContext);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
    }

    /**
     * 开始定位
     * @param listener 定位回调
     */
    public void startLocation(OnLocationListener listener) {
       startLocation(intervalTime,listener);
    }

    /**
     * 开始定位
     * @param intervalTime 间隔时间
     * @param listener     定位回调
     */
    public void startLocation(int intervalTime,OnLocationListener listener){
        startLocation(intervalTime,true,listener);
    }

    /**
     * 开始定位
     * @param intervalTime   间隔时间
     * @param isOnceLocation 是否定位
     * @param listener       定位回调
     */
    public void startLocation(int intervalTime,boolean isOnceLocation,OnLocationListener listener){
        this.listener=listener;
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(intervalTime);
        // mLocationOption.setOnceLocationLatest(true);
        //下面这句就是设置只定位一次的代码
        mLocationOption.setOnceLocation(isOnceLocation);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();
    }
    /**
     * 结束定位
     *
     * @param
     */
    public void stopLocation() {
        mlocationClient.stopLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                String address=aMapLocation.getAddress();//获取地址
                if (listener!=null){
                    listener.onSucceed(aMapLocation);
                }
            } else {
                if (listener!=null){
                    listener.onFailure(aMapLocation);
                }
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"+ aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 定義回調接口
     */
    public interface OnLocationListener{
        public void onSucceed(AMapLocation aMapLocation);
        public void onFailure(AMapLocation aMapLocation);
    }
}
