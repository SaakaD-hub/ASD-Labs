package com.ads.officemanager.service;

import com.ads.officemanager.entity.Role;
import com.ads.officemanager.entity.User;
import com.ads.officemanager.repository.RoleRepository;
import com.ads.officemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private KafkaTemplate<String, Object> kafka;

    public User createUser(User user) {
        Role role = roleRepo.findByName("ROLE_MANAGER");
        if (role == null) {
            role = roleRepo.save(new Role(null, "ROLE_MANAGER"));
        }
        user.setRole(role);
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

    public void deleteUser(String id) {
        userRepo.deleteById(id);
        kafka.send("user.deleted", id);
    }
}
