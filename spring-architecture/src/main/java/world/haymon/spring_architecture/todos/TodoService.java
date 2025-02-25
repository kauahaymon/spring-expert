package world.haymon.spring_architecture.todos;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoEntity save(TodoEntity todo) {
        return todoRepository.save(todo);
    }

    public void updateStatus(TodoEntity todo) {
        this.todoRepository.save(todo);
    }

    public TodoEntity getById(Integer id) {
        return this.todoRepository.findById(id).orElse(null);
    }
}
