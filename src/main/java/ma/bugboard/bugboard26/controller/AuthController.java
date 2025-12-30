package ma.bugboard.bugboard26.controller;

import ma.bugboard.bugboard26.dto.LoginRequest;
import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:63342")

public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // 1. Cerca l'utente
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        // 2. Controllo Password (semplice per ora)
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Email o Password errati!");
        }

        // 3. Login OK!
        return ResponseEntity.ok(user);
    }
}