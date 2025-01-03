package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    ArrayList<City> cityList;
    CustomAdapter adapter;
    Button clearHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        clearHistoryButton = findViewById(R.id.clearHistoryButton);

        cityList = new ArrayList<>();
        adapter = new CustomAdapter(this, cityList);
        listView.setAdapter(adapter);

        loadCitiesFromDB();
        setupSearchView();

        clearHistoryButton.setOnClickListener(v -> clearHistory());
    }

    private void loadCitiesFromDB() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<City> dbCities = DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .cityDao()
                    .getAllCities();

            runOnUiThread(() -> {
                cityList.clear();
                cityList.addAll(dbCities);
                adapter.notifyDataSetChanged();
            });
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchWeatherData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<WeatherResponse> call = weatherAPI.getWeather(
                cityName,
                "dbdce6700c9647758178dbfbdab9d178",
                "metric"
        );

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse data = response.body();

                    float windSpeedVal = data.getWind().getSpeed();
                    int pressureVal = data.getMain().getPressure();
                    int humidityVal = data.getMain().getHumidity();
                    Log.d("WeatherDebug", "windSpeedVal = " + windSpeedVal);
                    Log.d("WeatherDebug", "pressureVal = " + pressureVal);
                    Log.d("WeatherDebug", "humidityVal = " + humidityVal);

                    String temp = data.getMain().getTemp() + "°C";
                    String desc = capitalizeWords(data.getWeather().get(0).getDescription());
                    int image = getWeatherIcon(desc);


                    City newCity = new City(
                            capitalizeWords(cityName),
                            temp,
                            desc,
                            image,
                            windSpeedVal,
                            pressureVal,
                            humidityVal,
                            System.currentTimeMillis()
                    );

                    Executors.newSingleThreadExecutor().execute(() -> {
                        DatabaseClient.getInstance(getApplicationContext())
                                .getAppDatabase()
                                .cityDao()
                                .insert(newCity);

                        loadCitiesFromDB();
                    });

                } else {
                    Log.e("API Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("API Error", t.getMessage() != null ? t.getMessage() : "Unknown error");
            }
        });
    }

    private void clearHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .cityDao()
                    .deleteAll();

            runOnUiThread(() -> {
                cityList.clear();
                adapter.notifyDataSetChanged();
            });
        });
    }

    private int getWeatherIcon(String description) {
        description = description.toLowerCase();
        if (description.contains("clear")) return R.drawable.sunny;
        if (description.contains("cloud")) return R.drawable.cloudy;
        if (description.contains("rain")) return R.drawable.rainy;
        if (description.contains("snow")) return R.drawable.snowy;
        if (description.contains("mist")) return R.drawable.fog;
        if (description.contains("fog")) return R.drawable.fog;
        return R.drawable.default_image;
    }

    private String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) return "";
        String[] words = input.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return capitalized.toString().trim();
    }
}
