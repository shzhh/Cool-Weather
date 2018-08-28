package com.shzhh.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.shzhh.coolweather.db.City;
import com.shzhh.coolweather.db.County;
import com.shzhh.coolweather.db.Province;
import com.shzhh.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONObject;


public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces=new JSONArray(response);
                for (int i=0;i<allProvinces.length();i++){
                    JSONObject provinceJson=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceCode(provinceJson.getInt("id"));
                    province.setProvinceName(provinceJson.getString("name"));
                    province.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities=new JSONArray(response);
                for (int i=0;i<allCities.length();i++){
                    JSONObject cityJson=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityCode(cityJson.getInt("id"));
                    city.setCityName(cityJson.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties=new JSONArray(response);
                for (int i=0;i<allCounties.length();i++){
                    JSONObject countyJson=allCounties.getJSONObject(i);
                    County county=new County();
                    county.setWeatherId(countyJson.getString("weather_id"));
                    county.setCountyName(countyJson.getString("name"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 返回的json数据解析成实体类Weather
     */
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getString(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
