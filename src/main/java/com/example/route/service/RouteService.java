package com.example.route.service;

import com.example.route.dto.PointDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private static final int EARTH_RADIUS_KM = 6371;

    public double calculateDistance(PointDTO p1, PointDTO p2) {
        double latDistance = Math.toRadians(p2.getLatitude() - p1.getLatitude());
        double lonDistance = Math.toRadians(p2.getLongitude() - p1.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(p1.getLatitude())) * Math.cos(Math.toRadians(p2.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public List<String> buildRoute(List<PointDTO> points) {
        List<String> steps = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            PointDTO from = points.get(i);
            PointDTO to = points.get(i + 1);
            double dist = calculateDistance(from, to);
            steps.add(String.format("От (%.4f, %.4f) до (%.4f, %.4f): %.2f км",
                    from.getLatitude(), from.getLongitude(),
                    to.getLatitude(), to.getLongitude(),
                    dist));
        }
        return steps;
    }
}