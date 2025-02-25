package world.haymon.spring_architecture.todos.components;

import org.springframework.stereotype.Component;

@Component
public class MailSender {

    public void send(String message) {
        System.out.println("Enviando email: " + message);
    }
}
