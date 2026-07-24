package com.ankita.todo;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(
		@NotBlank String title
) {

}
