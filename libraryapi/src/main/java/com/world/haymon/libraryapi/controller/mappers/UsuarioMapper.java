package com.world.haymon.libraryapi.controller.mappers;

import com.world.haymon.libraryapi.controller.dto.UsuarioDTO;
import com.world.haymon.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}