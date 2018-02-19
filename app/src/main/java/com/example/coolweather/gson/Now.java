package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 彭卓荣 on 2018-02-17.
 */

public class Now {
    @SerializedName("temp")
    private String temperature;


    @SerializedName("cond")
    private More more;

    public class More {
        @SerializedName("txt_d")
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public More getMore() {
        return more;
    }

    public void setMore(More more) {
        this.more = more;
    }
}
