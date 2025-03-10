package com.world.haymon.libraryapi.controller;

import com.world.haymon.libraryapi.controller.dto.AutorDTO;
import com.world.haymon.libraryapi.controller.dto.ErroResposta;
import com.world.haymon.libraryapi.controller.mappers.AutorMapper;
import com.world.haymon.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.world.haymon.libraryapi.exceptions.RegistroDuplicadoException;
import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
/// http://localhost:8080/autores
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
            Autor autor = mapper.toEntity(dto);
            service.salvar(autor);

            /// http://localhost:8080/autores/dc02abe9-74e2-417b-849d-edc6828d4544
            URI location = gerarHeaderLocation(autor.getId());

            return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        return service.obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> autors = service.filtrarPorExample(nome, nacionalidade);
        List<AutorDTO> autorDTOS = autors
                .stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(autorDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();

        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
