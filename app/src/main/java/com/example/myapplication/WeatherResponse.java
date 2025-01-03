package com.example.myapplication;

import java.util.ArrayList;

public class WeatherResponse {
    private Main main;
    private ArrayList<Weather> weather;
    private Wind wind;

    public Main getMain() {
        return main;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public class Main {
        private float temp;
        private int pressure;
        private int humidity;

        public float getTemp() {
            return temp;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public class Weather {
        private String description;

        public String getDescription() {
            return description;
        }
    }

    public class Wind {
        private float speed;

        public float getSpeed() {
            return speed;
        }
    }
}
