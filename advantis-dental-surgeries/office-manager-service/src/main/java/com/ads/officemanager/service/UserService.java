package com.ads.officemanager.service;


import com.ads.officemanager.entity.User;
import com.ads.officemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    @Autowired
    private PasswordEncoder passwordEncoder;  // ✅ NEW

    public User createUser(User user) {
        // ✅ NEW - Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ✅ NEW - Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_MANAGER");
        }

        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepo.save(user);
        kafka.send("user.created", saved);
        return saved;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ NEW - Update user
    public User updateUser(String id, User newData) {
        User existing = getUserById(id);

        if (newData.getUsername() != null) {
            existing.setUsername(newData.getUsername());
        }
        if (newData.getEmail() != null) {
            existing.setEmail(newData.getEmail());
        }
        if (newData.getPassword() != null && !newData.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(newData.getPassword()));
        }
        if (newData.getFirstName() != null) {
            existing.setFirstName(newData.getFirstName());
        }
        if (newData.getLastName() != null) {
            existing.setLastName(newData.getLastName());
        }
        if (newData.getPhone() != null) {
            existing.setPhone(newData.getPhone());
        }
        if (newData.getRole() != null) {
            existing.setRole(newData.getRole());
        }

        User updated = userRepo.save(existing);
        kafka.send("user.updated", updated);
        return updated;
    }

    // ✅ NEW - Get users by role
    public List<User> getUsersByRole(String role) {
        return userRepo.findByRole(role);
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);
        kafka.send("user.deleted", id);
    }
}