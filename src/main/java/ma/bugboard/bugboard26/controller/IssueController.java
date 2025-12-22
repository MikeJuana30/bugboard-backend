package ma.bugboard.bugboard26.controller;

import ma.bugboard.bugboard26.model.Issue;
import ma.bugboard.bugboard26.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import ma.bugboard.bugboard26.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueRepository issueRepository;

    // API per creare una segnalazione
    // Esempio: POST /api/issues?reporterId=1
    @PostMapping
    public Issue createIssue(@RequestParam Long reporterId, @RequestBody Issue issue) {
        return issueService.createIssue(reporterId, issue);
    }

    // API per vedere tutte le segnalazioni
    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    // 1. Per CANCELLARE un bug (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 2. Per CHIUDERE un bug (cambia stato a CLOSED)
    @PutMapping("/{id}/close")
    public ResponseEntity<Issue> closeIssue(@PathVariable Long id) {
        return issueRepository.findById(id).map(issue -> {
            issue.setStatus("CLOSED"); // Cambia lo stato
            return ResponseEntity.ok(issueRepository.save(issue));
        }).orElse(ResponseEntity.notFound().build());
    }
}