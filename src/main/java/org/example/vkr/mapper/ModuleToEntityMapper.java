package org.example.vkr.mapper;

import org.example.vkr.dao.ModuleEntity;
import org.example.vkr.models.Module;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModuleToEntityMapper {
    ModuleEntity moduleToModuleEntity(Module module);
    Module moduleEntityToModule(ModuleEntity moduleEntity);
}