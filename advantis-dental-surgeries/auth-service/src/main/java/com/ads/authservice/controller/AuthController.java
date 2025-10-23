package com.ads.authservice.controller;

import com.ads.authservice.entity.User;
import com.ads.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    // ✅ Constructor injection (better than @Autowired field injection)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> payload) {
        String token = authService.login(payload.get("username"), payload.get("password"));

        // ✅ Fixed: Use HashMap instead of Map.of() for Java compatibility
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(authService.getUserByUsername(username));
    }
}