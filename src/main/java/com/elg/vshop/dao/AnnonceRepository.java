package com.elg.vshop.dao;

import com.elg.vshop.entity.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer>, JpaSpecificationExecutor<Annonce> {
    Set<Annonce> findAllByUserId(int userId);
    Long countByUserId(int userId);
    List<Annonce> findFirst10ByOrderByCreationDateDesc();
}
