package com.alex.todo.service;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.dto.UpdateToDoDTO;
import com.alex.todo.entity.TodoEntity;
import com.alex.todo.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseTodoDTO update(Long id, UpdateToDoDTO updateToDoDTO){
        // Check if to do id exists
        Optional<TodoEntity> todoEntityOptional = toDoRepository.findById(id);
        if(todoEntityOptional.isPresent()){
            // To Do exists, continue with the update
            TodoEntity todoEntity = todoEntityOptional.get();
            todoEntity.setTitle(updateToDoDTO.getTitle());
            todoEntity.setDescription(updateToDoDTO.getDescription());
            todoEntity.setStatus(updateToDoDTO.getStatus());
            todoEntity.setDetails(updateToDoDTO.getDetails());

            todoEntity = toDoRepository.save(todoEntity);

            ResponseTodoDTO responseTodoDTO = new ResponseTodoDTO();
            responseTodoDTO.setId(todoEntity.getId());
            responseTodoDTO.setTitle(todoEntity.getTitle());
            responseTodoDTO.setDescription(todoEntity.getDescription());
            responseTodoDTO.setStatus(todoEntity.getStatus());
            responseTodoDTO.setDetails(todoEntity.getDetails());

            return  responseTodoDTO;
        }
        return null;
    }

    public boolean delete(Long id){
        // Check if to do id exists
        Optional<TodoEntity> todoEntityOptional = toDoRepository.findById(id);
        if(todoEntityOptional.isPresent()){
            try{
                toDoRepository.deleteById(id);
            } catch(Exception e){
                return false;
            }
            return true;
        }
        return false;
    }

    public List<ResponseTodoDTO> getAll(){
        List<ResponseTodoDTO> responseTodoDTOList = new ArrayList<>();
        Iterable<TodoEntity> todoEntityList = toDoRepository.findAll();
        for(TodoEntity todo : todoEntityList){
            ResponseTodoDTO responseTodoDTO = new ResponseTodoDTO();
            responseTodoDTO.setId(todo.getId());
            responseTodoDTO.setTitle(todo.getTitle());
            responseTodoDTO.setDescription(todo.getDescription());
            responseTodoDTO.setStatus(todo.getStatus());
            responseTodoDTO.setDetails(todo.getDetails());

            responseTodoDTOList.add(responseTodoDTO);
        }
        return responseTodoDTOList;
    }

    public ResponseTodoDTO getTodoById(Long id){
        // Check if to do id exists
        Optional<TodoEntity> todoEntityOptional = toDoRepository.findById(id);
        if(todoEntityOptional.isPresent()){
            TodoEntity todoEntity = todoEntityOptional.get();
            ResponseTodoDTO responseTodoDTO = new ResponseTodoDTO();

            responseTodoDTO.setId(todoEntity.getId());
            responseTodoDTO.setTitle(todoEntity.getTitle());
            responseTodoDTO.setDescription(todoEntity.getDescription());
            responseTodoDTO.setStatus(todoEntity.getStatus());
            responseTodoDTO.setDetails(todoEntity.getDetails());
            return responseTodoDTO;
        }
        return null;
    }
}
