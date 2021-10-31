package in.alqaholic;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class TodoController {

    private TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @RequestMapping("/todos")
    public String getTodos(Model model) {
        List<Todo> todos = service.getTodos();
        model.addAttribute("todos", todos);
        return "todos";
    }

    @PostMapping("/todos/edit")
    public String editTodo(String id, String newValue) {
        service.editTodo(id, newValue);
        return "redirect:/todos";
    }

    @GetMapping("/todos/edit")
    public String editTodoPage(String id, Model model) {
        Todo todo = service.getTodo(id);
        model.addAttribute("todo", todo);
        return "edit_todo";
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