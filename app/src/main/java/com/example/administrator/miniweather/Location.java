package com.example.administrator.miniweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/19.
 */
public class Location extends Activity implements View.OnClickListener{

    private ImageView mBackbtn;
    private String cityCode;
    private TextView latitude,longitude,country,province,city,district,address;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener =null;
    public AMapLocationClientOption mLocationOption = null;

    private HashMap<String,String> data= MyApplication.getInstance().data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        mBackbtn=(ImageView)findViewById(R.id.title_back);
        mBackbtn.setOnClickListener(this);
        latitude=(TextView)findViewById(R.id.latitude);
        longitude=(TextView)findViewById(R.id.longitude);
        province=(TextView)findViewById(R.id.province);
        country=(TextView)findViewById(R.id.country);
        city=(TextView)findViewById(R.id.city);
        district=(TextView)findViewById(R.id.district);
        address=(TextView)findViewById(R.id.address);
        initLocation();
        mLocationClient.startLocation();
    }

    @Override
    public void onClick(View v) {
         if(v.getId()==R.id.title_back){
             Intent i=new Intent();
             i.putExtra("cityCode",cityCode);
             setResult(RESULT_OK,i);
             finish();
         }
    }

    void initLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationListener= new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                Log.d("定位",amapLocation.toString());
                if (amapLocation!=null) {
                    if (amapLocation.getErrorCode()==0) {
                        latitude.setText("纬度："+amapLocation.getLatitude());
                        longitude.setText("经度："+amapLocation.getLongitude());
                        address.setText("地址："+amapLocation.getAddress());
                        country.setText("国家："+amapLocation.getCountry());
                        province.setText("省："+amapLocation.getProvince());
                        district.setText("城区："+amapLocation.getDistrict());
                        city.setText("市："+amapLocation.getCity());
                        String cityName=(amapLocation.getCity()).substring(0,amapLocation.getCity().length()-1);
                        String districtName=(amapLocation.getDistrict()).substring(0,amapLocation.getDistrict().length()-1);
                        if(data.containsKey(districtName))
                          cityCode=data.get(districtName);
                        else if(data.containsKey(cityName))
                            cityCode=data.get(cityName);
                        Log.d("城市编码",cityCode);
                    }else {
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                    mLocationClient.stopLocation();
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }
}
