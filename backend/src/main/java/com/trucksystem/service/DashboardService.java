package com.trucksystem.service;

import com.trucksystem.dto.DashboardResponse;
import com.trucksystem.entity.Booking;
import com.trucksystem.repository.BookingRepository;
import com.trucksystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    
    public DashboardResponse getDashboardStats() {
        Long totalUsers = userRepository.count();
        Long totalBookings = bookingRepository.countTotalBookings();
        BigDecimal totalRevenue = bookingRepository.calculateTotalRevenue();
        
        if (totalRevenue == null) {
            totalRevenue = BigDecimal.ZERO;
        }
        
        Long pendingBookings = (long) bookingRepository.findByStatus(Booking.BookingStatus.PENDING).size();
        Long completedBookings = (long) bookingRepository.findByStatus(Booking.BookingStatus.DELIVERED).size();
        
        return new DashboardResponse(totalUsers, totalBookings, totalRevenue, pendingBookings, completedBookings);
    }
}
