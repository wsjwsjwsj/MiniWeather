package com.example.administrator.miniweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/15.
 */
public class MyFragment1 extends android.support.v4.app.Fragment {
    private ImageView weatherImg1,weatherImg2;
    private TextView date1,date2,type1,type2,temperature1,temperature2,wind1,wind2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.weather1, container, false);
        weatherImg1=(ImageView)view.findViewById(R.id.weather_img1);
        weatherImg2=(ImageView)view.findViewById(R.id.weather_img2);
        date1=(TextView)view.findViewById(R.id.week_today1);
        date2=(TextView)view.findViewById(R.id.week_today2);
        type1=(TextView)view.findViewById(R.id.climate1);
        type2=(TextView)view.findViewById(R.id.climate2);
        temperature1=(TextView)view.findViewById(R.id.temperature1);
        temperature2=(TextView)view.findViewById(R.id.temperature2);
        wind1=(TextView)view.findViewById(R.id.wind1);
        wind2=(TextView)view.findViewById(R.id.wind2);
        return view;
    }

    public void update(TodayWeather todayWeather) {
        if (todayWeather != null) {
            date1.setText(todayWeather.futureWeather[0].getDate());
            temperature1.setText(todayWeather.futureWeather[0].getHigh() + "~" + todayWeather.futureWeather[0].getLow());
            type1.setText(todayWeather.futureWeather[0].getType());
            wind1.setText(todayWeather.futureWeather[0].getFengli());
            if (todayWeather.futureWeather[0].getType() != null)
                switch (todayWeather.futureWeather[0].getType()) {
                    case "暴雪":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                        break;
                    case "暴雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                        break;
                    case "大暴雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                        break;
                    case "大雪":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_daxue);
                        break;
                    case "多云":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                        break;
                    case "雷阵雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                        break;
                    case "雷阵雨冰雹":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                        break;
                    case "晴":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_qing);
                        break;
                    case "沙尘暴":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                        break;
                    case "特大暴雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                        break;
                    case "雾":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_wu);
                        break;
                    case "小雪":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                        break;
                    case "小雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                        break;
                    case "阴":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_yin);
                        break;
                    case "雨加雪":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                        break;
                    case "阵雪":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                        break;
                    case "阵雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                        break;
                    case "中雪":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                        break;
                    case "中雨":
                        weatherImg1.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                        break;
                }
            date2.setText(todayWeather.futureWeather[1].getDate());
            temperature2.setText(todayWeather.futureWeather[1].getHigh() + "~" + todayWeather.futureWeather[1].getLow());
            type2.setText(todayWeather.futureWeather[1].getType());
            wind2.setText(todayWeather.futureWeather[1].getFengli());
            if (todayWeather.futureWeather[1].getType() != null)
                switch (todayWeather.futureWeather[1].getType()) {
                    case "暴雪":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_baoxue);
                        break;
                    case "暴雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_baoyu);
                        break;
                    case "大暴雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_dabaoyu);
                        break;
                    case "大雪":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_daxue);
                        break;
                    case "多云":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_duoyun);
                        break;
                    case "雷阵雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_leizhenyu);
                        break;
                    case "雷阵雨冰雹":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_leizhenyubingbao);
                        break;
                    case "晴":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_qing);
                        break;
                    case "沙尘暴":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_shachenbao);
                        break;
                    case "特大暴雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_tedabaoyu);
                        break;
                    case "雾":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_wu);
                        break;
                    case "小雪":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_xiaoxue);
                        break;
                    case "小雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_xiaoyu);
                        break;
                    case "阴":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_yin);
                        break;
                    case "雨加雪":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_yujiaxue);
                        break;
                    case "阵雪":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_zhenxue);
                        break;
                    case "阵雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_zhenyu);
                        break;
                    case "中雪":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_zhongxue);
                        break;
                    case "中雨":
                        weatherImg2.setImageResource(R.drawable.biz_plugin_weather_zhongyu);
                        break;
                }
        }
    }
}
