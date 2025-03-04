package com.world.haymon.libraryapi.controller.mappers;

import com.world.haymon.libraryapi.controller.dto.AutorDTO;
import com.world.haymon.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Creates the implementation in compile time
 */
@Mapper(componentModel = "spring")
public interface AutorMapper {

    // In case they have different names to map
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor entity);
}
