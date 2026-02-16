package com.trucksystem.repository;

import com.trucksystem.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findByTruckNumber(String truckNumber);
    List<Truck> findByAvailabilityStatus(Truck.AvailabilityStatus status);
}
