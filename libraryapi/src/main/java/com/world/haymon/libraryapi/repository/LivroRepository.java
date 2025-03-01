package com.world.haymon.libraryapi.repository;

import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query Method
    List<Livro> findByAutor(Autor autor);
}
