package com.alex.todo.controller;

import com.alex.todo.dto.ToDoDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {

    @PostMapping("/add")
    public void add(@RequestBody ToDoDTO toDoDTO){
        System.out.println("Title: " + toDoDTO.getTitle());
    }
}
