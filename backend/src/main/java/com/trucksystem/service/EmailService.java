package com.trucksystem.service;

import com.trucksystem.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    public void sendBookingConfirmation(String toEmail, Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Booking Confirmation - Truck Management System");
            message.setText(String.format(
                    "Dear Customer,\n\n" +
                    "Your booking has been confirmed!\n\n" +
                    "Booking ID: %d\n" +
                    "Pickup Location: %s\n" +
                    "Drop Location: %s\n" +
                    "Load Type: %s\n" +
                    "Weight: %.2f tons\n" +
                    "Distance: %.2f km\n" +
                    "Total Price: $%.2f\n" +
                    "Status: %s\n\n" +
                    "Thank you for choosing our service!\n\n" +
                    "Best regards,\n" +
                    "Truck Management Team",
                    booking.getId(),
                    booking.getPickupLocation(),
                    booking.getDropLocation(),
                    booking.getLoadType(),
                    booking.getWeight(),
                    booking.getDistance(),
                    booking.getPrice(),
                    booking.getStatus()
            ));
            
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
