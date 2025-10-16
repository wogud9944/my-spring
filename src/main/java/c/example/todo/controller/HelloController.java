package c.example.todo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello ✅";
    }
}
