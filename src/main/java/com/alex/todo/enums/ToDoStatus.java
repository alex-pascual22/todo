package com.alex.todo.enums;

public enum ToDoStatus {

    NOT_YET_STARTED("Not yet started"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    private final String description;

    ToDoStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
