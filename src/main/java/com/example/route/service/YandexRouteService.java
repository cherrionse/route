package com.example.route.service;

import com.example.route.dto.PointDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YandexRouteService {

    private static final String API_KEY = "ВАШ_YANDEX_API_КЛЮЧ";  // <--- Замени на свой ключ

    public String getTruckRoute(List<PointDTO> points) {
        RestTemplate restTemplate = new RestTemplate();

        String pointsParam = points.stream()
                .map(p -> p.getLongitude() + "," + p.getLatitude())
                .collect(Collectors.joining("~"));

        String url = String.format(
                "https://api.routing.yandex.net/v2/route?" +
                        "apikey=%s&" +
                        "waypoints=%s&" +
                        "vehicle_type=truck&" +
                        "weight=500",  // 500 кг
                API_KEY,
                pointsParam
        );

        return restTemplate.getForObject(url, String.class);
    }
}
