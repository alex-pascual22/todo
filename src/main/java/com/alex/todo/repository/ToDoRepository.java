package com.alex.todo.repository;

import com.alex.todo.entity.TodoEntity;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<TodoEntity, Long> {
}
