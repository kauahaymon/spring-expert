package com.world.haymon.libraryapi.repository;

import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.GeneroLivro;
import com.world.haymon.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setDataNascimento(LocalDate.of(2000, 1, 10));
        autor.setNacionalidade("Americana");

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTeste() {
        var id = UUID.fromString("18bed589-6bf1-4286-9b76-126d7628c328");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();

            System.out.println("Dados do autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1996, 3, 18));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        long count = repository.count();
        System.out.println("Contagem de autores: " + count);
    }

    @Test
    public void deletarPorId() {
        var id = UUID.fromString("272a8e34-40a1-4adb-940a-ae03cca21daa");
        repository.deleteById(id);
    }

    @Test
    public void deletarTest() {
        var id = UUID.fromString("7527a1f3-9f9b-40e1-835d-d1462e8450a5");
        Autor autor = repository.findById(id).get();
        repository.delete(autor);
    }

    @Test
    public void salvarAutorComLivros() {
        Autor autor = new Autor();
        autor.setNome("J.K");
        autor.setDataNascimento(LocalDate.of(2005, 2, 11));
        autor.setNacionalidade("Americana");

        Livro livro1 = new Livro();
        livro1.setTitulo("Harry Potter e pedra filosofal");
        livro1.setIsbn("12359-95644");
        livro1.setPreco(BigDecimal.valueOf(140));
        livro1.setDataPublicacao(LocalDate.of(2001, 8, 23));
        livro1.setGenero(GeneroLivro.FICCAO);
        livro1.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setTitulo("Harry Potter e a câmara secreta");
        livro2.setIsbn("424353-15643");
        livro2.setPreco(BigDecimal.valueOf(160));
        livro2.setDataPublicacao(LocalDate.of(2002, 3, 20));
        livro2.setGenero(GeneroLivro.FICCAO);
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());

        autor.getLivros().add(livro1);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    public void listarLivrosAutorTest() {
        var id = UUID.fromString("3f36aef3-f611-4f44-92a3-36bd8e5c0082");
        var autor = repository.findById(id).get();

        List<Livro> livrosAutor = livroRepository.findByAutor(autor);
        autor.setLivros(livrosAutor);

        autor.getLivros().forEach(System.out::println);
    }
}
