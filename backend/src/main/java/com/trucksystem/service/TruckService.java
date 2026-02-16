package com.trucksystem.service;

import com.trucksystem.dto.TruckDTO;
import com.trucksystem.entity.Truck;
import com.trucksystem.exception.ResourceAlreadyExistsException;
import com.trucksystem.exception.ResourceNotFoundException;
import com.trucksystem.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TruckService {
    
    private final TruckRepository truckRepository;
    
    public List<TruckDTO> getAllTrucks() {
        return truckRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public TruckDTO getTruckById(Long id) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found"));
        return mapToDTO(truck);
    }
    
    @Transactional
    public TruckDTO createTruck(TruckDTO dto) {
        if (truckRepository.findByTruckNumber(dto.getTruckNumber()).isPresent()) {
            throw new ResourceAlreadyExistsException("Truck number already exists");
        }
        
        Truck truck = new Truck();
        truck.setTruckNumber(dto.getTruckNumber());
        truck.setType(dto.getType());
        truck.setCapacity(dto.getCapacity());
        truck.setAvailabilityStatus(Truck.AvailabilityStatus.AVAILABLE);
        
        truck = truckRepository.save(truck);
        return mapToDTO(truck);
    }
    
    @Transactional
    public TruckDTO updateTruck(Long id, TruckDTO dto) {
        Truck truck = truckRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Truck not found"));
        
        truck.setTruckNumber(dto.getTruckNumber());
        truck.setType(dto.getType());
        truck.setCapacity(dto.getCapacity());
        if (dto.getAvailabilityStatus() != null) {
            truck.setAvailabilityStatus(Truck.AvailabilityStatus.valueOf(dto.getAvailabilityStatus()));
        }
        
        truck = truckRepository.save(truck);
        return mapToDTO(truck);
    }
    
    @Transactional
    public void deleteTruck(Long id) {
        if (!truckRepository.existsById(id)) {
            throw new ResourceNotFoundException("Truck not found");
        }
        truckRepository.deleteById(id);
    }
    
    private TruckDTO mapToDTO(Truck truck) {
        TruckDTO dto = new TruckDTO();
        dto.setId(truck.getId());
        dto.setTruckNumber(truck.getTruckNumber());
        dto.setType(truck.getType());
        dto.setCapacity(truck.getCapacity());
        dto.setAvailabilityStatus(truck.getAvailabilityStatus().name());
        return dto;
    }
}
