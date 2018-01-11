package cc.kyyubi.sunshine.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by U0162467 on 12/31/2017.
 */
@Dao
@TypeConverters(DateConverter.class)
public interface WeatherDao {

    @Query("SELECT * FROM weather " +
            "INNER JOIN Location ON Location.id = Weather.location_id " +
            "WHERE Location.location_setting LIKE :location")
   List<Weather> loadWeatherByLocation(String location);

    @Query("SELECT * FROM weather " +
            "INNER JOIN Location ON Location.id = Weather.location_id " +
            "WHERE Location.location_setting LIKE :location " +
            "AND date = :date")
    Weather loadWeatherByLocationAndDate(String location, String date);

    @Insert(onConflict = REPLACE)
    void insertWeather(Weather weather);

    @Query("DELETE FROM Weather")
    void deleteAll();
}
