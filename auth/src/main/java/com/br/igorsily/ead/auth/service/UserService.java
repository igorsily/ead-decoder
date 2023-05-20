package com.br.igorsily.ead.auth.service;

import com.br.igorsily.ead.auth.dtos.InstructorDTO;
import com.br.igorsily.ead.auth.dtos.UserCourseDTO;
import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserDTO> findAll();

    Page<UserDTO> findAll(Specification<User> spec, Pageable pageable);

    UserDTO findUserById(UUID id);

    UserDTO saveUser(UserDTO userDTO);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void delete(UUID id);

    UserDTO update(UUID id, UserDTO userDTO);


    Object updatePassword(UUID userId, UserDTO userDTO);

    void saveInstructor(InstructorDTO instructorDTO);

}
