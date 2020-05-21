package com.elg.vshop.dao;

import com.elg.vshop.entity.vehicule.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarburantRepository extends JpaRepository<Carburant, Integer> {
    Carburant findByTypeOfCarburant(String type);
}
