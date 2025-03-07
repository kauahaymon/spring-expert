package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.repository.AutorRepository;
import com.world.haymon.libraryapi.repository.LivroRepository;
import com.world.haymon.libraryapi.security.SecurityService;
import com.world.haymon.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor /// Creates constructor to required fields
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
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

    public List<Autor> filtrarPorExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataCadastro", "dataNascimento")
                .withIgnoreNullValues() /// Ignores Null Values
                .withIgnoreCase() /// Ignores String Cases
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); /// Contains Any String Related
        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    private boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
