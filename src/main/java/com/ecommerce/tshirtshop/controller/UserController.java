package com.ecommerce.tshirtshop.controller;

import com.ecommerce.tshirtshop.model.User;
import com.ecommerce.tshirtshop.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }

        // Vérifie si un autre utilisateur a déjà cet email
        Optional<User> emailConflict = userRepository.findByEmail(updatedUser.getEmail());
        if (emailConflict.isPresent() && !emailConflict.get().getId().equals(id)) {
            return ResponseEntity.badRequest().body("Cet email est déjà utilisé par un autre compte.");
        }

        User user = existingUser.get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPostalCode(updatedUser.getPostalCode());
        user.setCity(updatedUser.getCity());
        user.setPassword(updatedUser.getPassword());

        userRepository.save(user);

        return ResponseEntity.ok(user); // ✅ renvoie l’utilisateur mis à jour
    }
}
