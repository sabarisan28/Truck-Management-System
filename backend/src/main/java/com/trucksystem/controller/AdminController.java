package com.trucksystem.controller;

import com.trucksystem.dto.*;
import com.trucksystem.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin", description = "Admin management APIs")
public class AdminController {
    
    private final TruckService truckService;
    private final DriverService driverService;
    private final BookingService bookingService;
    private final DashboardService dashboardService;
    
    // Dashboard
    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard statistics")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
    
    // Truck Management
    @GetMapping("/trucks")
    @Operation(summary = "Get all trucks")
    public ResponseEntity<List<TruckDTO>> getAllTrucks() {
        return ResponseEntity.ok(truckService.getAllTrucks());
    }
    
    @GetMapping("/trucks/{id}")
    @Operation(summary = "Get truck by ID")
    public ResponseEntity<TruckDTO> getTruckById(@PathVariable Long id) {
        return ResponseEntity.ok(truckService.getTruckById(id));
    }
    
    @PostMapping("/trucks")
    @Operation(summary = "Create a new truck")
    public ResponseEntity<TruckDTO> createTruck(@Valid @RequestBody TruckDTO dto) {
        return ResponseEntity.ok(truckService.createTruck(dto));
    }
    
    @PutMapping("/trucks/{id}")
    @Operation(summary = "Update truck")
    public ResponseEntity<TruckDTO> updateTruck(@PathVariable Long id, @Valid @RequestBody TruckDTO dto) {
        return ResponseEntity.ok(truckService.updateTruck(id, dto));
    }
    
    @DeleteMapping("/trucks/{id}")
    @Operation(summary = "Delete truck")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        truckService.deleteTruck(id);
        return ResponseEntity.noContent().build();
    }
    
    // Driver Management
    @GetMapping("/drivers")
    @Operation(summary = "Get all drivers")
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }
    
    @GetMapping("/drivers/{id}")
    @Operation(summary = "Get driver by ID")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }
    
    @PostMapping("/drivers")
    @Operation(summary = "Create a new driver")
    public ResponseEntity<DriverDTO> createDriver(@Valid @RequestBody DriverDTO dto) {
        return ResponseEntity.ok(driverService.createDriver(dto));
    }
    
    @PutMapping("/drivers/{id}")
    @Operation(summary = "Update driver")
    public ResponseEntity<DriverDTO> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverDTO dto) {
        return ResponseEntity.ok(driverService.updateDriver(id, dto));
    }
    
    @DeleteMapping("/drivers/{id}")
    @Operation(summary = "Delete driver")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
    
    // Booking Management
    @GetMapping("/bookings")
    @Operation(summary = "Get all bookings")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @PutMapping("/bookings/{id}/assign")
    @Operation(summary = "Assign driver and truck to booking")
    public ResponseEntity<BookingResponse> assignDriver(
            @PathVariable Long id,
            @RequestParam Long driverId,
            @RequestParam Long truckId) {
        return ResponseEntity.ok(bookingService.assignDriver(id, driverId, truckId));
    }
    
    @PutMapping("/bookings/{id}/status")
    @Operation(summary = "Update booking status")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }
}
