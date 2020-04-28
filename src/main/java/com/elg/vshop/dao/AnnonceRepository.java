package com.elg.vshop.dao;

import com.elg.vshop.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer>, JpaSpecificationExecutor<Annonce> {
    List<Annonce> findAllByUserId(int userId);
    Long countByUserId(int userId);
    List<Annonce> findFirst10ByOrderByCreationDateDesc();
}
