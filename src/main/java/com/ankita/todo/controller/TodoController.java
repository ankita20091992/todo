package com.ankita.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankita.todo.CreateTodoRequest;
import com.ankita.todo.Todo;
import com.ankita.todo.service.TodoService;

import jakarta.validation.Valid;

// imports: TodoService, Todo, List, and the web annotations
// (Ctrl+Shift+O will pull most of these in — accept the org.springframework.web.bind.annotation ones)

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    // GET /api/todos  → list all
    @GetMapping
    public List<Todo> findAll() {
        // call the service method that returns all todos, return its result
    	return service.findAll();
    }

    // POST /api/todos  → create one
    @PostMapping
    public Todo create(@Valid @RequestBody CreateTodoRequest todo) {
        // call the service's create method, passing the title out of the incoming todo
        // (use the record accessor for title)
    	return service.create(todo.title(), todo.dueDate());
    }

    // PUT /api/todos/{id}  → toggle done
    @PutMapping("/{id}")
    public void toggle(@PathVariable Long id) {
        // call the service's toggle method with id
    	service.toggle(id);
    }

    // DELETE /api/todos/{id}  → delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // call the service's delete method with id
    	service.delete(id);
    }
}