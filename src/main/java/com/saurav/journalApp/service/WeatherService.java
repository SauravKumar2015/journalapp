package com.saurav.journalApp.service;

import com.saurav.journalApp.api.response.WeatherResponse;
import com.saurav.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private  String api_key;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("Weather_of_" + city, WeatherResponse.class);
        if(weatherResponse != null) {
            return weatherResponse;
        }

        String apiTemplate = appCache.appCache.get(AppCache.keys.WEATHER_API.toString());
        if (apiTemplate == null || apiTemplate.isBlank()) {
            apiTemplate = weatherApiUrl;
        }

        String finalAPI = apiTemplate.replace("<city>", city);
        if (finalAPI.contains("<apiKey>")) {
            finalAPI = finalAPI.replace("<apiKey>", api_key);
        } else {
            finalAPI = finalAPI.replaceAll("(?i)(access_key=)[^&]+", "$1" + api_key);
        }

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        if(body != null) {
            redisService.set("Weather_of_" + city, body, 300L);
        }
        return body;
    }
}
