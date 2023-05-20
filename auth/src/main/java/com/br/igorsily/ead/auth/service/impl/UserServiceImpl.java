package com.br.igorsily.ead.auth.service.impl;

import com.br.igorsily.ead.auth.dtos.InstructorDTO;
import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.entity.User;
import com.br.igorsily.ead.auth.enums.UserStatus;
import com.br.igorsily.ead.auth.enums.UserType;
import com.br.igorsily.ead.auth.exception.ConflictException;
import com.br.igorsily.ead.auth.exception.ResourceNotFoundException;
import com.br.igorsily.ead.auth.mapper.UserMapper;
import com.br.igorsily.ead.auth.repository.UserRepository;
import com.br.igorsily.ead.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    private final UserMapper userMapper;

    @Override
    public UserDTO findUserById(UUID id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) throw new ResourceNotFoundException("User not found");

        return userMapper.toDTO(userOptional.get());

    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        log.info("POST saveUser");

        if (this.existsByUsername(userDTO.getUsername())) throw new ConflictException("Username already exists");
        if (this.existsByEmail(userDTO.getEmail())) throw new ConflictException("Email already exists");

        User user = userMapper.toEntity(userDTO);
        user.setStatus(UserStatus.ACTIVE);
        user.setType(UserType.STUDENT);

        userRepository.save(user);

        log.info("User saved successfully userId {} ", user.getId());

        return userMapper.toDTO(user);

    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(userMapper::toDTO).toList();
    }

    @Override
    public Page<UserDTO> findAll(Specification<User> spec, Pageable pageable) {

        Page<User> users = this.userRepository.findAll(spec, pageable);

        return users.map(userMapper::toDTO);

    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void delete(UUID id) {
        log.debug("DELETE deleteUser userId deleted {} ", id);

        this.findUserById(id);

        userRepository.deleteById(id);
        log.info("User deleted successfully userId {} ", id);
    }

    @Override
    public UserDTO update(UUID id, UserDTO userDTO) {
        log.debug("PUT updateUser userId saved {} ", userDTO.getId());

        this.findUserById(id);

        if (this.existsByUsername(userDTO.getUsername())) throw new ConflictException("Username already exists");
        if (this.existsByEmail(userDTO.getEmail())) throw new ConflictException("Email already exists");

        User user = userMapper.toEntity(userDTO);
        user.setId(id);

        userRepository.save(user);

        log.info("User updated successfully userId {} ", userDTO.getId());

        return userMapper.toDTO(user);
    }

    @Override
    public Object updatePassword(UUID userId, UserDTO userDTO) {
        log.debug("PUT updatePassword userDto received {} ", userDTO.toString());

        var oldUser = this.findUserById(userId);

        if (oldUser.getPassword().equals(userDTO.getOldPassword())) {
            log.warn("Mismatched old password userId {} ", userId);
            throw new ConflictException("The new password must be different from the old password");
        }

        oldUser.setPassword(userDTO.getPassword());

        userRepository.save(userMapper.toEntity(oldUser));

        log.info("Password updated successfully userId {} ", userId);

        return oldUser;
    }

    @Override
    public void saveInstructor(InstructorDTO instructorDTO) {
        UserDTO userDTO = this.findUserById(instructorDTO.userId());

        User user = userMapper.toEntity(userDTO);
        user.setType(UserType.INSTRUCTOR);

        userRepository.save(user);

    }

}
