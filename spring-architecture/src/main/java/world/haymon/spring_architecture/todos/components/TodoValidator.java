package world.haymon.spring_architecture.todos.components;

import org.springframework.stereotype.Component;
import world.haymon.spring_architecture.todos.TodoEntity;
import world.haymon.spring_architecture.todos.TodoRepository;

@Component
public class TodoValidator {

    private final TodoRepository todoRepository;

    public TodoValidator(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void validate(TodoEntity todo) {
        if (todoExistsWithDescription(todo.getDescription())) {
            throw new IllegalArgumentException("Essa descrição já existe!");
        }
    }

    public boolean todoExistsWithDescription(String description) {
        return this.todoRepository.existsByDescription(description);
    }
}
