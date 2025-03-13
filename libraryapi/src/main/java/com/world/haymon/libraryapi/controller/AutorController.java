package com.world.haymon.libraryapi.controller;

import com.world.haymon.libraryapi.controller.dto.AutorDTO;
import com.world.haymon.libraryapi.controller.mappers.AutorMapper;
import com.world.haymon.libraryapi.model.Autor;
import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.service.AutorService;
import com.world.haymon.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
/// http://localhost:8080/autores
@RequiredArgsConstructor
@Tag(name = "Autores", description = "Tudo sobre Autor")
public class AutorController implements GenericController {

    private final AutorService service;
    private final UsuarioService usuarioService;
    private final AutorMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo autor.")
    @ApiResponses( {
            @ApiResponse(responseCode = "201", description = "Cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);

        /// http://localhost:8080/autores/dc02abe9-74e2-417b-849d-edc6828d4544
        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Obter detalhes", description = "Retorna os dados do autor pelo ID")
    @ApiResponses( {
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
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
    @PreAuthorize("hasAnyRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta um autor existente pelo ID")
    @ApiResponses( {
            @ApiResponse(responseCode = "204", description = "Deletato com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado.")
    })
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
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parâmetros.")
    @ApiResponses( {
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
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
    @PreAuthorize("hasAnyRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza um autor existente.")
    @ApiResponses( {
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
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
