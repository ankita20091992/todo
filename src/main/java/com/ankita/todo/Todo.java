package com.ankita.todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Todo(Long id, String title, boolean completed,
		LocalDateTime createdDate, LocalDate dueDate) {

}
