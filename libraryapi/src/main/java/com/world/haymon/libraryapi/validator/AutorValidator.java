package com.world.haymon.libraryapi.validator;

import com.world.haymon.libraryapi.exceptions.RegistroDuplicadoException;
import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorOptional = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());
        // when saving
        if (autor.getId() == null) {
            return autorOptional.isPresent();
        }
        // when updating
        return !autorOptional.get().getId().equals(autor.getId());
    }
}
