package com.br.igorsily.ead.auth.mapper;

import org.mapstruct.Mapper;

import java.util.List;

public interface GenericMapper <E, D> {

    D toDTO(E entity);

    E toEntity(D dto);

    List<D> toDTO(List<E> entityList);

    List<E> toEntity(List<D> dtoList);

}
