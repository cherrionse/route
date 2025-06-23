package com.example.route.controller;

import com.example.route.dto.PointDTO;
import com.example.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public List<String> getRoute(@RequestBody List<PointDTO> points) {
        return routeService.buildRoute(points);
    }
}