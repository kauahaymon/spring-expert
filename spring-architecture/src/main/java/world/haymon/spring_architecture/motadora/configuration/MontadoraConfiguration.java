package world.haymon.spring_architecture.motadora.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import world.haymon.spring_architecture.motadora.Motor;
import world.haymon.spring_architecture.motadora.TipoMotor;

@Configuration
public class MontadoraConfiguration {

    @Bean // A function that returns a Constructed Object, is a Bean
    public Motor motorAspirado() {
        Motor motor = new Motor();
        motor.setModelo("XPTO-0");
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setLitragem(2.0);
        motor.setTipo(TipoMotor.ASPIRADO);
        return motor;
    }
}
