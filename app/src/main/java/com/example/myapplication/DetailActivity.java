package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        TextView cityName = findViewById(R.id.detailCityName);
        TextView temperature = findViewById(R.id.detailTemperature);
        TextView description = findViewById(R.id.detailDescription);
        ImageView weatherIcon = findViewById(R.id.detailWeatherIcon);
        TextView windSpeed = findViewById(R.id.detailWindSpeed);
        TextView pressure = findViewById(R.id.detailPressure);
        TextView humidity = findViewById(R.id.detailHumidity);

        Intent intent = getIntent();

        // Şehir bilgileri
        cityName.setText(intent.getStringExtra("cityName") != null
                ? intent.getStringExtra("cityName")
                : "Unknown City");
        temperature.setText(intent.getStringExtra("temperature") != null
                ? intent.getStringExtra("temperature")
                : "--°C");
        description.setText(intent.getStringExtra("description") != null
                ? intent.getStringExtra("description")
                : "No description");

        // İkon
        int imageResource = intent.getIntExtra("image", R.drawable.default_image);
        weatherIcon.setImageResource(imageResource);

        // Rüzgar hızı (float)
        float windSpeedVal = intent.getFloatExtra("windSpeed", 0f);
        windSpeed.setText("Wind Speed: " + windSpeedVal + " m/s");

        // Basınç (int)
        int pressureVal = intent.getIntExtra("pressure", -1);
        pressure.setText(pressureVal != -1
                ? "Pressure: " + pressureVal + " hPa"
                : "Pressure: -- hPa");

        // Nem (int)
        int humidityVal = intent.getIntExtra("humidity", -1);
        humidity.setText(humidityVal != -1
                ? "Humidity: " + humidityVal + "%"
                : "Humidity: --%");


        weatherIcon.setAlpha(0f);
        weatherIcon.animate().alpha(1f).setDuration(1000);

        cityName.setTranslationY(100f);
        cityName.animate().translationY(0f).setDuration(800);

        temperature.setTranslationY(100f);
        temperature.animate().translationY(0f).setDuration(1000);

        description.setTranslationY(100f);
        description.animate().translationY(0f).setDuration(1200);

        windSpeed.setTranslationY(100f);
        windSpeed.animate().translationY(0f).setDuration(1400);

        pressure.setTranslationY(100f);
        pressure.animate().translationY(0f).setDuration(1600);

        humidity.setTranslationY(100f);
        humidity.animate().translationY(0f).setDuration(1800);
    }
}
