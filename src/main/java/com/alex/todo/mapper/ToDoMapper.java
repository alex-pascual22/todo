package com.alex.todo.mapper;

import com.alex.todo.dto.CreateTodoDTO;
import com.alex.todo.dto.ResponseTodoDTO;
import com.alex.todo.dto.UpdateToDoDTO;
import com.alex.todo.entity.TodoEntity;
import org.springframework.stereotype.Component;

@Component
public class ToDoMapper {

    public ResponseTodoDTO convertEntityToResponseDto(TodoEntity todoEntity){
        ResponseTodoDTO responseTodoDTO = new ResponseTodoDTO();
        responseTodoDTO.setId(todoEntity.getId());
        responseTodoDTO.setTitle(todoEntity.getTitle());
        responseTodoDTO.setDescription(todoEntity.getDescription());
        responseTodoDTO.setStatus(todoEntity.getStatus());
        responseTodoDTO.setStatusDescription(todoEntity.getStatus().getDescription());
        responseTodoDTO.setDetails(todoEntity.getDetails());
        return responseTodoDTO;
    }

    public TodoEntity convertCreateDtoToEntity(CreateTodoDTO createTodoDTO){
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(createTodoDTO.getTitle());
        todoEntity.setDescription(createTodoDTO.getDescription());
        todoEntity.setDetails(createTodoDTO.getDetails());
        return todoEntity;
    }

    public TodoEntity updateEntityFromDto(UpdateToDoDTO updateToDoDTO, TodoEntity todoEntity){
        todoEntity.setTitle(updateToDoDTO.getTitle());
        todoEntity.setDescription(updateToDoDTO.getDescription());
        todoEntity.setStatus(updateToDoDTO.getStatus());
        todoEntity.setDetails(updateToDoDTO.getDetails());
        return todoEntity;
    }
}
