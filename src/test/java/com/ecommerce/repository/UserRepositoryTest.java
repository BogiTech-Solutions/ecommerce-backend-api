package com.ecommerce.repository;

import com.ecommerce.entity.Role;
import com.ecommerce.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by email")
    void findByEmail_Success() {
        User user = User.builder()
                .email("test@ecommerce.com")
                .password("encoded_password")
                .firstName("Test")
                .lastName("User")
                .role(Role.ROLE_USER)
                .enabled(true)
                .build();

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("test@ecommerce.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@ecommerce.com");
    }
}