package in.alqaholic;

import java.util.UUID;

public class Todo {

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

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void toggleCompleted() {
        this.completed = !completed;
    }
}