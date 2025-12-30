package ma.bugboard.bugboard26.service;

import ma.bugboard.bugboard26.model.Issue;
import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.repository.IssueRepository;
import ma.bugboard.bugboard26.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    // COSTRUTTORE MANUALE: Sostituisce Lombok e permette l'iniezione delle dipendenze
    public IssueService(IssueRepository issueRepository,
                        UserRepository userRepository,
                        AuditLogService auditLogService) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    public Issue createIssue(Long reporterId, Issue issue) {
        // 1. Cerchiamo l'utente
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato!"));

        // 2. Prepariamo la segnalazione
        issue.setReporter(reporter);
        issue.setStatus("OPEN");

        // 3. Salviamo la segnalazione
        Issue savedIssue = issueRepository.save(issue);

        // 4. Log automatico
        auditLogService.logAction(
                "CREATE_ISSUE",
                savedIssue.getId(),
                "Created by user: " + reporter.getEmail()
        );

        return savedIssue;
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }
}