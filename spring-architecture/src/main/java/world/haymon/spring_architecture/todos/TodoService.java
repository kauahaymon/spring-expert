package world.haymon.spring_architecture.todos;

import org.springframework.stereotype.Service;
import world.haymon.spring_architecture.todos.components.MailSender;
import world.haymon.spring_architecture.todos.components.TodoValidator;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoValidator todoValidator;
    private final MailSender mailSender;

    public TodoService(TodoRepository todoRepository,
                       TodoValidator todoValidator,
                       MailSender mailSender) {
        this.todoRepository = todoRepository;
        this.todoValidator = todoValidator;
        this.mailSender = mailSender;
    }

    public TodoEntity save(TodoEntity todo) {
        this.todoValidator.validate(todo);
        return todoRepository.save(todo);
    }

    public void updateStatus(TodoEntity todo) {
        this.todoRepository.save(todo);
        String status = todo.getDone().equals(Boolean.TRUE) ? "Concluído" : "Não concluído";
        this.mailSender.send("Todo '" + todo.getDescription() + "' foi atualizado para " + status);
    }

    public TodoEntity getById(Integer id) {
        return this.todoRepository.findById(id).orElse(null);
    }
}
