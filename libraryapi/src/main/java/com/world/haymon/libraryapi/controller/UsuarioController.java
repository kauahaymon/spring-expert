package com.world.haymon.libraryapi.controller;

import com.world.haymon.libraryapi.controller.dto.UsuarioDTO;
import com.world.haymon.libraryapi.controller.mappers.UsuarioMapper;
import com.world.haymon.libraryapi.model.Usuario;
import com.world.haymon.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto) {
        Usuario entity = mapper.toEntity(dto);
        service.salvar(entity);
    }
}