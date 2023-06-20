package by.pleshkov.database.repository;

import by.pleshkov.database.config.DatabaseConfig;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(2)
    void whenFindAllInvoked_ThenAllTheOrdersAreReturned() {
        Integer[] actual = orderRepository.findAll()
                .stream()
                .map(OrderEntity::getPrice)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(200, 300, 400)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }


    @Test
    @Order(3)
    void whenCreatedInvokeWithOrder_ThenOrderIsSaved() {
        UserEntity testUser = userRepository.findByName("Bred")
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("XXX")
                        .email("xxx@gmail.com")
                        .password("XXX")
                        .build());
        OrderEntity testOrder = OrderEntity.builder()
                .user(testUser)
                .price(500)
                .statusOrder(StatusOrder.NEW)
                .solution(Solution.UNPROCESSED)
                .build();
        orderRepository.save(testOrder);
        List<Integer> all = orderRepository.findAll().stream()
                .map(OrderEntity::getPrice)
                .toList();
        assertTrue(all.contains(testOrder.getPrice()));
    }

    @Test
    @Order(4)
    void whenDeletedInvokeWithOrder_ThenOrderIsDeleted() {
        OrderEntity orderTest = orderRepository.findById(4L).orElse(OrderEntity.builder()
                .price(500)
                .statusOrder(StatusOrder.NEW)
                .solution(Solution.UNPROCESSED)
                .build());
        orderRepository.delete(orderTest);
        Integer[] actual = orderRepository.findAll()
                .stream()
                .map(OrderEntity::getPrice)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(200, 300, 400)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }
}