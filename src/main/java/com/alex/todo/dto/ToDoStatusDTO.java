package com.alex.todo.dto;

import com.alex.todo.enums.ToDoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDoStatusDTO {
    private String statusCode;
    private String statusDescription;
}
