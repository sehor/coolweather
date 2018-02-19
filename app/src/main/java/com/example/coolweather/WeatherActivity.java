package com.example.coolweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private LinearLayout forecastLayout;
    private TextView titleCity;
    private TextView titleUpdatTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // initial view
        weatherLayout=findViewById(R.id.weather_layout);
        forecastLayout=findViewById(R.id.forecast_layout);
        titleCity=findViewById(R.id.city_title);
        titleUpdatTime=findViewById(R.id.title_update_time);
        degreeText=findViewById(R.id.degree);
        weatherInfoText=findViewById(R.id.weather_info_text);
        aqiText=findViewById(R.id.aqi_text);
        pm25Text=findViewById(R.id.pm25_text);
        comfortText=findViewById(R.id.comfort_text);
        carWashText=findViewById(R.id.car_wash_text);
        sportText=findViewById(R.id.sport_text);

        //缓存
        SharedPreferences preferences=
                PreferenceManager.getDefaultSharedPreferences(this);

        String weatherString =preferences.getString("weather",null);
        weatherString=null;
        if(weatherString!=null){
            Weather weather= Utility.handleWeatherResponse(weatherString);
            System.out.println("have some weather?");
            showWeatherInfo(weather);
        }else {
            // 无缓存时去服务器取数
            String weatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }

    }


    private void requestWeather(String weatherId){

        String weatherUrl="http://guolin.tech/api/weather?" +
                "cityid="+weatherId+"&key=93a755669ce74c049e6c07fec77734d3";
        Log.d("WeatherActivity", "requestWeather() called with: weatherId = [" + weatherUrl + "]");
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,"获取天气信息失败"
                        ,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

//                final String responseText=response.body().string();
                final String responseText="{ \"HeWeather\": [\n" +
                        "\n" +
                        "        {  \"status\":\"ok\",\n" +
                        "\n" +
                        "\"basic\":{\n" +
                        "  \"city\":\"shenzhen\",\n" +
                        "  \"id\":\"CN100100000\",\n" +
                        "  \"update\":{ \"log\": \"2018-02-20\"}\n" +
                        "},\n" +
                        "\n" +
                        "\"aqi\":{\n" +
                        "   \"city\" :{ \"aqi\":\"44\", \"pm25\":\"13\"}\n" +
                        "},\n" +
                        "\n" +
                        "\"now\" :{ \"temp\":\"29\", \"cond\":{\"txt\":\"Rain\"} },\n" +
                        "\n" +
                        "\"suggestion\": {\"comfort\":{\"txt\":\"白天天气较热，但雨水带来些许凉意。\"},\"cw\":{\"txt\": \"不宜洗车\"},\"sport\":{\"txt\":\"室内做低强度运动\"}},\n" +
                        "\n" +
                        "\"daily_forecast\" :[\n" +
                        "  \n" +
                        "     { \"date\": \"2018-02-21\", \"cond\": {\"txt_d\":\"Rain\"},\"tem\":{\"max\":\"29\", \"min\":\"20\"}},\n" +
                        "\n" +
                        "     { \"date\": \"2018-02-22\", \"cond\": {\"txt_d\":\"Sunny and Rain\"},\"tem\":{\"max\":\"43\", \"min\":\"20\"}},\n" +
                        " \n" +
                        "      { \"date\": \"2018-02-23\", \"cond\": {\"txt_d\":\"Cloudy all day\"},\"tem\":{\"max\":\"24\", \"min\":\"15\"}}\n" +
                        "                    \n" +
                        "\n" +
                        "                  ]\n" +
                        "\n" +
                        " }\n" +
                        "               ]\n" +
                        "\n" +
                        "  }\n";
                final Weather weather=Utility.handleWeatherResponse(responseText);
                Log.d("WeatherActivity", "response = [" + responseText + "]");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(weather!=null&&"ok".equalsIgnoreCase(weather.getStatus())){
                            SharedPreferences.Editor editor=PreferenceManager
                                    .getDefaultSharedPreferences(
                                            WeatherActivity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        }else {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败"
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


    private void showWeatherInfo(Weather weather){

        titleCity.setText(weather.getBasic().getCityName());
        titleUpdatTime.setText(weather.getBasic().getUpdate().getUpdateTime()
        );
        degreeText.setText(weather.getNow().getTemperature()+"℃");
        weatherInfoText.setText(weather.getNow().getMore().getInfo());

        forecastLayout.removeAllViews();

        for(Forecast forecast:weather.getForecastList()){

            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,
                    forecastLayout,false);

            TextView dateText=view.findViewById(R.id.date_text);
            System.out.println(forecast.getDate());
            dateText.setText(forecast.getDate());

            TextView infoText=view.findViewById(R.id.info_text);
            TextView maxText=view.findViewById(R.id.max_text);
            TextView minText=view.findViewById(R.id.min_text);

            infoText.setText(forecast.getMore().getInfo());
            maxText.setText(forecast.getTemperature().getMax());
            minText.setText(forecast.getTemperature().getMax());

            forecastLayout.addView(view);

        }

        if(weather.getAqi()!=null){

            aqiText.setText(weather.getAqi().getAqiCity().getAqi());
            pm25Text.setText(weather.getAqi().getAqiCity().getPm25()
            );
        }

        String comfort="舒适度："+weather.getSuggestion().getComfort().getInfo();
        String carWash="洗车指数："+weather.getSuggestion().getCarWash().getInfo();
        String sport="运动指数："+weather.getSuggestion().getSport().getInfo();
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}
