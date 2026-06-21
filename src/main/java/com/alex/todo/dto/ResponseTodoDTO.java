package com.alex.todo.dto;

import com.alex.todo.enums.ToDoStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseTodoDTO {
    private Long id;
    private String title;
    private String description;
    private ToDoStatus status;
    private String statusDescription;
    private String details;
}
