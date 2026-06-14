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