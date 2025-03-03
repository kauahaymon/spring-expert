package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.repository.AutorRepository;
import com.world.haymon.libraryapi.repository.LivroRepository;
import com.world.haymon.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) throw new IllegalArgumentException(
                "Não é possível atualizar Autor que não existe!"
        );
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é possível excluir um Autor que possui Livros!"
            );
        }
        repository.delete(autor);
    }

    public List<Autor> filtrarPor(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        if (nome != null) return repository.findByNome(nome);
        if (nacionalidade != null) return repository.findByNacionalidade(nacionalidade);
        return repository.findAll();
    }

    private boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
