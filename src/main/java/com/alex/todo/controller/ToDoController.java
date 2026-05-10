package com.alex.todo.controller;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseTodoDTO> add(@RequestBody CreateTodoDTO createTodoDTO){
        ResponseTodoDTO responseTodoDTO;
        responseTodoDTO = toDoService.add(createTodoDTO);
        return new ResponseEntity<>(responseTodoDTO, HttpStatus.CREATED);
    }
}
