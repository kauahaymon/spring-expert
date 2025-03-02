package com.world.haymon.libraryapi.repository;

import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.GeneroLivro;
import com.world.haymon.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();

        livro.setTitulo("Ciencias Contabeis");
        livro.setIsbn("53929-13184");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(1989, 8, 23));
        livro.setGenero(GeneroLivro.CIENCIA);

        var idAutor = UUID.fromString("18bed589-6bf1-4286-9b76-126d7628c328");
        Autor autor = autorRepository.findById(idAutor).orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest() { // NO cascade
        Livro livro = new Livro();

        livro.setTitulo("Romance");
        livro.setIsbn("9hj29-98g84");
        livro.setPreco(BigDecimal.valueOf(140));
        livro.setDataPublicacao(LocalDate.of(1970, 1, 22));
        livro.setGenero(GeneroLivro.FICCAO);

        Autor autor = new Autor();
        autor.setNome("Guilherme");
        autor.setDataNascimento(LocalDate.of(2001, 3, 15));
        autor.setNacionalidade("Brasileira");

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest() { // Persist "Livro" and "Autor" in Cascade
        Livro livro = new Livro();

        livro.setTitulo("UFO");
        livro.setIsbn("98929-98184");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setDataPublicacao(LocalDate.of(1080, 8, 23));
        livro.setGenero(GeneroLivro.FICCAO);

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setDataNascimento(LocalDate.of(2003, 2, 11));
        autor.setNacionalidade("Americana");

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivroTest() {
        UUID livroId = UUID.fromString("e95c6471-a3b5-41ec-96d9-18c5498d7680");
        var livroParaAtualizar = repository.findById(livroId).get();

        UUID autorId = UUID.fromString("98b48067-e1f6-4843-9ead-55b6cfa25230");
        var guilherme = autorRepository.findById(autorId).get();

        livroParaAtualizar.setAutor(guilherme);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID livroId = UUID.fromString("e95c6471-a3b5-41ec-96d9-18c5498d7680");
        repository.deleteById(livroId);
    }

    @Test
    void buscarLivroTest() {
        UUID id = UUID.fromString("2478679b-02fb-4cdc-bcef-b194f2635397");
        var livro = repository.findById(id).orElse(null);

        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> livrosLista = repository.findByTitulo("Harry Potter e pedra filosofal");
        livrosLista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorIsbn() {
        List<Livro> livrosLista = repository.findByIsbn("9hj29-98g84");
        livrosLista.forEach(System.out::println);
    }

    @Test
    void pesquisarPorTituloEPrecoTest() {
        var preco = BigDecimal.valueOf(240.00);
        var titulo = "Romance";

        List<Livro> livrosLista = repository.findByTituloAndPreco(titulo, preco);
        livrosLista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL() {
        var resultado = repository.listarLivrosOrdenadoPorTituloEPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosTest() {
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresGeneroLivrosAutoresTest() {
        var resultado = repository.listarGeneroAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryEParamTest() {
        var genero = GeneroLivro.FICCAO;
        var ordem = "titulo";

        var namedParam = repository.findByGeneroNamedParam(genero, ordem);
        var positionalParam = repository.findByGeneroPositionalParam(genero, ordem);

        positionalParam.forEach(System.out::println);
    }

    @Test
    void deletarPorGeneroTest() {
        repository.deletarPorGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updatePrecoTest() {
        var newPreco = BigDecimal.valueOf(200);
        var id = UUID.fromString("dc02abe9-74e2-417b-849d-edc6828d4544");

        repository.updatePreco(newPreco, id);
    }
}
