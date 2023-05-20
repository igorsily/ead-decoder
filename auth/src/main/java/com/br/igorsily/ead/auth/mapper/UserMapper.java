package com.br.igorsily.ead.auth.mapper;

import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDTO> {

    @Mapping(target = "oldPassword", ignore = true)
    @Override
    UserDTO toDTO(User entity);

    @Mapping(target = "usersCourses", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Override
    User toEntity(UserDTO dto);
}
