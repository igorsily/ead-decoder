package com.br.igorsily.ead.auth.mappers;

import com.br.igorsily.ead.auth.dtos.UserDTO;
import com.br.igorsily.ead.auth.entity.User;
import com.br.igorsily.ead.auth.mapper.UserMapper;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserMapperTests {

    private final Faker faker = new Faker(new Locale("pt", "BR"));
    private final UserMapper userMapper;

    @Test
    @DisplayName("Given User when map then return UserDTO")
    public void givenUser_whenMap_thenReturnUserDTO() {

        //Given - precondition or setup

        User user = createUser();

        //When - action or the behaviour that we are going test

        UserDTO userDTO = userMapper.toDTO(user);

        //Then - assertion or verification
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getFullName()).isEqualTo(user.getFullName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("Given UserDTO when map then return User")
    public void givenUserDTO_whenMap_thenReturnUseer() {

        //Given - precondition or setup

        UserDTO userDTO = createUserDTO();

        //When - action or the behaviour that we are going test

        User user = userMapper.toEntity(userDTO);

        //Then - assertion or verification
        assertThat(user).isNotNull();
        assertThat(user.getFullName()).isEqualTo(userDTO.getFullName());
        assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());


    }

    @Test
    @DisplayName("Given UserList When Map  Then UserDTOList")
    public void givenUserList_whenMap_thenReturnUserDTOList() {

        //Given - precondition or setup
        List<User> users = createListUser();

        //When - action or the behaviour that we are going test

        List<UserDTO> userDTOS = userMapper.toDTO(users);

        //Then - assertion or verification
        assertThat(userDTOS).isNotNull();
        assertThat(userDTOS).isNotEmpty();
        assertThat(userDTOS.size()).isEqualTo(users.size());
    }

    @Test
    @DisplayName("Given UserDTOList When Map  Then UserList")
    public void givenUserDTOList_whenMap_thenReturnUserList() {

        //Given - precondition or setup
        List<UserDTO> usersDtos = createListUserDTO();

        //When - action or the behaviour that we are going test

        List<User> users = userMapper.toEntity(usersDtos);

        //Then - assertion or verification
        assertThat(users).isNotNull();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(usersDtos.size());
    }

    private User createUser() {
        return User.builder()
                .fullName(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();
    }

    private UserDTO createUserDTO() {
        return UserDTO.builder()
                .fullName(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .build();
    }

    private List<User> createListUser() {


        return Stream.generate(this::createUser)
                .limit(getRandomNumber()).toList();

    }

    private List<UserDTO> createListUserDTO() {

        return Stream.generate(this::createUserDTO)
                .limit(getRandomNumber()).toList();

    }

    private int getRandomNumber() {
        return (int) ((Math.random() * (10 - 1)) + 1);
    }
}
