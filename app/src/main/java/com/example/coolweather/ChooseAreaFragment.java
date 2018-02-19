package com.example.coolweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.entity.City;
import com.example.coolweather.entity.County;
import com.example.coolweather.entity.Province;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class ChooseAreaFragment extends Fragment {
    private static final String TAG = "ChooseAreaFragment";

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;
    private City selectedCity;

    private int currentLevel;


    public ChooseAreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_area,
                container, false);

        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvinces();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE){
                  selectedProvince=provinceList.get(position);
                    queryCities();
                }else if(currentLevel==LEVEL_CITY){
                    selectedCity=cityList.get(position);
                    queryCounties();
                }else if(currentLevel==LEVEL_COUNTY){
                    String weatherId=countyList.get(position).getWeatherId();
                    Intent intent=new Intent(getActivity(),WeatherActivity.class);
                    intent.putExtra("weather_id",weatherId);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel==LEVEL_COUNTY){
                    queryCities();
                }else if(currentLevel==LEVEL_CITY){

                    queryProvinces();
                }
            }
        });

    }








    private void queryFromService(String address, final String type) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(getContext(), "load failed!", Toast.LENGTH_LONG
                        ).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String reponseText = response.body().string();
                boolean result = false;

                if ("province".equalsIgnoreCase(type)) {

                    result = Utility.handleProvinceResponse(reponseText);

                } else if ("city".equalsIgnoreCase(type)) {

                    result = Utility.handleCityReponse(reponseText, selectedProvince.getId());
                } else if ("county".equalsIgnoreCase(type)) {

                    result = Utility.handleCountyReponse(reponseText, selectedCity.getId());
                }

                if (result) {
                    getActivity().runOnUiThread(new Runnable() {

                        public void run() {

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            if ("province".equalsIgnoreCase(type)) {

                                queryProvinces();

                            }else if("city".equalsIgnoreCase(type)){
                                Log.d(TAG, "run: "+" recall to queryCities");
                                queryCities();
                            }else if("county".equalsIgnoreCase(type)){
                                Log.d(TAG, "run: "+ "recall to queryCounties");
                                queryCounties();
                            }
                        }
                    });
                }

            }
        });

    }


    private void queryProvinces() {

        titleText.setText("China");
        backButton.setVisibility(View.GONE);
        List<Province> provinces = DataSupport.findAll(Province.class);
        provinceList=provinces;

        if (provinces.size() > 0) {

            dataList.clear();
            for (Province province : provinces) {

                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = 0;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromService(address, "province");
        }
    }

    private void queryCities() {

        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        List<City> cities = DataSupport.where("provinceId=?",
                String.valueOf(selectedProvince.getId())).find(City.class);
        cityList=cities;
        if (cities.size() > 0) {
            dataList.clear();
            for (City city : cities) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            String address = "http://guolin.tech/api/china/"
                    + selectedProvince.getProvinceCode();
            queryFromService(address, "city");
        }
    }

    private void queryCounties() {

        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        List<County> counties = DataSupport.where("cityId=?",
                String.valueOf(selectedCity.getId())).find(County.class);
        countyList=counties;
        if (counties.size() > 0) {
            dataList.clear();
            for (County county : counties) {
                dataList.add(county.getCountyName());
            }
            Log.d(TAG, "queryCounties: "+dataList.get(0));
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            String address = "http://guolin.tech/api/china/"
                    + selectedProvince.getProvinceCode()
                    + "/" + selectedCity.getCityCode();
            queryFromService(address, "county");
        }
    }
}
