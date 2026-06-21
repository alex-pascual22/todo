package com.alex.todo.controller;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.dto.ToDoStatusDTO;
import com.alex.todo.dto.UpdateToDoDTO;
import com.alex.todo.enums.ToDoStatus;
import com.alex.todo.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.in;

@RestController
@RequestMapping("/api/v1/todo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseTodoDTO> add(@RequestBody CreateTodoDTO createTodoDTO){
        ResponseTodoDTO responseTodoDTO = toDoService.add(createTodoDTO);
        return new ResponseEntity<>(responseTodoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseTodoDTO> update(@PathVariable Long id, @RequestBody UpdateToDoDTO updateToDoDTO){
        ResponseTodoDTO responseTodoDTO = toDoService.update(id, updateToDoDTO);

        if(responseTodoDTO != null){
            return new ResponseEntity<>(responseTodoDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean isDeleted = toDoService.delete(id);

        if(isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public List<ResponseTodoDTO> getAll(){
        return toDoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTodoDTO> get(@PathVariable Long id){
        ResponseTodoDTO responseTodoDTO = toDoService.getTodoById(id);

        if(responseTodoDTO != null){
            return new ResponseEntity<>(responseTodoDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/statuses")
    public List<ToDoStatusDTO> getToDoStatuses() {
        List<ToDoStatusDTO> toDoStatusDTOList = new ArrayList<>();
        for(ToDoStatus status : ToDoStatus.values()){
            ToDoStatusDTO toDoStatusDTO = new ToDoStatusDTO();
            toDoStatusDTO.setStatusCode(status.name());
            toDoStatusDTO.setStatusDescription(status.getDescription());
            toDoStatusDTOList.add(toDoStatusDTO);
        }
        return toDoStatusDTOList;
    }
}
