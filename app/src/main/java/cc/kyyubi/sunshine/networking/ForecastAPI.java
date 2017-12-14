package cc.kyyubi.sunshine.networking;

import cc.kyyubi.sunshine.model.ForecastResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by U0162467 on 12/7/2017.
 * http://api.openweathermap.org/data/2.5/forecast/daily?q=Bangalore&appid=677dde5e43a2c9b04fd684363104f894&units=metric&cnt=5
 */

public class ForecastAPI {
    private static final String API_KEY = "677dde5e43a2c9b04fd684363104f894";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private static ForecastService forecastService = null;

    public static ForecastService getApi(){
        if(forecastService== null){
            Retrofit retrofit =  new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            forecastService = retrofit.create(ForecastService.class);
        }
        return forecastService;
    }

    public interface ForecastService {

        //Receive Forecast for the specified city and no of days
        @GET("forecast/daily?appid=" + API_KEY)
        Call<ForecastResponse> getForecast(@Query("q") String city, @Query("cnt") int days, @Query("units") String units);
    }
}
