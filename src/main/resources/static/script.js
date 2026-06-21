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
            status.textContent = todo.statusDescription;

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

    const deleteButton = document.getElementById("deleteButton");
    deleteButton.onclick = async function(){
        const isDelete = confirm("Are you sure you want to delete?");
        if(isDelete){
          const deleteResponse = await fetch("/api/v1/todo/" + todoId, {method: "DELETE"});

          if(deleteResponse.ok){
              window.location.href = "/index.html";
          } else {
              console.log("Error deleting to do");
          }
        } else {
            return;
        }
    }

    const todoBox = document.getElementById("todoBox");

    const title = document.createElement("h3");
    title.textContent = todo.title;

    const description = document.createElement("p");
    description.textContent = todo.description;

    const status = document.createElement("p");
    status.textContent = todo.statusDescription;

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

    try{
        const response = await fetch("/api/v1/todo/" + todoId);

        if(!response.ok){
            console.log("Error with API");
            window.location.href = "/index.html";
        }

        const todo = await response.json();

        var title = document.getElementById("title");
        title.value = todo.title;

        var description = document.getElementById("description");
        description.value = todo.description;

        var statusSelect = document.getElementById("status");
        const todoStatusesResponse = await fetch("/api/v1/todo/statuses");
        const statuses = await todoStatusesResponse.json();

        for(const status of statuses){
            var option = document.createElement("option");
            option.value = status.statusCode;
            option.textContent = status.statusDescription;
            statusSelect.appendChild(option);
        }

        statusSelect.value = todo.status;

        var details = document.getElementById("details");
        details.value = todo.details;

        var saveButton = document.getElementById("saveButton");
        saveButton.onclick = async function(){
            title = document.getElementById("title").value;
            description = document.getElementById("description").value;
            status = document.getElementById("status").value;
            details = document.getElementById("details").value;

            const todoUpdated = {
                title: title,
                description: description,
                status: status,
                details: details
            }

            const todoUpdateResponse = await fetch("/api/v1/todo/update/" + todoId, {method: "PUT", headers: {"Content-Type": "application/json"}, body: JSON.stringify(todoUpdated)});

            if(todoUpdateResponse.ok){
                window.location.href = "/todo.html?id=" + todoId;
            }
        }

    } catch (error) {
        console.log("Error: " + error);
        window.location.href = "/index.html";
    }
}