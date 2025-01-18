package com.deiz0n.cryptography.repositories;

import com.deiz0n.cryptography.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCreditCardToken(String creditCardToken);
    Optional<User> findByUserDocument(String userDocument);

}
