package com.alex.todo.controller;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.dto.UpdateToDoDTO;
import com.alex.todo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseTodoDTO> update(@PathVariable Long id, @RequestBody UpdateToDoDTO updateToDoDTO){
        ResponseTodoDTO responseTodoDTO;
        responseTodoDTO = toDoService.update(id, updateToDoDTO);

        if(!isNull(responseTodoDTO)){
            return new ResponseEntity<>(responseTodoDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTodoDTO> delete(@PathVariable Long id){
        Boolean response = toDoService.delete(id);
        if(response){
            return new ResponseEntity<>(HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public List<ResponseTodoDTO> getAll(){
        List<ResponseTodoDTO> responseTodoDTOList = toDoService.getAll();
        return responseTodoDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTodoDTO> get(@PathVariable Long id){
        ResponseTodoDTO responseTodoDTO = toDoService.getTodoById(id);
        if(!isNull(responseTodoDTO)){
            return new ResponseEntity<>(responseTodoDTO, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
