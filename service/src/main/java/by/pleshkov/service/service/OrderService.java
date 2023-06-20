package by.pleshkov.service.service;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }

    public OrderEntity getById(Long id) {
        return orderRepository.findById(id)
                .orElse(OrderEntity.builder()
                        .user(UserEntity.builder()
                                .name("XXX")
                                .surname("XXX")
                                .build())
                        .price(1)
                        .solution(Solution.UNPROCESSED)
                        .statusOrder(StatusOrder.CLOSE)
                        .build());
    }

    public boolean delete(Long id) {
        orderRepository.deleteById(id);
        return orderRepository.findById(id).isEmpty();
    }

    public List<OrderEntity> getAll() {
        return orderRepository.findAll();
    }
}
