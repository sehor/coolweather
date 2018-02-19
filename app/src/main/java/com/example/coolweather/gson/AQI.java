package com.example.coolweather.gson;

/**
 * Created by 彭卓荣 on 2018-02-17.
 */

public class AQI {

    private AQICity aqiCity;

    public class AQICity {


        private String aqi;
        private String pm25;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }
    }


    public AQICity getAqiCity() {
        return aqiCity;
    }

    public void setAqiCity(AQICity aqiCity) {
        this.aqiCity = aqiCity;
    }
}
