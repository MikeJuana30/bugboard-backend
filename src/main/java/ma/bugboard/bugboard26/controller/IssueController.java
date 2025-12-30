package ma.bugboard.bugboard26.controller;

import ma.bugboard.bugboard26.model.Issue;
import ma.bugboard.bugboard26.model.User;
import ma.bugboard.bugboard26.repository.UserRepository;
import ma.bugboard.bugboard26.service.IssueService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ma.bugboard.bugboard26.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "http://localhost:63342")
public class IssueController {

    private final IssueService issueService;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    // COSTRUTTORE MANUALE: Sostituisce Lombok e risolve l'errore rosso in IntelliJ
    public IssueController(IssueService issueService,
                           IssueRepository issueRepository,
                           UserRepository userRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Issue createIssue(@RequestParam Long reporterId, @RequestBody Issue issue) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato!"));

        issue.setReporter(reporter);
        issue.setCreatedAt(LocalDateTime.now());
        issue.setStatus("TODO");
        return issueRepository.save(issue);
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<Issue> closeIssue(@PathVariable Long id) {
        return issueRepository.findById(id).map(issue -> {
            issue.setStatus("CLOSED");
            return ResponseEntity.ok(issueRepository.save(issue));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Issue updateIssue(@PathVariable Long id, @RequestBody Issue updatedIssue) {
        return issueRepository.findById(id)
                .map(issue -> {
                    issue.setTitle(updatedIssue.getTitle());
                    issue.setDescription(updatedIssue.getDescription());
                    issue.setType(updatedIssue.getType());
                    issue.setPriority(updatedIssue.getPriority());
                    issue.setStatus(updatedIssue.getStatus());
                    issue.setAssignee(updatedIssue.getAssignee());
                    return issueRepository.save(issue);
                })
                .orElseThrow(() -> new RuntimeException("Issue not found"));
    }
}