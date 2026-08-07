package com.ankita.todo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(
		@NotBlank String title, LocalDate dueDate
) {

}
