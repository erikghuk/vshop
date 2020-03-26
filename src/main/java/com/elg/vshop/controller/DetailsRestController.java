package com.elg.vshop.controller;

import com.elg.vshop.dao.UserDetailsRepository;
import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class DetailsRestController {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/details")
    public List<UserDetails> getAllDetails() {
        return userDetailsRepository.findAll();
    }

    @GetMapping("/users/{userId}/details")
    public UserDetails getDetailsByUserId(@PathVariable int userId) {
        if(!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found!");
        }

        List<UserDetails> userDetails = userDetailsRepository.findByUserId(userId);
        if(userDetails.size() > 0) {
            return userDetails.get(0);
        }else {
            throw new RuntimeException("User not found!");
        }
    }

    @PostMapping(value = "/users/{userId}/details")
    public UserDetails addDetails(@PathVariable int userId,
                                  @RequestBody UserDetails details) {
        return userRepository.findById(userId)
                .map(user -> {
                    details.setUser(user);
                    return userDetailsRepository.save(details);
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @PutMapping("/details/{detailsId}")
    public UserDetails updateDetails(@PathVariable int detailsId,
                                     @RequestBody UserDetails detailsUpdated) {
        return userDetailsRepository.findById(detailsId)
                .map(details -> {
                    details.setFirstName(detailsUpdated.getFirstName());
                    details.setLastName(detailsUpdated.getLastName());
                    details.setDob(detailsUpdated.getDob());
                    return userDetailsRepository.save(details);
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @DeleteMapping("/details/{detailsId}")
    public String deleteDetails(@PathVariable int detailsId) {
        return userDetailsRepository.findById(detailsId)
                .map(details -> {
                    userDetailsRepository.delete(details);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new RuntimeException("User not found!"));
    }
}
