package com.alex.todo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="TODO_ENTRY")
@Getter
@Setter
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TODO_ID")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "TODO_TITLE", length = 20)
    private String title;

    @Column(name="TODO_DESCR", length = 50)
    private String description;

    @Column(name="TODO_STATUS", length = 20)
    private String status;

    @Column(name="TODO_DETAILS")
    private String details;
}
