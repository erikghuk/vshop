package com.elg.vshop.controller.testCRUD;

import com.elg.vshop.dao.UserRepository;
import com.elg.vshop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class UserRestController {
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public User getUserByID(@PathVariable int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if(optUser.isPresent()) {
            return optUser.get();
        }else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id,
                                 @RequestBody User userUpdated) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setDateRegistration(userUpdated.getDateRegistration());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }
}
