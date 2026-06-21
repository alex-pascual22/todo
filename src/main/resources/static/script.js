const API_BASE = "/api/v1/todo";

// Create
async function createTodo() {
    try {
        const title = document.getElementById("title").value;
        const description = document.getElementById("description").value;
        const details = document.getElementById("details").value;

        const todo = {
            title: title,
            description: description,
            details: details
        };

        const response = await fetch(`${API_BASE}/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        });

        if (response.ok) {
            alert("To Do Saved!");
            window.location.href = "/index.html";
            return;
        }

        alert("Failed to create To Do");
    } catch (error) {
        console.log("Error creating todo:", error);
        alert("Could not save To Do");
    }
}

// Read all todos
async function retrieveToDos() {
    const todoContainer = document.getElementById("todoContainer");
    if (!todoContainer) {
        return;
    }

    clearElement(todoContainer);

    try {
        const response = await fetch(`${API_BASE}/`);

        if (!response.ok) {
            todoContainer.innerHTML = "<p>Failed to retrieve To Dos.</p>";
            return;
        }

        const todos = await response.json();

        if (!Array.isArray(todos) || todos.length === 0) {
            todoContainer.innerHTML = "<p>No To Dos yet.</p>";
            return;
        }

        for (const todo of todos) {
            todoContainer.appendChild(renderTodoCard(todo));
        }
    } catch (error) {
        console.log("Error retrieving todos:", error);
        todoContainer.innerHTML = "<p>Could not reach the server.</p>";
    }
}

// Retrieve single to do
async function retrieveTodoById() {
    const todoId = getTodoIdFromUrl();

    if (!todoId) {
        console.log("Missing todo id");
        window.location.href = "/index.html";
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/${todoId}`);

        if (!response.ok) {
            console.log("Todo not found");
            window.location.href = "/index.html";
            return;
        }

        const todo = await response.json();

        const editButton = document.getElementById("editButton");
        if (editButton) {
            editButton.onclick = function () {
                window.location.href = `/editTodo.html?id=${todoId}`;
            };
        }

        const deleteButton = document.getElementById("deleteButton");
        if (deleteButton) {
            deleteButton.onclick = function () {
                deleteTodo(todoId);
            };
        }

        renderTodoDetails(todo);
    } catch (error) {
        console.log("Error retrieving todo by id:", error);
        window.location.href = "/index.html";
    }
}

// Update to do
async function editTodo() {
    const todoId = getTodoIdFromUrl();

    if (!todoId) {
        console.log("Missing todo id");
        window.location.href = "/index.html";
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/${todoId}`);

        if (!response.ok) {
            console.log("Error with API");
            window.location.href = "/index.html";
            return;
        }

        const todo = await response.json();

        const titleInput = document.getElementById("title");
        const descriptionInput = document.getElementById("description");
        const detailsInput = document.getElementById("details");
        const statusSelect = document.getElementById("status");
        const saveButton = document.getElementById("saveButton");

        if (!titleInput || !descriptionInput || !detailsInput || !statusSelect) {
            console.log("Missing edit form fields");
            return;
        }

        titleInput.value = todo.title ?? "";
        descriptionInput.value = todo.description ?? "";
        detailsInput.value = todo.details ?? "";

        await populateStatusSelect(statusSelect, todo.status);

        if (saveButton) {
            saveButton.onclick = async function () {
                const todoUpdated = {
                    title: titleInput.value,
                    description: descriptionInput.value,
                    status: statusSelect.value,
                    details: detailsInput.value
                };

                try {
                    const todoUpdateResponse = await fetch(`${API_BASE}/update/${todoId}`, {
                        method: "PUT",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(todoUpdated)
                    });

                    if (todoUpdateResponse.ok) {
                        window.location.href = `/todo.html?id=${todoId}`;
                        return;
                    }

                    alert("Failed to update To Do");
                } catch (error) {
                    console.log("Error updating todo:", error);
                    alert("Could not update To Do");
                }
            };
        }
    } catch (error) {
        console.log("Error:", error);
        window.location.href = "/index.html";
    }
}

// Delete to do
async function deleteTodo(todoId) {
    const isDelete = confirm("Are you sure you want to delete?");
    if (!isDelete) {
        return;
    }

    try {
        const deleteResponse = await fetch(`${API_BASE}/${todoId}`, {
            method: "DELETE"
        });

        if (deleteResponse.ok) {
            window.location.href = "/index.html";
            return;
        }

        console.log("Error deleting to do");
    } catch (error) {
        console.log("Error deleting todo:", error);
    }
}

// helper functions
function getTodoIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

function clearElement(element) {
    if (element) {
        element.innerHTML = "";
    }
}

function createTextElement(tagName, text) {
    const element = document.createElement(tagName);
    element.textContent = text ?? "";
    return element;
}

function renderTodoCard(todo) {
    const todoCard = document.createElement("div");
    todoCard.className = "todoCard";
    todoCard.dataset.todoId = todo.id;

    const titleHeading = document.createElement("h3");
    const titleLink = document.createElement("a");
    titleLink.href = `/todo.html?id=${todo.id}`;
    titleLink.textContent = todo.title ?? "";
    titleHeading.appendChild(titleLink);

    const description = createTextElement("p", todo.description);
    const status = createTextElement("p", todo.statusDescription);

    todoCard.appendChild(titleHeading);
    todoCard.appendChild(description);
    todoCard.appendChild(status);

    return todoCard;
}

function renderTodoDetails(todo) {
    const todoDetails = document.getElementById("todoDetails");
    if (!todoDetails) {
        return;
    }

    clearElement(todoDetails);

    const title = createTextElement("h3", todo.title);
    const description = createTextElement("p", todo.description);
    const status = createTextElement("p", todo.statusDescription);
    const details = createTextElement("p", todo.details);

    todoDetails.appendChild(title);
    todoDetails.appendChild(description);
    todoDetails.appendChild(status);
    todoDetails.appendChild(details);
}

async function populateStatusSelect(statusSelect, selectedStatusCode) {
    clearElement(statusSelect);

    const todoStatusesResponse = await fetch(`${API_BASE}/statuses`);

    if (!todoStatusesResponse.ok) {
        throw new Error("Failed to load todo statuses");
    }

    const statuses = await todoStatusesResponse.json();

    for (const todoStatus of statuses) {
        const option = document.createElement("option");
        option.value = todoStatus.statusCode;
        option.textContent = todoStatus.statusDescription;
        statusSelect.appendChild(option);
    }

    if (selectedStatusCode) {
        statusSelect.value = selectedStatusCode;
    }
}