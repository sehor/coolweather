package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 彭卓荣 on 2018-02-17.
 */

public class Basic {

    @SerializedName("city")
    private String cityName;

    @SerializedName("id")
    private String weatherId;


    private Update update;

    public class Update {

        @SerializedName("log")
        private String updateTime;

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
