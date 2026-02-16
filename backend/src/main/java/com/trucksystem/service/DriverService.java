package com.trucksystem.service;

import com.trucksystem.dto.DriverDTO;
import com.trucksystem.entity.Driver;
import com.trucksystem.entity.Truck;
import com.trucksystem.exception.ResourceAlreadyExistsException;
import com.trucksystem.exception.ResourceNotFoundException;
import com.trucksystem.repository.DriverRepository;
import com.trucksystem.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {
    
    private final DriverRepository driverRepository;
    private final TruckRepository truckRepository;
    
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public DriverDTO getDriverById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        return mapToDTO(driver);
    }
    
    @Transactional
    public DriverDTO createDriver(DriverDTO dto) {
        if (driverRepository.findByLicenseNumber(dto.getLicenseNumber()).isPresent()) {
            throw new ResourceAlreadyExistsException("License number already exists");
        }
        
        Driver driver = new Driver();
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setLicenseNumber(dto.getLicenseNumber());
        
        if (dto.getAssignedTruckId() != null) {
            Truck truck = truckRepository.findById(dto.getAssignedTruckId())
                    .orElseThrow(() -> new ResourceNotFoundException("Truck not found"));
            driver.setAssignedTruck(truck);
        }
        
        driver = driverRepository.save(driver);
        return mapToDTO(driver);
    }
    
    @Transactional
    public DriverDTO updateDriver(Long id, DriverDTO dto) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
        
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setLicenseNumber(dto.getLicenseNumber());
        
        if (dto.getAssignedTruckId() != null) {
            Truck truck = truckRepository.findById(dto.getAssignedTruckId())
                    .orElseThrow(() -> new ResourceNotFoundException("Truck not found"));
            driver.setAssignedTruck(truck);
        } else {
            driver.setAssignedTruck(null);
        }
        
        driver = driverRepository.save(driver);
        return mapToDTO(driver);
    }
    
    @Transactional
    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver not found");
        }
        driverRepository.deleteById(id);
    }
    
    private DriverDTO mapToDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setId(driver.getId());
        dto.setName(driver.getName());
        dto.setPhone(driver.getPhone());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setAssignedTruckId(driver.getAssignedTruck() != null ? driver.getAssignedTruck().getId() : null);
        dto.setAssignedTruckNumber(driver.getAssignedTruck() != null ? driver.getAssignedTruck().getTruckNumber() : null);
        return dto;
    }
}
