package com.world.haymon.libraryapi.repository;

import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.GeneroLivro;
import com.world.haymon.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    /**
     *  QUERY METHODS
     * <a href="https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html">JPA Query Methods</a>
     */

    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = ?
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = ?
    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL --> References the Entities and the Properties
    // select l.* from livro order by l.titulo, l.preco
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarLivrosOrdenadoPorTituloEPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor;
     */
    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGeneroAutoresBrasileiros();

}
