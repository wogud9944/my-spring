package c.example.todo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import c.example.todo.Todo;
import c.example.todo.TodoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    // 전체 목록
    @GetMapping
    public List<Todo> list() {
        return repo.findAll();
    }

    // 생성
    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo req) {
        Todo saved = repo.save(req);
        return ResponseEntity
                .created(URI.create("/api/todos/" + saved.getId()))
                .body(saved);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Todo> find(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @Valid @RequestBody Todo req) {
        return repo.findById(id)
                .map(t -> {
                    t.setTitle(req.getTitle());
                    t.setCompleted(req.isCompleted());
                    return ResponseEntity.ok(repo.save(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
