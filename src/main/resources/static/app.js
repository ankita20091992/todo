const taskInput = document.getElementById("taskInput");
const addBtn = document.getElementById("addBtn");
const taskList = document.getElementById("taskList");
const dueDateInput = document.getElementById("dueDateInput");

async function loadTasks() {
	//GET is default
	const res = await fetch("/api/todos");
	//parse JSON response body into JS [] of task objects
	const tasks = await res.json();
	//clear the list before redrawing
	taskList.innerHTML = "";
	tasks.forEach(task => {
		//1. new empty <li> to hold this one task
		const li = document.createElement("li");
		//2. make checkbox
		const checkbox = document.createElement("input");
		checkbox.type = "checkbox"; //make it a checkbox specifically
		checkbox.checked = task.completed; //tick it if task is done
		checkbox.onclick = () => toggleTask(task.id); //on click, toggle THIS 
		//3. make text span for title
		const text = document.createElement("span");
		const due = document.createElement("span");
		if(task.dueDate) {
			due.textContent = " (due: " + task.dueDate + ")";
		} else {
			due.textContent = "";
		}
		text.textContent = task.title; //put the task's title text in it
		//4. make delete button
		const deleteBtn = document.createElement("button");
		deleteBtn.textContent = "Delete"; //put task title text in it
		deleteBtn.onclick = () => deleteTask(task.id); //on click, delete this task
		//5. put all three pieces inside the <li>
		li.appendChild(checkbox);
		li.appendChild(text);
		li.appendChild(due);
		li.appendChild(deleteBtn);
		if (task.completed) {
			li.classList.add("completed");
		}
		//6. put the finished <li> into the list on the page
		taskList.appendChild(li);
	});
}
async function addTask() {
	const title = taskInput.value;
	const dueDate = dueDateInput.value;
	if (title === "") { return; }
	await fetch("/api/todos", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ title: title, dueDate: dueDate || null })
	});
	taskInput.value = ""; //clear the box
	dueDateInput.value = "";
	loadTasks(); //redraw with the new task
	}
async function toggleTask(id) {
	await fetch("/api/todos/" + id, { method: "PUT"});
	loadTasks();
}
async function deleteTask(id) {
	await fetch("/api/todos/" + id, { method: "DELETE" });
	loadTasks();
}
addBtn.addEventListener("click", addTask);
loadTasks(); //draw the list as soon as the page opens
