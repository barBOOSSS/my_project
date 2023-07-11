package by.pleshkov.database.repository;

import by.pleshkov.database.config.DatabaseConfig;
import by.pleshkov.database.entity.UserEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SqlGroup({
        @Sql(value = "classpath:test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:purge-data.sql", executionPhase = AFTER_TEST_METHOD)
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    void whenGetByEmailAndPassContainsOnlyUserInvoked_ThenAllTheFilteredByUserRoomsAreReturned() {
        UserEntity testUser = userRepository.findByName("Bred")
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("XXX")
                        .email("xxx@gmail.com")
                        .password("XXX")
                        .build());
        String[] actual = userRepository.findByEmailAndPassword(testUser.getEmail(), testUser.getPassword())
                .stream()
                .map(UserEntity::getName)
                .toArray(String[]::new);
        String[] expected = (List.of("Bred")
                .toArray(String[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(3)
    void whenFindAllInvoked_ThenAllTheUsersAreReturned() {
        String[] actual = userRepository.findAll()
                .stream()
                .map(UserEntity::getEmail)
                .toArray(String[]::new);
        String[] expected = (List.of("pit@gmail.com", "vin@gmail.com")
                .toArray(String[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(4)
    void whenCreatedInvokeWithUser_ThenUserIsSaved() {
        UserEntity testUser = UserEntity.builder()
                .name("Tom")
                .surname("Cruise")
                .email("tom@gmail.com")
                .password("333")
                .build();
        userRepository.save(testUser);
        List<String> allUsers = userRepository.findAll().stream()
                .map(UserEntity::getEmail)
                .toList();
        assertTrue(allUsers.contains(testUser.getEmail()));
    }

    @Test
    @Order(5)
    void whenDeletedInvokeWithUser_ThenUserIsDeleted() {
        UserEntity testUser = userRepository.findById(3L).orElse(UserEntity.builder()
                .name("XXX")
                .surname("XXX")
                .email("XXX@gmail.com")
                .password("333")
                .build());
        userRepository.delete(testUser);
        String[] actual = userRepository.findAll()
                .stream()
                .map(UserEntity::getEmail)
                .toArray(String[]::new);
        String[] expected = (List.of("pit@gmail.com", "vin@gmail.com")
                .toArray(String[]::new));
        assertArrayEquals(expected, actual);
    }
}