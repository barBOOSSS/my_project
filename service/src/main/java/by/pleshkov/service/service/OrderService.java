package by.pleshkov.service.service;

import by.pleshkov.database.dto.OrderCreationDto;
import by.pleshkov.database.dto.OrderReadDto;
import by.pleshkov.database.dto.RoomReadDto;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.repository.OrderRepository;
import by.pleshkov.database.repository.RoomRepository;
import by.pleshkov.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.pleshkov.database.constant.Solution.DENIED;
import static by.pleshkov.database.constant.StatusRoom.FREE;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

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

    public List<OrderReadDto> getAllByUSer(Long id) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getUser().getId().equals(id))
                .map(this::toReadDto)
                .toList();
    }

    public Long create(OrderCreationDto order) {
        OrderEntity newOrder = OrderEntity
                .builder()
                .price(order.getPrice())
                .numberRoom(order.getNumberRoom())
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
        if (update.getSolution().equals(DENIED)) {
            Optional<RoomEntity> byNumber = roomRepository.findByNumber(update.getNumberRoom());
            Long userId = byNumber.get().getUsers().stream()
                    .map(UserEntity::getId)
                    .findFirst()
                    .orElse(null);
            byNumber.get().removeUser(userRepository.findById(userId).get());
            byNumber.get().setStatusRoom(FREE);
            roomRepository.save(byNumber.get());
        }
        if (existedOrder.isPresent()) {
            OrderEntity order = existedOrder.get();
            order.setPrice(update.getPrice());
            order.setNumberRoom(update.getNumberRoom());
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
                order.getNumberRoom(),
                order.getStatusOrder(),
                order.getSolution(),
                order.getUser().getName(),
                order.getUser().getSurname());
    }
}
