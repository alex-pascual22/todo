async function createTodo(){
    // Read values from the page
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const details = document.getElementById("details").value;

    // create to do object that matches CreateToDoDTO
    const todo = {
        title: title,
        description: description,
        details: details
    }

    // Send POST request to SpringBoot
    const response = await fetch("/api/v1/todo/add", {method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(todo)});

    // Check if response succeeded
    if(response.ok){
        alert("To Do Saved!");
        window.location.href = "/index.html";
    } else {
        alert("Failed to create To Do");
    }
}

async function retrieveToDos(){
    const response = await fetch("/api/v1/todo/");

    if(response.ok){
        const todos = await response.json();

        //can use todos.length to check if there are to dos already

        const todoContainer = document.getElementById("todoContainer");
        for(const todo of todos){
            const todoCard = document.createElement("div");
            todoCard.className = "todoCard";

            const title = document.createElement("h3");
            const titleLink = document.createElement("a");
            titleLink.href = "/todo.html?id=" + todo.id;
            titleLink.textContent = todo.title;
            title.appendChild(titleLink);

            const description = document.createElement("p");
            description.textContent = todo.description;

            const status = document.createElement("p");
            status.textContent = todo.status;

            todoCard.appendChild(title);
            todoCard.appendChild(description);
            todoCard.appendChild(status);

            todoContainer.appendChild(todoCard);
        }
    } else {
        console.log("Failed to retrieve To Dos");
        return;
    }
}

async function retrieveTodoById(){
    const params = new URLSearchParams(window.location.search);
    const todoId = params.get("id");

    const response = await fetch("/api/v1/todo/" + todoId);
    const todo = await response.json();

    const editButton = document.getElementById("editButton");
    editButton.onclick = function(){
        window.location.href = "/editTodo.html?id=" + todoId;
    }

    const todoBox = document.getElementById("todoBox");

    const title = document.createElement("h3");
    title.textContent = todo.title;

    const description = document.createElement("p");
    description.textContent = todo.description;

    const status = document.createElement("p");
    status.textContent = todo.status;

    const details = document.createElement("p");
    details.textContent = todo.details;

    todoBox.appendChild(title);
    todoBox.appendChild(description);
    todoBox.appendChild(status);
    todoBox.appendChild(details);
}


async function editTodo(){
    const params = new URLSearchParams(window.location.search);
    const todoId = params.get("id");

    console.log(todoId);
}