package com.example.route.controller;

import com.example.route.dto.PointDTO;
import com.example.route.service.RouteService;
import com.example.route.service.YandexRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteService routeService;
    private final YandexRouteService yandexRouteService;

    public RouteController(RouteService routeService, YandexRouteService yandexRouteService) {
        this.routeService = routeService;
        this.yandexRouteService = yandexRouteService;
    }

    @PostMapping
    public List<String> getRoute(@RequestBody List<PointDTO> points) {
        return routeService.buildRoute(points);
    }
    @PostMapping("/save")
    public String saveRoute(@RequestBody List<PointDTO> points) {
        StringBuilder sb = new StringBuilder();
        for (PointDTO point : points) {
            sb.append(point.getLatitude()).append(",").append(point.getLongitude()).append("\n");
        }

        try {
            Files.write(
                    Paths.get("saved_routes.txt"),
                    sb.toString().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка сохранения";
        }

        return "Сохранено";
    }

    @PostMapping("/export-json")
    public String exportJsonToFile(@RequestBody String json) {
        try {
            Files.write(
                    Paths.get("exported_route.json"),
                    json.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка";
        }
        return "OK";
    }

    @PostMapping("/truck")
    public String getTruckRoute(@RequestBody List<PointDTO> points) {
        return yandexRouteService.getTruckRoute(points);
    }
}
