package ma.bugboard.bugboard26.controller;

import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Dice a Spring: "Questa classe risponde alle chiamate web"
@RequestMapping("/api/users") // Tutti gli indirizzi inizieranno con /api/users
public class UserController {

    @Autowired
    private UserService userService;

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
}