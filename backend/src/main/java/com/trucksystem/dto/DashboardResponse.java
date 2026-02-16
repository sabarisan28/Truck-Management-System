package com.trucksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalUsers;
    private Long totalBookings;
    private BigDecimal totalRevenue;
    private Long pendingBookings;
    private Long completedBookings;
}
