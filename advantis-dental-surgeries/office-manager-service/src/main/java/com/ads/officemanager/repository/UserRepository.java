package com.ads.officemanager.repository;

import com.ads.officemanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);  // ✅ NEW
    List<User> findByRole(String role);        // ✅ NEW
}
