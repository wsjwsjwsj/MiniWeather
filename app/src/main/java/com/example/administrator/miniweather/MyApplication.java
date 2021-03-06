package com.example.administrator.miniweather;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MyApplication extends Application{

    private static MyApplication myApplication;
    private CityDB cityDB;
    private List<City> cityList;
    public HashMap<String,String> data=new HashMap<String, String>();
    @Override
    public void onCreate() {
        super.onCreate();
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                Log.d("deviceToken",deviceToken);
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d("deviceToken","fail");
            }
        });

        myApplication=this;
        cityDB=openCityDB();
        initCityList();
    }

    private void initCityList() {
        cityList=new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }

    private boolean prepareCityList() {
        cityList=cityDB.getAllCity();
        int i=0;
        for(City city:cityList){
            String cityName=city.getCity();
            String cityNumber=city.getNumber();
            i++;
            data.put(cityName,cityNumber);
        }
        Log.d("MyApplication","i="+i);
        return true;
    }

    public  HashMap<String,String> getCityMap(){
        return data;
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    private CityDB openCityDB() {
        String path = "/data" + Environment.getDataDirectory().getAbsolutePath() + File.separator + getPackageName() + File.separator + "databases1" + File.separator + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d("MyApplication",path);
        if (!db.exists()) {
            String pathfolder = "/data" + Environment.getDataDirectory().getAbsolutePath() + File.separator + getPackageName() + File.separator + "databases1" + File.separator;
            File dirFirstFolder = new File(pathfolder);
            if(!dirFirstFolder.exists()){
                dirFirstFolder.mkdirs();
                Log.i("MyApp","mkdirs");
            }
            Log.i("MyApp","db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }
}
