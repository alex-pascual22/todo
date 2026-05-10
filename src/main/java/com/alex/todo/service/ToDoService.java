package com.alex.todo.service;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.entity.TodoEntity;
import com.alex.todo.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository = toDoRepository;
    }

    public ResponseTodoDTO add(CreateTodoDTO createTodoDTO){
        TodoEntity todoEntity = new TodoEntity();
        ResponseTodoDTO responseTodoDTO = new ResponseTodoDTO();

        if(isNull(createTodoDTO.getTitle()) || createTodoDTO.getTitle().isBlank()){
            createTodoDTO.setTitle("Untitled");
        }

        // Create Entity
        todoEntity.setTitle(createTodoDTO.getTitle());
        todoEntity.setDescription(createTodoDTO.getDescription());
        todoEntity.setStatus("Not yet started");
        todoEntity.setDetails(createTodoDTO.getDetails());

        todoEntity = toDoRepository.save(todoEntity);

        responseTodoDTO.setId(todoEntity.getId());
        responseTodoDTO.setTitle(todoEntity.getTitle());
        responseTodoDTO.setDescription(todoEntity.getDescription());
        responseTodoDTO.setStatus(todoEntity.getStatus());
        responseTodoDTO.setDetails(todoEntity.getDetails());

        return responseTodoDTO;
    }
}
