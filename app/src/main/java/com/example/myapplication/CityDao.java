package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(City city);

    @Query("SELECT * FROM cities")
    List<City> getAllCities();

    @Query("DELETE FROM cities")
    void deleteAll();


    @Update
    void updateCity(City city);


}
