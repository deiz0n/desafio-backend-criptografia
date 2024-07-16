package com.deiz0n.cryptography.repositories;

import com.deiz0n.cryptography.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
