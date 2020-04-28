package com.elg.vshop.dao;

import com.elg.vshop.entity.vehicule.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
