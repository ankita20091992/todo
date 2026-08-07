package com.ankita.todo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ankita.todo.Todo;
import com.ankita.todo.TodoEntity;
import com.ankita.todo.TodoRepository;
@Service
public class TodoService {

    //private final List<Todo> todos = new ArrayList<>();
    //private long counter = 0;
	private final TodoRepository repo;
	public TodoService(TodoRepository repo) {
		this.repo= repo;
		}
	//convert a database entity into API record
	private Todo toDto(TodoEntity e) {
		return new Todo(e.getId(), e.getTitle(), e.isCompleted(),
				e.getCreatedDate(), e.getDueDate());
	}
    // 1. list all
    public List<Todo> findAll() {
        //repo.findAll() returns List<TodoEntity>
    	//convert each entity to a Todo. Use stream or List<Todo>
    	return repo.findAll().stream().map(this::toDto).toList();
    }

    // 2. create from a title
    public Todo create(String title, LocalDate dueDate) {
    	TodoEntity entity = new TodoEntity(title, false);
    	entity.setCreatedDate(LocalDateTime.now());
    	entity.setDueDate(dueDate);
    	TodoEntity saved = repo.save(entity);
    	return toDto(saved);
    }
    
    // 3. toggle done/undone by id
    public void toggle(Long id) {
        //repo.findById(id) returns an Optional<TodoEntity>
    	//get the entity
    	//or throw NoSuchElementException if absent),
    	//flip its completed with setCompleted(!e.isCompleted())
    	TodoEntity e = repo.findById(id)
    			.orElseThrow(() -> new NoSuchElementException("Todo not found: " + id));
    	e.setCompleted(!e.isCompleted());
    	//then repo.save(e) to persist the change
    	repo.save(e);
    }

    // 4. delete by id
    public void delete(Long id) {
        //if repo.existsById(id) is false, throw NoSuchElementException
    	if(!repo.existsById(id)) {
    		throw new NoSuchElementException("Todo not found: " +id);
    	}
    	//otherwise repo.deleteById(id)
    	repo.deleteById(id);
    }
}
//findAll — right.
//create — counter bump, record construction with false, add, return. All correct.
//toggle — you nailed the hard part. You looped, found the match with .equals() (not == — you got the Long gotcha right), built a new immutable Todo with !current.completed() to flip it, and swapped it in with todos.set(i, replacement). That's the immutable-record pattern done properly. That was the conceptually hardest thing in this whole phase and you did it yourself.
//delete — the removeIf one-liner, correct
