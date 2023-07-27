package org.example.vkr.mapper;

import org.example.vkr.dao.ProjectEntity;
import org.example.vkr.models.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectToEntityMapper {
    ProjectEntity projectToProjectEntity(Project project);
    Project projectEntityToProject(ProjectEntity projectEntity);
}