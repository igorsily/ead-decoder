package com.br.igorsily.ead.auth.dtos;

import com.br.igorsily.ead.auth.enums.UserStatus;
import com.br.igorsily.ead.auth.enums.UserType;
import com.br.igorsily.ead.auth.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {

    public interface UserDTOView {

        public static interface CreateUser {
        }

        public static interface UpdateUser {
        }

        public static interface ResponseUser {
        }

        public static interface UpdatePasswordUser {
        }

    }

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonView({UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private UUID id;

    @NotBlank(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @UsernameConstraint(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @Size(min = 4, max = 50, groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @JsonView({UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private String username;

    @NotBlank(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @Email(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @JsonView({UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private String email;

    @NotBlank(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @JsonView({UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private String fullName;

    @JsonView({UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private UserStatus status;

    @JsonView({UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private UserType type;

    @NotBlank(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @JsonView({UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private String phoneNumber;

    @NotBlank(groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @Size(min = 11, max = 11, groups = {UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    @JsonView({UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class, UserDTOView.ResponseUser.class})
    private String document;

    private String imageUrl;

    @NotBlank(groups = {UserDTOView.CreateUser.class,  UserDTOView.UpdatePasswordUser.class})
    @Size(min = 8, max = 50, groups = {UserDTOView.CreateUser.class, UserDTOView.UpdatePasswordUser.class})
    @JsonView({UserDTOView.CreateUser.class, UserDTOView.UpdateUser.class})
    private String password;

    @NotBlank(groups = {UserDTOView.UpdatePasswordUser.class})
    @Size(min = 8, max = 50, groups = {UserDTOView.UpdatePasswordUser.class})
    @JsonView({UserDTOView.UpdateUser.class})
    private String oldPassword;



}
