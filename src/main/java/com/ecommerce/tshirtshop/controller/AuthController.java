package com.ecommerce.tshirtshop.controller;

import com.ecommerce.tshirtshop.model.User;
import com.ecommerce.tshirtshop.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Endpoint de connexion
    @PostMapping("/login")
    public Object login(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByEmailAndPassword(
                loginRequest.getEmail(), loginRequest.getPassword()
        );

        if (user.isPresent()) {
            return user.get(); // renvoie les infos de l'utilisateur connecté
        } else {
            return new ErrorResponse("Email ou mot de passe incorrect");
        }
    }

    // ✅ classe interne pour message d’erreur
    public record ErrorResponse(String message) {}
}
