package com.alex.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateToDoDTO {
    private String title;
    private String status;
    private String description;
    private String details;
}
