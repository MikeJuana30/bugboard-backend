package ma.bugboard.bugboard26.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // Es. "CREATE_ISSUE", "UPDATE_STATUS"

    @Column(name = "entity_id")
    private Long entityId; // L'ID della Issue modificata

    @Column(columnDefinition = "TEXT")
    private String payload; // Dettagli della modifica (JSON o testo)

    private LocalDateTime timestamp = LocalDateTime.now();
}