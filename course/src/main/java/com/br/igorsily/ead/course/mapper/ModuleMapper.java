package com.br.igorsily.ead.course.mapper;

import com.br.igorsily.ead.course.dtos.ModuleDTO;
import com.br.igorsily.ead.course.entity.Module;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ModuleMapper extends GenericMapper<Module, ModuleDTO> {
}
