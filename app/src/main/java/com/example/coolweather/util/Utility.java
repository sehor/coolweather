package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.entity.City;
import com.example.coolweather.entity.County;
import com.example.coolweather.entity.Province;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 彭卓荣 on 2018-02-15.
 */

public class Utility {

    public static boolean handleProvinceResponse(String respone){

    if(!TextUtils.isEmpty(respone)){

        try {
            JSONArray allProvinces=new JSONArray(respone);
            for(int i=0;i<allProvinces.length();i++){
                JSONObject jo=allProvinces.getJSONObject(i);
                Province province=new Province();
                province.setProvinceName(jo.getString("name"));
                province.setProvinceCode(jo.getInt("id"));
                province.save();
            }

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    return false;
    }


    public static boolean handleCityReponse(String response,int provincid){

        if(!TextUtils.isEmpty(response)){

            try {
                JSONArray allCities=new JSONArray(response);
                for (int i=0;i<allCities.length();i++) {

                    JSONObject jo=allCities.getJSONObject(i);
                    City city=new City();

                    city.setCityName(jo.getString("name"));
                    city.setCityCode(jo.getInt("id"));
                    city.setProvinceId(provincid);
                    city.save();
                }

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }



    public static boolean handleCountyReponse(String response,int cityId){

        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties=new JSONArray(response);
                for(int i=0;i<allCounties.length();i++){

                    JSONObject jo=allCounties.getJSONObject(i);
                    County county=new County();

                    county.setCountyName(jo.getString("name"));
                    county.setWeatherId(jo.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public static Weather handleWeatherResponse(String respose){

        try {
            JSONObject jsonObject=new JSONObject(respose);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String  weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
