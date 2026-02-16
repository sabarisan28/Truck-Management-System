package com.trucksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long truckId;
    private String truckNumber;
    private Long driverId;
    private String driverName;
    private String pickupLocation;
    private String dropLocation;
    private String loadType;
    private BigDecimal weight;
    private BigDecimal distance;
    private BigDecimal price;
    private String status;
    private LocalDateTime bookingDate;
}
