package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<City> cityList;

    public CustomAdapter(Context context, ArrayList<City> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }


        TextView cityName = convertView.findViewById(R.id.cityName);
        TextView temperature = convertView.findViewById(R.id.temperature);
        ImageView weatherIcon = convertView.findViewById(R.id.weatherIcon);


        City city = cityList.get(position);
        cityName.setText(city.getName());
        temperature.setText(city.getTemperature());
        weatherIcon.setImageResource(city.getImage());


        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);



            intent.putExtra("cityName", city.getName());
            intent.putExtra("temperature", city.getTemperature());
            intent.putExtra("description", city.getWeatherDescription());
            intent.putExtra("image", city.getImage());


            intent.putExtra("windSpeed", city.getWindSpeed()); // float
            intent.putExtra("pressure", city.getPressure());   // int
            intent.putExtra("humidity", city.getHumidity());   // int

            context.startActivity(intent);
        });

        return convertView;
    }
}
