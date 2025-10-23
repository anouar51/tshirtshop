package com.ecommerce.tshirtshop.repository;

import com.ecommerce.tshirtshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // déjà utilisée pour l’inscription

    // 👇 ajoute cette ligne pour la connexion
    Optional<User> findByEmailAndPassword(String email, String password);
}
