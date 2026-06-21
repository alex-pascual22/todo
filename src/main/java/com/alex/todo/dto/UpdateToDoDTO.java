package com.alex.todo.dto;

import com.alex.todo.enums.ToDoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateToDoDTO {
    private String title;
    private ToDoStatus status;
    private String description;
    private String details;
}
