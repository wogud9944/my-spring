package com.example.todo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Todo> list() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo req) {
        Todo saved = repo.save(req);
        return ResponseEntity.created(URI.create("/api/todos/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> find(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @Valid @RequestBody Todo req) {
        return repo.findById(id).map(t -> {
            t.setTitle(req.getTitle());
            t.setCompleted(req.isCompleted());
            return ResponseEntity.ok(repo.save(t));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
