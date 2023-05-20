package com.br.igorsily.ead.auth;

import com.br.igorsily.ead.auth.entity.User;
import com.br.igorsily.ead.auth.enums.UserStatus;
import com.br.igorsily.ead.auth.enums.UserType;
import com.br.igorsily.ead.auth.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    final Faker faker = new Faker(new Locale("pt", "BR"));

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() > 0) {
            return;
        }

        List<User> users = Stream.generate(() ->
                        User.builder()
                                .username(faker.name().username())
                                .email(faker.internet().emailAddress())
                                .password(faker.internet().password())
                                .fullName(faker.name().fullName())
                                .status(faker.options().option(UserStatus.class))
                                .type(faker.options().option(UserType.class))
                                .phoneNumber(faker.phoneNumber().cellPhone())
                                .document(faker.number().digits(11))
                                .build()
                )
                .limit(10).toList();


        userRepository.saveAll(users);
        userRepository.save(User.builder()
                .username("admin")
                .email("admin@admin.com")
                .fullName("Admin")
                .password("admin")
                .status(UserStatus.ACTIVE)
                .type(UserType.ADMIN)
                .phoneNumber("99999999999")
                .document("99999999999")
                .build());

    }
}
