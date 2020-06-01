package com.elg.vshop.controller.main;

import com.elg.vshop.dao.AnnonceSearch;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.exception.AnnoncesNotFoundException;
import com.elg.vshop.service.AnnonceService;
import com.elg.vshop.service.FileService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/annonces")
@CrossOrigin("http://localhost:4200")
public class AnnonceRestController {
    private AnnonceService annonceService;
    private FileService fileService;

    @Autowired
    public AnnonceRestController(AnnonceService annonceService, FileService fileService) {
        this.annonceService = annonceService;
        this.fileService = fileService;
    }

    @GetMapping("/sec/u/count")
    public long getCountById() {
        return annonceService.countById();
    }

    @GetMapping("/sec/u/all")
    public Set<Annonce> getAnnoncesByUserId() throws NotFoundException {
        return annonceService.findAnnoncesByUserId();
    }

    @GetMapping("/latest")
    public List<Annonce> getLatestAnnonces() throws NotFoundException {
        return annonceService.findLatestAnnonces();
    }

    @GetMapping("/sec/{annonceId}")
    public Annonce getAnnonceById(@PathVariable int annonceId) throws AnnoncesNotFoundException {
        return annonceService.findById(annonceId);

    }

    // -----------------------------------
    @PostMapping("/vh/search")
    public List<Annonce> getAnnoncesbyFilters(@Valid @RequestBody AnnonceSearch annonceSearch) {
        return annonceService.findByCriteria(annonceSearch);
    }
    // -----------------------------------

    @PostMapping("sec/u")
    public Annonce addAnnonce(@RequestPart("imageFiles") MultipartFile[] file, @Valid @RequestPart Annonce annonce) {
        fileService.uploadFile(file, annonce);
        annonceService.saveAnnonce(annonce);
        return annonce;
    }

    @PutMapping("sec/{annonceId}")
    public Annonce updateAnnonce(@PathVariable int annonceId, @Valid @RequestBody Annonce annonceUpdated) throws NotFoundException {
        annonceService.updateAnnonce(annonceId, annonceUpdated);
        return annonceUpdated;
    }

    @DeleteMapping("sec/{annonceId}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable int annonceId) {
        Annonce annonce = annonceService.deleteAnnonce(annonceId);
        if(annonce != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
