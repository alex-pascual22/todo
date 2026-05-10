package com.alex.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoDTO {
    private String title;
    private String description;
    private String details;
}
