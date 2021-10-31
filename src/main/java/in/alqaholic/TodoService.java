package in.alqaholic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
class TodoService {
	private List<Todo> todos;

	TodoService() {
		this.todos = new ArrayList<>();
		// this.todos.add(new Todo("Drink coffee"));
		// this.todos.add(new Todo("Eat breakfast"));
		// this.todos.add(new Todo("Go to college"));
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void addTodo(String value) {
		this.todos.add(new Todo(value));
	}

	public void toggleTodo(String id) {
		this.todos.forEach(todo -> {
			if (todo.getId().equals(id))
				todo.toggleCompleted();
		});
	}

	public void deleteTodo(String id) {
		this.todos.removeIf(todo -> todo.getId().equals(id));
	}

    public void editTodo(String id, String newValue) {
		this.todos.forEach(todo -> {
			if (todo.getId().equals(id))
				todo.setValue(newValue);
		});
    }

    public Todo getTodo(String id) {
        return this.todos.stream().filter(todo -> todo.getId().equals(id)).findFirst().get();
    }
}