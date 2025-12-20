package ma.bugboard.bugboard26.repository;

import ma.bugboard.bugboard26.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Trova un utente dalla sua email
    Optional<User> findByEmail(String email);

    // Controlla se una email esiste già (utile per la registrazione)
    boolean existsByEmail(String email);
}