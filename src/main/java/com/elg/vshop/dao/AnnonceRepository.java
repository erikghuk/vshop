package com.elg.vshop.dao;

import com.elg.vshop.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    List<Annonce> findByUserId(int userId);
}
