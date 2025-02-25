package world.haymon.spring_architecture.todos;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoEntity save(@RequestBody TodoEntity entity) {
        return this.todoService.save(entity);
    }

    @PutMapping("{id}")
    public void updateStatus(@PathVariable("id") Integer id,
                                   @RequestBody TodoEntity entity) {
        entity.setId(id);
        this.todoService.updateStatus(entity);
    }

    @GetMapping("{id}")
    public TodoEntity getById(@PathVariable("id") Integer id) {
        return this.todoService.getById(id);
    }
}
