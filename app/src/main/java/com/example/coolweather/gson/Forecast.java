package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 彭卓荣 on 2018-02-19.
 */

public class Forecast {
    private String date;


    @SerializedName("tem")
    private Temperature temperature;

    public class Temperature {
        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }


    @SerializedName("cond")
    private Now.More more;


    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Now.More getMore() {
        return more;
    }

    public void setMore(Now.More more) {
        this.more = more;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
