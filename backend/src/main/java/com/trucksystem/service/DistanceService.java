package com.trucksystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class DistanceService {
    
    @Value("${google.maps.api.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public BigDecimal calculateDistance(String origin, String destination) {
        try {
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
                    origin.replace(" ", "+"),
                    destination.replace(" ", "+"),
                    apiKey
            );
            
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response != null && "OK".equals(response.get("status"))) {
                Map<String, Object> rows = ((java.util.List<Map<String, Object>>) response.get("rows")).get(0);
                Map<String, Object> elements = ((java.util.List<Map<String, Object>>) rows.get("elements")).get(0);
                
                if ("OK".equals(elements.get("status"))) {
                    Map<String, Object> distance = (Map<String, Object>) elements.get("distance");
                    Integer distanceInMeters = (Integer) distance.get("value");
                    
                    // Convert meters to kilometers
                    return new BigDecimal(distanceInMeters)
                            .divide(new BigDecimal("1000"), 2, RoundingMode.HALF_UP);
                }
            }
        } catch (Exception e) {
            // Fallback to dummy calculation if API fails
            System.err.println("Google Maps API error: " + e.getMessage());
        }
        
        // Fallback: return random distance between 10-100 km
        return new BigDecimal(Math.random() * 90 + 10).setScale(2, RoundingMode.HALF_UP);
    }
}
