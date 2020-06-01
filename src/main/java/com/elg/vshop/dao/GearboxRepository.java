package com.elg.vshop.dao;

import com.elg.vshop.entity.vehicule.Gearbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GearboxRepository extends JpaRepository<Gearbox, Integer> {
    Gearbox findByBoxName(String boxName);
}
