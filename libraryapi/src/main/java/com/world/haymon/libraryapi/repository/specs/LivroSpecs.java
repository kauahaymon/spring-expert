package com.world.haymon.libraryapi.repository.specs;

import com.world.haymon.libraryapi.model.GeneroLivro;
import com.world.haymon.libraryapi.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) -> cb.like( cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, cb) -> cb.equal( cb.function(
                "to_char",
                String.class,
                root.get("dataPublicacao"),
                cb.literal("YYYY")
        ), anoPublicacao.toString() );
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor) {
        return (root, query, cb) -> {
            return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%" );
        };
    }
}
