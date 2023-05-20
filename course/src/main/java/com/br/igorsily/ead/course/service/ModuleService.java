package com.br.igorsily.ead.course.service;

import com.br.igorsily.ead.course.dtos.ModuleDTO;

import java.util.UUID;

public interface ModuleService {

    void deleteModule(UUID id);

    ModuleDTO findModuleById(UUID id);
}
