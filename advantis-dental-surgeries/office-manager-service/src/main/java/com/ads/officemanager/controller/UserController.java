package com.ads.officemanager.controller;

import com.ads.officemanager.entity.User;
import com.ads.officemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adsweb/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(service.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " deleted successfully");
    }
}
