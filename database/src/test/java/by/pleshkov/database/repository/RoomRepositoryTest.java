package by.pleshkov.database.repository;

import by.pleshkov.database.config.DatabaseConfig;
import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(2)
    void whenFindAllInvoked_ThenAllTheRoomsAreReturned() {
        Integer[] actual = roomRepository.findAll()
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2, 3, 4)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(3)
    void whenFindAllByUserInvoked_ThenAllTheRoomsOfUserAreReturned() {
        Optional<UserEntity> bredPit = userRepository.findByName("Bred");
        List<RoomEntity> allByUsersContains = roomRepository.findAllByUsersContains(bredPit.get());
        Integer[] actual = roomRepository.findAllByUsersContains(bredPit.get())
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(4)
    void whenFindAllByFilterContainsOnlyUserInvoked_ThenAllTheFilteredByUserRoomsAreReturned() {
        RoomFilter filter = RoomFilter.builder()
                .userName("Bred")
                .build();
        Optional<UserEntity> testUser = userRepository.findByName(filter.getUserName());
        Integer[] actual = roomRepository.findByFilter(filter)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(5)
    void whenFindAllByFilterContainsUserAndPlacesInvoked_ThenAllTheFilteredByUserAndPlacesRoomsAreReturned() {
        RoomFilter filter = RoomFilter.builder()
                .userName("Bred")
                .places(1)
                .build();
        Integer[] actual = roomRepository.findByFilter(filter)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(6)
    void whenCreatedInvokeWithRoom_ThenRoomIsSaved() {
        RoomEntity testRoom = RoomEntity.builder()
                .number(1)
                .places(2)
                .price(300)
                .classRoom(ClassRoom.STANDARD)
                .statusRoom(StatusRoom.FREE)
                .build();
        RoomEntity roomEntity = roomRepository.save(testRoom);
        List<Integer> allNumbers = roomRepository.findAll().stream()
                .map(RoomEntity::getNumber)
                .toList();
        assertTrue(allNumbers.contains(testRoom.getNumber()));
    }

    @Test
    @Order(7)
    void whenDeletedInvokeWithRoom_ThenRoomIsDeleted() {
        RoomEntity roomTest = roomRepository.findById(5L).orElse(RoomEntity.builder()
                .number(100)
                .price(500)
                .statusRoom(StatusRoom.FREE)
                .classRoom(ClassRoom.STANDARD)
                .places(1)
                .build());
        roomRepository.delete(roomTest);
        Integer[] actual = roomRepository.findAll()
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2, 3, 4)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(8)
    void testFindAllByAuthor() {
        List<Integer> allNumbers = roomRepository.findAllBy("Bred").stream()
                .map(RoomEntity::getNumber)
                .toList();
        assertTrue(allNumbers.containsAll(List.of(1, 2)));
    }

    @Test
    @Order(9)
    @Transactional
    void setPriceById() {
        Optional<RoomEntity> number1 = roomRepository.findByNumber(1);
        roomRepository.setNumberById(100, number1.get().getId());
        assertTrue(roomRepository.findByNumber(100).isPresent());
    }
}