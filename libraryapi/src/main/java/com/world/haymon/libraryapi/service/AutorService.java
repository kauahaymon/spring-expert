package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public Autor salvar(Autor autor) {
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor) {
        repository.delete(autor);
    }

    public List<Autor> filtrarPor(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        if (nome != null) return repository.findByNome(nome);
        if (nacionalidade != null) return repository.findByNacionalidade(nacionalidade);
        return repository.findAll();
    }
}
