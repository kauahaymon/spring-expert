package com.world.haymon.libraryapi.repository;

import com.world.haymon.libraryapi.service.TransacoesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    private TransacoesService service;

    @Test
    void transacaoSimples() {
        service.executar();
    }

    @Test
    void transacaoEstadoManaged() {
        service.atualizacaoSemAtualizar();
    }
}
