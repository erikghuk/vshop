package com.elg.vshop.dao;

import com.elg.vshop.entity.vehicule.Marque;
import com.elg.vshop.entity.vehicule.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    Model findByModelName(String modelName);
    List<Model> findAllByMarqueId(int id);
}
