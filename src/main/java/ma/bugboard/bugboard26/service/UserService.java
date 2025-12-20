package ma.bugboard.bugboard26.service;

import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Dice a Spring: "Questa è la logica di business"
public class UserService {

    @Autowired
    private UserRepository userRepository; // Iniettiamo il repository (il magazzino)

    // Metodo per creare un utente
    public User createUser(User user) {
        // Controllo logico: l'email esiste già?
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email già in uso!");
        }
        // Se è tutto ok, salva nel database
        return userRepository.save(user);
    }

    // Metodo per leggere tutti gli utenti
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}