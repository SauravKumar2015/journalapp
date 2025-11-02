package com.saurav.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    private Current current;



    @Getter
    @Setter
    public static class Current {

        private int temperature;
        @JsonProperty("weather_code")
        private int weatherCode;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;
    }

}