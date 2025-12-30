package ma.bugboard.bugboard26.service;

import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // COSTRUTTORE MANUALE: Sostituisce Lombok e risolve l'errore rosso nell'IDE
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email già in uso!");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}