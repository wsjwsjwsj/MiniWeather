package com.example.administrator.miniweather;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/25.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private static final int UPDATE_TODAY_WEATHER = 1;
    private ImageView mUpdateBtn;
    private ProgressBar mUpdateBar;
    private ImageView mCitySelect;
    private ImageView mLocation;
    private TextView cityTv, timeTv, humidityTv,wenduTv, weekTv, pmDataTv, pmQualityTv, temperatureTv, climateTv, windTv, city_name_Tv;
    private ImageView pmImg, weatherImg;
    private MyService myService;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private ImageView[] dots;
    private int[] ids={R.id.iv1,R.id.iv2};



    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    mUpdateBtn.setVisibility(View.VISIBLE);
                    mUpdateBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    public Handler getHandler(){
        return this.mHandler;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService=null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((MyService.MyBinder) service).getService();
            myService.mainActivity=MainActivity.this;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_info);
        bindService(new Intent(getBaseContext(),MyService.class),connection,BIND_AUTO_CREATE);
        mLocation=(ImageView)findViewById(R.id.title_location) ;
        mLocation.setOnClickListener(this);
        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);
        mUpdateBar=(ProgressBar)findViewById(R.id.title_update_progress);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络已连接");
            Toast.makeText(MainActivity.this, "网络已连接", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "网络未连接");
            Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_LONG).show();
        }
        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);
        initView();
        initFragments();
    }

    void initView() {
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        wenduTv =(TextView)findViewById(R.id.wendu);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);
        SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        String city = sharedPreferences.getString("City", "");
        String time = sharedPreferences.getString("Updatetime", "");
        String humidity = sharedPreferences.getString("Shidu", "");
        String wendu =sharedPreferences.getString("Wendu","");
        String data = sharedPreferences.getString("Pm25", "");
        String quality = sharedPreferences.getString("Quality", "");
        String week = sharedPreferences.getString("Date", "");
        String climate = sharedPreferences.getString("Type", "");
        String wind = sharedPreferences.getString("Fengli", "");
        String low = sharedPreferences.getString("Low", "");
        String high = sharedPreferences.getString("High", "");
        city_name_Tv.setText(city+"天气");
        cityTv.setText(city);
        timeTv.setText(time+"发布");
        humidityTv.setText("湿度："+humidity);
        wenduTv.setText("温度："+wendu+"℃");
        pmDataTv.setText(data);
        pmQualityTv.setText(quality);
        weekTv.setText(week);
        temperatureTv.setText(low + "~" + high);
        climateTv.setText(climate);
        windTv.setText("风力："+wind);

        if (data != "") {
            if (Integer.parseInt(data) <= 50)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
            else if (Integer.parseInt(data) <= 100)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
            else if ( Integer.parseInt(data) <= 150)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
            else if (Integer.parseInt(data) <= 200)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
            else if (Integer.parseInt(data) <= 300)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
            else if (Integer.parseInt(data) > 300)
                pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
        }

        if (climate != "")
            switch (climate) {
                case "暴雪":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                    break;
                case "暴雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                    break;
                case "大暴雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                    break;
                case "大雪":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
                    break;
                case "多云":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                    break;
                case "雷阵雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                    break;
                case "雷阵雨冰雹":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                    break;
                case "晴":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
                    break;
                case "沙尘暴":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                    break;
                case "特大暴雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                    break;
                case "雾":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
                    break;
                case "小雪":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                    break;
                case "小雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                    break;
                case "阴":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
                    break;
                case "雨加雪":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                    break;
                case "阵雪":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                    break;
                case "阵雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                    break;
                case "中雪":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                    break;
                case "中雨":
                    weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                    break;
            }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.title_city_manager) {
            Intent i = new Intent(this, SelectCity.class);
            startActivityForResult(i, 1);
        }
        if (view.getId() == R.id.title_update_btn) {
            mUpdateBtn.setVisibility(View.INVISIBLE);
            mUpdateBar.setVisibility(View.VISIBLE);
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code", "101010100");
            Log.d("myWeather", cityCode);
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络已连接");
                myService.queryWeatherCode(cityCode);
            } else {
                Log.d("myWeather", "网络未连接");
                Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_LONG).show();
            }
        }
        if (view.getId() == R.id.title_location) {
            Intent i = new Intent(this, Location.class);
            startActivityForResult(i, 2);
            Toast.makeText(MainActivity.this, "定位", Toast.LENGTH_LONG).show();
            }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data.getStringExtra("cityCode")!=null ){
            String newCityCode = data.getStringExtra("cityCode");
            Log.d("myWeather", "选择的城市代码为" + newCityCode);
            SharedPreferences settings = (SharedPreferences) getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("main_city_code", newCityCode);
            editor.commit();
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络已连接");
                myService.queryWeatherCode(newCityCode);
            } else {
                Log.d("myWeather", "网络未连接");
                Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == 2 && resultCode == RESULT_OK && data.getStringExtra("cityCode")!=null ){
            String newCityCode = data.getStringExtra("cityCode");
            Log.d("myWeather", "定位的城市代码为" + newCityCode);
            SharedPreferences settings = (SharedPreferences) getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("main_city_code", newCityCode);
            editor.commit();
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络已连接");
                myService.queryWeatherCode(newCityCode);
            } else {
                Log.d("myWeather", "网络未连接");
                Toast.makeText(MainActivity.this, "网络未连接", Toast.LENGTH_LONG).show();
            }
        }
    }

    void updateTodayWeather(TodayWeather todayWeather) {
        if (todayWeather != null) {
            SharedPreferences settings = (SharedPreferences) getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();

            city_name_Tv.setText(todayWeather.getCity()+"天气");
            editor.putString("City", todayWeather.getCity());
            cityTv.setText(todayWeather.getCity());

            timeTv.setText(todayWeather.getUpdatetime() + "发布");
            editor.putString("Updatetime", todayWeather.getUpdatetime());

            humidityTv.setText("湿度：" + todayWeather.getShidu());
            editor.putString("Shidu", todayWeather.getShidu());

            wenduTv.setText("温度："+todayWeather.getWendu()+"℃");
            editor.putString("Wendu", todayWeather.getWendu());

            pmDataTv.setText(todayWeather.getPm25());
            editor.putString("Pm25", todayWeather.getPm25());

            pmQualityTv.setText(todayWeather.getQuality());
            editor.putString("Quality", todayWeather.getQuality());

            weekTv.setText(todayWeather.getDate());
            editor.putString("Date", todayWeather.getDate());

            temperatureTv.setText(todayWeather.getHigh() + "~" + todayWeather.getLow());
            editor.putString("High", todayWeather.getHigh());
            editor.putString("Low", todayWeather.getLow());

            climateTv.setText(todayWeather.getType());
            editor.putString("Type", todayWeather.getType());

            windTv.setText("风力:" + todayWeather.getFengli());
            editor.putString("Fengli", todayWeather.getFengli());

            if (todayWeather.getPm25() != null) {
                if (Integer.parseInt(todayWeather.getPm25()) <= 50)
                    pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
                else if (Integer.parseInt(todayWeather.getPm25()) <= 100)
                    pmImg.setImageResource(R.drawable.biz_plugin_weather_51_100);
                else if (Integer.parseInt(todayWeather.getPm25()) <= 150)
                    pmImg.setImageResource(R.drawable.biz_plugin_weather_101_150);
                else if (Integer.parseInt(todayWeather.getPm25()) <= 200)
                    pmImg.setImageResource(R.drawable.biz_plugin_weather_151_200);
                else if (Integer.parseInt(todayWeather.getPm25()) <= 300)
                    pmImg.setImageResource(R.drawable.biz_plugin_weather_201_300);
                else if (Integer.parseInt(todayWeather.getPm25()) > 300)
                    pmImg.setImageResource(R.drawable.biz_plugin_weather_greater_300);
            }
            if (todayWeather.getType() != null)
                switch (todayWeather.getType()) {
                    case "暴雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                        break;
                    case "暴雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                        break;
                    case "大暴雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                        break;
                    case "大雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_daxue);
                        break;
                    case "多云":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                        break;
                    case "雷阵雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                        break;
                    case "雷阵雨冰雹":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                        break;
                    case "晴":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_qing);
                        break;
                    case "沙尘暴":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                        break;
                    case "特大暴雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                        break;
                    case "雾":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_wu);
                        break;
                    case "小雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                        break;
                    case "小雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                        break;
                    case "阴":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_yin);
                        break;
                    case "雨加雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                        break;
                    case "阵雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                        break;
                    case "阵雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                        break;
                    case "中雪":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                        break;
                    case "中雨":
                        weatherImg.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                        break;
                }
            ((MyFragment1)fragments.get(0)).update(todayWeather);
            ((MyFragment2)fragments.get(1)).update(todayWeather);
            editor.commit();
            Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFragments(){
        fragments=new ArrayList<Fragment>();
        fragments.add(new MyFragment1());
        fragments.add(new MyFragment2());
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = (ViewPager)findViewById(R.id.viewpager1);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(this);

        dots=new ImageView[fragments.size()];
        for(int i=0;i<fragments.size();i++){
            dots[i]=(ImageView)findViewById(ids[i]);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int a=0;a<ids.length;a++)
            if(a==position)
                dots[a].setImageResource(R.drawable.page_indicator_focused);
            else
                dots[a].setImageResource(R.drawable.page_indicator_unfocused);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}