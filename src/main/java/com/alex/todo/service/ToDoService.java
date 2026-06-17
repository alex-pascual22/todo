package com.alex.todo.service;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.dto.UpdateToDoDTO;
import com.alex.todo.entity.TodoEntity;
import com.alex.todo.mapper.ToDoMapper;
import com.alex.todo.repository.ToDoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;

    public ToDoService(ToDoRepository toDoRepository, ToDoMapper toDoMapper){
        this.toDoRepository = toDoRepository;
        this.toDoMapper = toDoMapper;
    }

    public ResponseTodoDTO add(CreateTodoDTO createTodoDTO){
        TodoEntity todoEntity;
        ResponseTodoDTO responseTodoDTO;

        if(isNull(createTodoDTO.getTitle()) || createTodoDTO.getTitle().isBlank()){
            createTodoDTO.setTitle("Untitled");
        }

        todoEntity = toDoMapper.convertCreateDtoToEntity(createTodoDTO);
        todoEntity.setStatus("Not yet started");
        todoEntity = toDoRepository.save(todoEntity);

        responseTodoDTO = toDoMapper.convertEntityToResponseDto(todoEntity);
        return responseTodoDTO;
    }

    public ResponseTodoDTO update(Long id, UpdateToDoDTO updateToDoDTO){
        // Check if to do exists
        Optional<TodoEntity> todoEntityOptional = toDoRepository.findById(id);
        if(todoEntityOptional.isPresent()){
            ResponseTodoDTO responseTodoDTO;
            TodoEntity todoEntity = todoEntityOptional.get();

            todoEntity = toDoMapper.updateEntityFromDto(updateToDoDTO, todoEntity);
            todoEntity = toDoRepository.save(todoEntity);

            responseTodoDTO = toDoMapper.convertEntityToResponseDto(todoEntity);
            return  responseTodoDTO;
        }
        return null;
    }

    public boolean delete(Long id){
        // Check if to do id exists
        Optional<TodoEntity> todoEntityOptional = toDoRepository.findById(id);
        if(todoEntityOptional.isPresent()){
            toDoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ResponseTodoDTO> getAll(){
        List<ResponseTodoDTO> responseTodoDTOList = new ArrayList<>();
        Iterable<TodoEntity> todoEntityList = toDoRepository.findAll();

        for(TodoEntity todo : todoEntityList){
            ResponseTodoDTO responseTodoDTO = toDoMapper.convertEntityToResponseDto(todo);
            responseTodoDTOList.add(responseTodoDTO);
        }
        return responseTodoDTOList;
    }

    public ResponseTodoDTO getTodoById(Long id){
        // Check if to do id exists
        Optional<TodoEntity> todoEntityOptional = toDoRepository.findById(id);
        if(todoEntityOptional.isPresent()){
            TodoEntity todoEntity = todoEntityOptional.get();
            return toDoMapper.convertEntityToResponseDto(todoEntity);
        }
        return null;
    }
}
