package com.elg.vshop.controller;

import com.elg.vshop.dao.AnnonceRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.Annonce;
import com.elg.vshop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AnnonceRestController {
    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/annonces")
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @GetMapping("/annonces/{annonceId}")
    public Annonce getAnnonceByID(@PathVariable int annonceId) {
        if(!annonceRepository.existsById(annonceId)) {
            throw new RuntimeException("User not found!");
        }
        return null;
    }

    @GetMapping("/users/{userId}/annonces")
    public List<Annonce> getAnnonceByUserID(@PathVariable int userId) {
        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("Annonce not found with userID - " + userId);
        }
        List<Annonce> annonces = annonceRepository.findByUserId(userId);

        if(annonces.size() > 0) {
            return annonces;
        } else
            throw new RuntimeException("Annonces not found!");
    }

    @PostMapping("/users/{userId}/annonces")
    public Annonce addAnnonce(@PathVariable int userId, @RequestBody Annonce annonce) {
        User user = null;
        Optional<User> res = userRepository.findById(userId);
        if(res.isPresent()) {
            user = res.get();
        } else {
            throw new RuntimeException("User not found");
        }
        annonce.setUser(user);
        annonceRepository.save(annonce);

        return annonce;
        /*return userRepository.findById(userId)
                .map(user -> {
                    annonce.setUser(user);
                    return annonceRepository.save(annonce);
                }).orElseThrow(() -> new RuntimeException("User not found!"));*/
    }

    @PutMapping("/annonces/{annonceId}")
    public Annonce updateAccount(@PathVariable int annonceId, @RequestBody Annonce annonceUpdated) {
        Annonce annonce = null;
        Optional<Annonce> res = annonceRepository.findById(annonceId);
        if(res.isPresent()) {
            annonce = res.get();
        } else {
            throw new RuntimeException("Annonce with id " + annonceId + " not found!");
        }
        annonce.setTitle(annonceUpdated.getTitle());
        annonce.setDescription(annonceUpdated.getDescription());
        annonce.setImageUrl(annonceUpdated.getImageUrl());
        annonceRepository.save(annonce);

        return annonce;
        /*return annonceRepository.findById(annonceId)
                .map(annonce -> {
                    annonce.setTitle(annonceUpdated.getTitle());
                    annonce.setDescription(annonceUpdated.getDescription());
                    annonce.setImageUrl(annonceUpdated.getImageUrl());
                    return annonceRepository.save(annonce);
                }).orElseThrow(() -> new RuntimeException("Annonce with id " + annonceId + " not found!"));*/
    }

    @DeleteMapping("/annonces/{annonceId}")
    public String deleteAccount(@PathVariable int annonceId) {
        Annonce annonce = null;
        Optional<Annonce> res = annonceRepository.findById(annonceId);
        if(res.isPresent()) {
            annonce = res.get();
        } else {
            throw new RuntimeException("Annonce with id " + annonceId + " not found!");
        }
        annonceRepository.delete(annonce);
        return "Deleted Successfully!";
        /*return annonceRepository.findById(annonceId)
                .map(annonce -> {
                    annonceRepository.delete(annonce);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new RuntimeException("Annonce not found!"));*/
    }

}
