package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 彭卓荣 on 2018-02-19.
 */

public class Suggestion {

    @SerializedName("comf")
    private Comfort comfort;

    public Suggestion() {
    }

    public class Comfort{
        @SerializedName("txt")
        private String info;
    }

    @SerializedName("cw")
    private CarWash carWash;
    public class CarWash{
        @SerializedName("txt")
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    private Sport sport;
    public class Sport{
        @SerializedName("txt")
        private String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }




    public Comfort getComfort() {
        return comfort;
    }

    public void setComfort(Comfort comfort) {
        this.comfort = comfort;
    }

    public CarWash getCarWash() {
        return carWash;
    }

    public void setCarWash(CarWash carWash) {
        this.carWash = carWash;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }


}
