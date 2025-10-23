package com.ecommerce.tshirtshop.service;

import com.ecommerce.tshirtshop.model.User;
import com.ecommerce.tshirtshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Un compte existe déjà avec cet email.";
        }
        userRepository.save(user);
        return "Compte créé avec succès !";
    }
}
