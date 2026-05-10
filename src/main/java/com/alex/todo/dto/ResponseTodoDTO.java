package com.alex.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseTodoDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String details;
}
