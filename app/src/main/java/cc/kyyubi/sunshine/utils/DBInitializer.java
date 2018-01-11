package cc.kyyubi.sunshine.utils;

import java.util.Calendar;
import java.util.Date;

import cc.kyyubi.sunshine.db.AppDatabase;
import cc.kyyubi.sunshine.db.Location;
import cc.kyyubi.sunshine.db.Weather;

/**
 * Created by U0162467 on 12/31/2017.
 */

public class DBInitializer {
    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    public static void populateDB(AppDatabase db) {

        Date today = getTodayPlusDays(0);
        Date tomorrow = getTodayPlusDays(1);
        Date dayAfterTomo = getTodayPlusDays(2);
        Date twoDaysAfterTomo = getTodayPlusDays(3);
        Date threeDaysAfterTomo = getTodayPlusDays(4);
        Date fourDaysAfterTomo = getTodayPlusDays(5);
        Date fiveDaysAfterTomo = getTodayPlusDays(6);


        //db.locationModel().deleteALL();
        //db.weatherModel().deleteAll();

        addLocation(db, 1277333, "bangalore", "Bangalore",
                (long) 77.6033, (long) 12.9762);
        try {
            Thread.sleep(500);

            addWeather(db,1277333, 800, today, "sky is clear",
                    18.51f, 21.53f, 76, 926.51f, 1.21f, 37);
            addWeather(db, 1277333, 800, tomorrow, "sky is clear",
                    13.86f, 27.2f, 60, 928.58f, 2.72f, 126);
            addWeather(db, 1277333, 800, dayAfterTomo, "sky is clear",
                    17.62f, 27.78f, 54, 929.01f, 3.7f, 81);
            addWeather(db, 1277333, 800, twoDaysAfterTomo, "sky is clear",
                    13.87f, 27.06f, 76, 926.51f, 1.21f, 37);
            //Thread.sleep(1000);
//            addWeather(db, 1277333, 501, threeDaysAfterTomo, "moderate rain",
//                    14.18f, 24.29f, 76, 926.51f, 1.21f, 37);
//            addWeather(db, 1277333, 501, fourDaysAfterTomo, "moderate rain",
//                    14.18f, 24.29f, 76, 926.51f, 1.21f, 37);
//            addWeather(db, 1277333, 501, fiveDaysAfterTomo, "moderate rain",
//                    14.18f, 24.29f, 76, 926.51f, 1.21f, 37);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void addLocation(final AppDatabase db, final int id,
                                    final String location_setting,
                                    final String city_name, final long coord_lat,
                                    final long coord_long) {
        Location location = new Location();
        location.id = id;
        location.city_name = city_name;
        location.location_setting = location_setting;
        location.coord_lat = coord_lat;
        location.coord_long = coord_long;

        db.locationModel().insertLocation(location);
    }

    private static void addWeather(final AppDatabase db, final int location_id,
                                   final int weather_id,
                                   final Date date, final String description,
                                   final float mintemp, final float maxtemp,
                                   final int humidity, final float pressure,
                                   final float wind, final float degrees) {
        Weather weather = new Weather();
        weather.location_id = location_id;
        weather.date = date;
        weather.weather_id = weather_id;
        weather.description = description;
        weather.min_temp = mintemp;
        weather.max_temp = maxtemp;
        weather.humidity = humidity;
        weather.pressure = pressure;
        weather.wind = wind;
        weather.degrees = degrees;

        db.weatherModel().insertWeather(weather);

    }


}
