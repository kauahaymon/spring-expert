package world.haymon.spring_architecture.motadora.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import world.haymon.spring_architecture.motadora.Motor;
import world.haymon.spring_architecture.motadora.TipoMotor;

@Configuration
public class MontadoraConfiguration {

    @Bean(name = "motorAspirado")
    public Motor motorAspirado() {
        Motor motor = new Motor();
        motor.setModelo("XPTO-0");
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setLitragem(2.0);
        motor.setTipo(TipoMotor.ASPIRADO);
        return motor;
    }


    @Bean(name = "motorEletrico")
    public Motor motorEletrico() {
        Motor motor = new Motor();
        motor.setModelo("TH-0");
        motor.setCavalos(110);
        motor.setCilindros(3);
        motor.setLitragem(1.4);
        motor.setTipo(TipoMotor.ELETRICO);
        return motor;
    }

    @Bean(name = "motorTurbo")
    public Motor motorTurbo() {
        Motor motor = new Motor();
        motor.setModelo("TH-0");
        motor.setCavalos(180);
        motor.setCilindros(3);
        motor.setLitragem(1.5);
        motor.setTipo(TipoMotor.TURBO);
        return motor;
    }
}
