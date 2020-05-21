package com.elg.vshop.service;

import com.elg.vshop.dao.AnnonceSearch;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.exception.AnnoncesNotFoundException;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface AnnonceService {
    void saveAnnonce(Annonce annonce);

    void updateAnnonce(int id, Annonce annonce) throws NotFoundException;

    Set<Annonce> findAnnoncesByUserId() throws NotFoundException;

    List<Annonce> findByCriteria(AnnonceSearch annonceSearch);

    Annonce deleteAnnonce(int id);

    long countById();

    Annonce findById(int annonceId) throws AnnoncesNotFoundException;

    List<Annonce> findLatestAnnonces() throws AnnoncesNotFoundException;
}
