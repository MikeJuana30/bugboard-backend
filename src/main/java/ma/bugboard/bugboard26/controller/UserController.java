package ma.bugboard.bugboard26.controller;

import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.repository.UserRepository;
import ma.bugboard.bugboard26.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // Dice a Spring: "Questa classe risponde alle chiamate web"
@RequestMapping("/api/users") // Tutti gli indirizzi inizieranno con /api/users
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository; //

    // API per creare un utente (POST)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    // API per leggere tutti gli utenti (GET)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // API per il LOGIN
    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");

        // Cerca l'utente. Se non c'è, lancia un errore.
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato!"));
    }

    // API per la REGISTRAZIONE
    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String password = userData.get("password");

        // 1. Controlla se l'email esiste già
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email già in uso!");
        }

        // 2. Crea il nuovo utente
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("USER"); // Ruolo base per tutti i nuovi

        return userRepository.save(newUser);
    }
}