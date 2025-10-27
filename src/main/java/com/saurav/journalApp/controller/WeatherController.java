package com.saurav.journalApp.controller;

import com.saurav.journalApp.service.WeatherService;
import com.saurav.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public String getWeather(@PathVariable String city) {
        WeatherResponse response = weatherService.getWeather(city);

        if (response == null || response.getCurrent() == null) {
            return "Weather data not available for " + city;
        }

        int temperature = response.getCurrent().getTemperature();
        int feelsLike = response.getCurrent().getFeelslike();

        return "Temperature: " + temperature + "°C, Feels like: " + feelsLike + "°C";
    }
}
