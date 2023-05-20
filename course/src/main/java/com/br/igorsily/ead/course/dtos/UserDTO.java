package com.br.igorsily.ead.course.dtos;

import com.br.igorsily.ead.course.enums.UserStatus;
import com.br.igorsily.ead.course.enums.UserType;
import lombok.Data;

import java.util.UUID;

public record UserDTO(
        UUID userId,
        String username,
        String email,
        String fullName,
        UserStatus userStatus,
        UserType userType,
        String phoneNumber,
        String cpf,
        String imageUrl
) {
}
