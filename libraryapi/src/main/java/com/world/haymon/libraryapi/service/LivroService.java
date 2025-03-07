package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.model.GeneroLivro;
import com.world.haymon.libraryapi.model.Livro;
import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.repository.LivroRepository;
import com.world.haymon.libraryapi.security.SecurityService;
import com.world.haymon.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.world.haymon.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;
    private final SecurityService securityService;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    public Page<Livro> filtrarPor(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina) {

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (isbn != null)
            specs = specs.and(isbnEqual(isbn));

        if (titulo != null)
            specs = specs.and(tituloLike(titulo));

        if (genero != null)
            specs = specs.and(generoEqual(genero));

        if (anoPublicacao != null)
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));

        if (nomeAutor != null)
            specs = specs.and(nomeAutorLike(nomeAutor));

        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageable);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) throw new IllegalIdentifierException("Não é possível atualizar um Livro que não existe!");
        validator.validar(livro);
        repository.save(livro);
    }
}
