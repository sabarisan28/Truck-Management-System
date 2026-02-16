package com.trucksystem.repository;

import com.trucksystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatus(Booking.BookingStatus status);
    
    @Query("SELECT SUM(b.price) FROM Booking b WHERE b.status = 'DELIVERED'")
    BigDecimal calculateTotalRevenue();
    
    @Query("SELECT COUNT(b) FROM Booking b")
    Long countTotalBookings();
}
