package in.alqaholic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class SeleniumTesting {

  public static void main(String[] args) {
    SpringApplication.run(SeleniumTesting.class, args);
  }

}

class Todo {
  public Todo(String value) {
    this.id = UUID.randomUUID().toString();
    this.value = value;
    this.completed = false;
  }
  String id;
  String value;
  boolean completed;

  public String getId() {
      return this.id;
  }

  public String getValue() {
      return value;
  }

  public boolean isCompleted() {
      return completed;
  }

  public void toggleCompleted() {
    this.completed = !completed;
  }
}

@Service
class TodoService {
  private List<Todo> todos;

  TodoService() {
    this.todos = new ArrayList<>();

    this.todos.add(new Todo("Drink coffee"));
    this.todos.add(new Todo("Eat breakfast"));
    this.todos.add(new Todo("Go to college"));
  }

  public List<Todo> getTodos() {
      return todos;
  }

  public void addTodo(String value) {
    this.todos.add(new Todo(value));
  }

  public void toggleTodo(String id) {
    this.todos.forEach(todo -> { if (todo.getId().equals(id)) todo.toggleCompleted(); });
  }

  public void deleteTodo(String id) {
    this.todos.removeIf(todo -> todo.getId().equals(id));
  }
}

@Controller
class TodoController {

  @Autowired private TodoService service;

  @RequestMapping("/todos")
  public String getTodos(Model model) {
    List<Todo> todos = service.getTodos();
    model.addAttribute("todos", todos);
    return "todos";
  }

  @RequestMapping("/todos/delete")
  public String deleteTodo(String id) {
    service.deleteTodo(id);
    return "redirect:/todos";
  }
  
  @RequestMapping("/todos/toggle")
  public String toggleTodo(String id) {
    service.toggleTodo(id);
    return "redirect:/todos";
  }

  @RequestMapping("/todos/create")
  public String addTodo(String value) {
    service.addTodo(value);
    return "redirect:/todos";
  }
}
