package com.trucksystem.service;

import com.trucksystem.dto.BookingRequest;
import com.trucksystem.dto.BookingResponse;
import com.trucksystem.entity.Booking;
import com.trucksystem.entity.Driver;
import com.trucksystem.entity.Payment;
import com.trucksystem.entity.Truck;
import com.trucksystem.entity.User;
import com.trucksystem.exception.ResourceNotFoundException;
import com.trucksystem.repository.BookingRepository;
import com.trucksystem.repository.DriverRepository;
import com.trucksystem.repository.PaymentRepository;
import com.trucksystem.repository.TruckRepository;
import com.trucksystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TruckRepository truckRepository;
    private final DriverRepository driverRepository;
    private final PaymentRepository paymentRepository;
    private final DistanceService distanceService;
    private final EmailService emailService;
    
    @Transactional
    public BookingResponse createBooking(BookingRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Calculate distance
        BigDecimal distance = distanceService.calculateDistance(
                request.getPickupLocation(), 
                request.getDropLocation()
        );
        
        // Calculate price: base rate + distance rate + weight rate
        BigDecimal baseRate = new BigDecimal("50.00");
        BigDecimal distanceRate = distance.multiply(new BigDecimal("2.50"));
        BigDecimal weightRate = request.getWeight().multiply(new BigDecimal("0.50"));
        BigDecimal totalPrice = baseRate.add(distanceRate).add(weightRate);
        
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setPickupLocation(request.getPickupLocation());
        booking.setDropLocation(request.getDropLocation());
        booking.setLoadType(request.getLoadType());
        booking.setWeight(request.getWeight());
        booking.setDistance(distance);
        booking.setPrice(totalPrice);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setBookingDate(LocalDateTime.now());
        
        booking = bookingRepository.save(booking);
        
        // Create payment record
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(totalPrice);
        payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
        paymentRepository.save(payment);
        
        // Send email notification
        emailService.sendBookingConfirmation(user.getEmail(), booking);
        
        return mapToResponse(booking);
    }
    
    public List<BookingResponse> getUserBookings(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return mapToResponse(booking);
    }
    
    @Transactional
    public BookingResponse assignDriver(Long bookingId, Long driverId, Long truckId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        
        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found"));
        
        booking.setDriver(driver);
        booking.setTruck(truck);
        booking.setStatus(Booking.BookingStatus.ASSIGNED);
        
        truck.setAvailabilityStatus(Truck.AvailabilityStatus.ASSIGNED);
        truckRepository.save(truck);
        
        booking = bookingRepository.save(booking);
        return mapToResponse(booking);
    }
    
    @Transactional
    public BookingResponse updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        booking.setStatus(Booking.BookingStatus.valueOf(status));
        
        // If delivered, make truck available again
        if (status.equals("DELIVERED") && booking.getTruck() != null) {
            Truck truck = booking.getTruck();
            truck.setAvailabilityStatus(Truck.AvailabilityStatus.AVAILABLE);
            truckRepository.save(truck);
        }
        
        booking = bookingRepository.save(booking);
        return mapToResponse(booking);
    }
    
    private BookingResponse mapToResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setUserId(booking.getUser().getId());
        response.setUserName(booking.getUser().getName());
        response.setTruckId(booking.getTruck() != null ? booking.getTruck().getId() : null);
        response.setTruckNumber(booking.getTruck() != null ? booking.getTruck().getTruckNumber() : null);
        response.setDriverId(booking.getDriver() != null ? booking.getDriver().getId() : null);
        response.setDriverName(booking.getDriver() != null ? booking.getDriver().getName() : null);
        response.setPickupLocation(booking.getPickupLocation());
        response.setDropLocation(booking.getDropLocation());
        response.setLoadType(booking.getLoadType());
        response.setWeight(booking.getWeight());
        response.setDistance(booking.getDistance());
        response.setPrice(booking.getPrice());
        response.setStatus(booking.getStatus().name());
        response.setBookingDate(booking.getBookingDate());
        return response;
    }
}
