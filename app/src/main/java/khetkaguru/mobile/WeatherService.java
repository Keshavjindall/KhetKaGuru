package khetkaguru.mobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather")
    Call<WeatherResponse> getWeatherByCity(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
