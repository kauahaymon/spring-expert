package com.world.haymon.libraryapi.service;

import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.GeneroLivro;
import com.world.haymon.libraryapi.model.Livro;
import com.world.haymon.libraryapi.repository.AutorRepository;
import com.world.haymon.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacoesService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    /**
     * Commit -> Confirms changes
     * Rollback -> Undo changes
     */
    @Transactional
    public void executar() {
        // 1. Begin

        // 2. Save "Autor"
        Autor autor = new Autor();
        autor.setNome("Roberto");
        autor.setDataNascimento(LocalDate.of(2001, 3, 15));
        autor.setNacionalidade("Brasileira");

        autorRepository.save(autor);

        // 3. Save "Livro"
        Livro livro = new Livro();
        livro.setTitulo("Romance");
        livro.setIsbn("9hj29-98g84");
        livro.setPreco(BigDecimal.valueOf(140));
        livro.setDataPublicacao(LocalDate.of(1970, 1, 22));
        livro.setGenero(GeneroLivro.FICCAO);

        livro.setAutor(autor);

        livroRepository.save(livro);

        // 4. Rollback or (Commit)
        if (autor.getNome().equals("Roberto")) {
            throw new RuntimeException("Rollback!");
        }
    }

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository.findById(UUID.fromString("bac06303-aa63-459d-a704-ec37e92fcd87")).get();

        livro.setGenero(GeneroLivro.BIOGRAFIA);
    }
}
