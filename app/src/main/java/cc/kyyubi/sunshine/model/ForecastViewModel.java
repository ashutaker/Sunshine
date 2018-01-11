package cc.kyyubi.sunshine.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cc.kyyubi.sunshine.db.AppDatabase;
import cc.kyyubi.sunshine.db.Weather;
import cc.kyyubi.sunshine.utils.DBInitializer;



/**
 * Created by u0162467 on 1/2/2018.
 */

public class ForecastViewModel extends AndroidViewModel {

    public final java.util.List<Weather> weatherList;
    public String weatherDetail;

    private AppDatabase mDb;
    private String location = PreferenceManager.getDefaultSharedPreferences(this.getApplication())
            .getString("location", "Bangalore");

     public ForecastViewModel(Application application) {
        super(application);
        createDB();

        weatherList = mDb.weatherModel().loadWeatherByLocation(location);


    }

    public String getWeatherDetail(int position) {
        String date = getDate(position);

        Weather weatherByDate = mDb.weatherModel().loadWeatherByLocationAndDate(location,date);
        weatherDetail = weatherByDate.description;
        return weatherDetail;
    }

    private String getDate(int position) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DATE, position);
        return df.format(calendar.getTime());
    }

    public void createDB() {
        mDb = AppDatabase.getInMemoryDatabase(this.getApplication());
        DBInitializer.populateDB(mDb);
    }
}
