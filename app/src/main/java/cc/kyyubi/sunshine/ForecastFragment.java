package cc.kyyubi.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.kyyubi.sunshine.model.ForecastResponse;
import cc.kyyubi.sunshine.networking.ForecastAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by U0162467 on 12/7/2017.
 */

public class ForecastFragment extends Fragment {

    private String LOG_TAG = ForecastFragment.class.getSimpleName();
    private ListView listView;
    private static List<cc.kyyubi.sunshine.model.List> weatherStore;
    private ForecastResponse forecastResponse;
    ArrayAdapter<String> mForecastAdapter;

    private String city = "Bangalore";
    private int days = 7;
    private String units = "metric";


    public ForecastFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
        MenuItem item = menu.findItem(R.id.action_share);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh) {

            updateFromAPI();
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

//        final String[] forecastArray = {
//                "Mon 6/23 - Sunny - 31/17",
//                "Tue 6/24 - Foggy - 21/8",
//                "Wed 6/25 - Cloudy - 22/17",
//                "Thurs 6/26 - Rainy - 18/11",
//                "Fri 6/27 - Foggy - 21/10",
//                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
//                "Sun 6/29 - Sunny - 20/7"
//        };

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity()
                , R.layout.list_item_forecast
                , R.id.list_item_forecast_textview
                , new ArrayList<String>());

        listView = (ListView) rootview.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String forecast = mForecastAdapter.getItem(position);
                Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intentDetail = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intentDetail);
            }
        });


        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateFromAPI();
    }

    private void updateFromAPI() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        city = sharedPreferences.getString(getString(R.string.pref_location_key)
                , getString(R.string.pref_location_default));


        Call<ForecastResponse> call = ForecastAPI.getApi().getForecast(city, days, units);
        call.enqueue(new Callback<ForecastResponse>() {

            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                forecastResponse = response.body();
                Toast.makeText(getActivity(), "RESPONSE RECEIVED", Toast.LENGTH_SHORT).show();

                Log.d(LOG_TAG, forecastResponse.getCity().getName());
                String[] result = getWeatherDataList(forecastResponse, days);
                mForecastAdapter.clear();

                mForecastAdapter.addAll(result);
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error in response", Toast.LENGTH_LONG).show();

            }
        });
    }

    private String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        units = sharedPreferences.getString(getString(R.string.pref_units_key)
                , getString(R.string.pref_units_default));

        if (units.equals(getString(R.string.pref_units_imperial))) {
            high = (high * 1.8) + 32;
            low = (low * 1.8) + 32;
        } else if (!units.equals(getString(R.string.pref_units_metric))) {
            Log.d(LOG_TAG, "Unit Type not found: " + units);
        }

        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    private String[] getWeatherDataList(ForecastResponse forecastData, int days) {

        Time dayTime = new Time();
        dayTime.setToNow();

        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        dayTime = new Time();

        String[] resultStr = new String[days];
        for (int i = 0; i < days; i++) {
            String day;
            String description;
            String highLow;

            long dateTime = dayTime.setJulianDay(julianStartDay + i);
            day = getReadableDateString(dateTime);

            Double high = forecastData.getList().get(i).getTemp().getMax();
            //Log.d(LOG_TAG,String.valueOf(forecastData.getList().get(i).getTemp().getMax()));
            Double low = forecastData.getList().get(i).getTemp().getMin();

            highLow = formatHighLows(high, low);

            description = forecastData.getList().get(i).getWeather().get(0).getMain();
            resultStr[i] = day + " - " + description + " - " + highLow;
        }
        return resultStr;
    }

}