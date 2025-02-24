package world.haymon.spring_architecture.motadora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.haymon.spring_architecture.motadora.*;

@RestController
@RequestMapping("/carros")
public class TesteFabricaController {

    @Autowired
    private Motor motor;

    @PostMapping
    public CarroStatus ligarCarro(@RequestBody Chave chave) {
        HondaHRV hondaHRV = new HondaHRV(motor);
        return hondaHRV.darIgnicao(chave);
    }
}
