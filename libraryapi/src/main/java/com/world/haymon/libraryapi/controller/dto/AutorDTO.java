package com.world.haymon.libraryapi.controller.dto;

import com.world.haymon.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatório") String nome,
        @NotNull(message = "Campo obrigatório") LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório") String nacionalidade) {

    public Autor mapearParaEntidade() {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);
        return autor;
    }
}
