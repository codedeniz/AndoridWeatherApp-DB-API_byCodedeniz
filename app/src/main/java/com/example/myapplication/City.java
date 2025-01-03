package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "cities",
        indices = {@Index(value = "name", unique = true)}
)
public class City {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String name;
    private final String temperature;
    private final String weatherDescription;
    private final int image;

    private final float windSpeed;
    private final int pressure;
    private final int humidity;


    private final long lastUpdate;

    public City(String name,
                String temperature,
                String weatherDescription,
                int image,
                float windSpeed,
                int pressure,
                int humidity,
                long lastUpdate
    ) {
        this.name = name;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.image = image;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.lastUpdate = lastUpdate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getTemperature() { return temperature; }
    public String getWeatherDescription() { return weatherDescription; }
    public int getImage() { return image; }
    public float getWindSpeed() { return windSpeed; }
    public int getPressure() { return pressure; }
    public int getHumidity() { return humidity; }

    public long getLastUpdate() { return lastUpdate; }
}
