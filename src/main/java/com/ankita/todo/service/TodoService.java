package com.ankita.todo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ankita.todo.Todo;
@Service
public class TodoService {

    private final List<Todo> todos = new ArrayList<>();
    private long counter = 0;

    // 1. list all
    public List<Todo> findAll() {
        // return the list
    	return todos;
    }

    // 2. create from a title
    public Todo create(String title) {
        // increment counter, build a new Todo with that id + title + false,
        counter++;
        Todo newTask = new Todo(counter, title, false);
    	// add it to the list, return it
        todos.add(newTask);
        return newTask;
    }

    // 3. toggle done/undone by id
    public void toggle(Long id) {
        // find the matching Todo in the list (loop, or indexed loop),
    	for(int i = 0; i < todos.size(); i++) {
    		// build a replacement Todo with the opposite 'completed',
    		Todo current = todos.get(i);
    		// put the replacement back in the same position
    		if (current.id().equals(id)) {
    			// build a replacement with completed flipped, 
    			Todo replacement = new Todo(current.id(), current.title(), 
    					!current.completed());
    			//then todos.set(i, replacement); then stop
    			todos.set(i, replacement);
    			return;
    		}
    	}   
    }

    // 4. delete by id
    public void delete(Long id) {
        // remove the Todo whose id matches
    	todos.removeIf(t -> t.id().equals(id));
    }
}
//findAll — right.
//create — counter bump, record construction with false, add, return. All correct.
//toggle — you nailed the hard part. You looped, found the match with .equals() (not == — you got the Long gotcha right), built a new immutable Todo with !current.completed() to flip it, and swapped it in with todos.set(i, replacement). That's the immutable-record pattern done properly. That was the conceptually hardest thing in this whole phase and you did it yourself.
//delete — the removeIf one-liner, correct
