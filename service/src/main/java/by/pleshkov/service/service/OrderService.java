package by.pleshkov.service.service;

import by.pleshkov.database.dto.OrderCreationDto;
import by.pleshkov.database.dto.OrderReadDto;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.repository.OrderRepository;
import by.pleshkov.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public Optional<OrderReadDto> getById(Long id) {
        return orderRepository.findById(id)
                .map(this::toReadDto);
    }

    public List<OrderReadDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    public Long create(OrderCreationDto order) {
        OrderEntity newOrder = OrderEntity
                .builder()
                .price(order.getPrice())
                .statusOrder(order.getStatusOrder())
                .solution(order.getSolution())
                .user(userRepository.findById(order.getUserId())
                        .orElse(UserEntity.builder()
                                .name("XXX")
                                .surname("XXX")
                                .build()))
                .build();
        return orderRepository.save(newOrder).getId();
    }

    public Optional<OrderReadDto> update(Long id, OrderCreationDto update) {
        Optional<OrderEntity> existedOrder = orderRepository.findById(id);
        if (existedOrder.isPresent()) {
            OrderEntity order = existedOrder.get();
            order.setPrice(update.getPrice());
            order.setStatusOrder(update.getStatusOrder());
            order.setSolution(update.getSolution());
            UserEntity user = existedOrder.get().getUser();
            existedOrder.get().setUser(user);
            return Optional.of(toReadDto(orderRepository.save(order)));
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        orderRepository.findById(id)
                .ifPresent(orderRepository::delete);
    }

    private OrderReadDto toReadDto(OrderEntity order) {
        return new OrderReadDto(order.getId(),
                order.getPrice(),
                order.getStatusOrder(),
                order.getSolution(),
                order.getUser().getName());
    }
}
