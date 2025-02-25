package world.haymon.spring_architecture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValueExemplo {

    @Value("${app.config.variable}")
    private String variable;

    public void print() {
        System.out.println(variable);
    }
}
