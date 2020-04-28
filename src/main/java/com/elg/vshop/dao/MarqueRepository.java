package com.elg.vshop.dao;

import com.elg.vshop.entity.vehicule.Marque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarqueRepository extends JpaRepository<Marque, Integer> {
    List<Marque> findAllByOrderByMarqueName();
}
