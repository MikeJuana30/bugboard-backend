package ma.bugboard.bugboard26;

import ma.bugboard.bugboard26.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Bugboard26Application {

	public static void main(String[] args) {
		SpringApplication.run(Bugboard26Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return args -> { // <--- Qui c'era l'errore, ora è corretto!

			// 1. CONTROLLO MARIO
			if (userRepository.findByEmail("mario.postman@example.com").isEmpty()) {
				ma.bugboard.bugboard26.model.User mario = new ma.bugboard.bugboard26.model.User();
				mario.setEmail("mario.postman@example.com");
				mario.setPassword("password");
				mario.setRole("USER");
				userRepository.save(mario);
				System.out.println("✅ UTENTE MARIO CREATO!");
			}

			// 2. CONTROLLO LUIGI
			if (userRepository.findByEmail("luigi.verdi@example.com").isEmpty()) {
				ma.bugboard.bugboard26.model.User luigi = new ma.bugboard.bugboard26.model.User();
				luigi.setEmail("luigi.verdi@example.com");
				luigi.setPassword("password");
				luigi.setRole("DEV");
				userRepository.save(luigi);
				System.out.println("✅ UTENTE LUIGI CREATO!");
			}
		};
	}
}
