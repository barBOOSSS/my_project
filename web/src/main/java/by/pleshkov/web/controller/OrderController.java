package by.pleshkov.web.controller;


import by.pleshkov.database.constant.Role;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.dto.OrderCreationDto;
import by.pleshkov.database.dto.RoomCreationDto;
import by.pleshkov.database.dto.RoomReadDto;
import by.pleshkov.database.dto.UserReadDto;
import by.pleshkov.service.service.OrderService;
import by.pleshkov.service.service.RoomService;
import by.pleshkov.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static by.pleshkov.database.constant.Role.USER;
import static by.pleshkov.web.util.PagesUtil.ORDERS;

@Controller
@RequestMapping(ORDERS)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final RoomService roomService;

    @GetMapping
    public String getOrdersPage(Model model, @AuthenticationPrincipal User user) {
        Optional<UserReadDto> userReadDto = userService.getByEmail(user.getUsername());
        if (userReadDto.get().getRole().equals(USER)) {
            model.addAttribute("orders", orderService.getAllByUSer(userReadDto.get().getId()));
        } else {
            model.addAttribute("orders", orderService.getAll());
        }
        return "orders";
    }

    @GetMapping(path = "/{id}")
    public String getOrderPage(Model model, @PathVariable Long id) {
        return orderService.getById(id).
                map(order -> {
                    model.addAttribute("order", order);
                    return "order";
                })
                .orElse("redirect:/orders");
    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
//    @GetMapping(path = "/create")
//    public String createOrderPage() {
//        return "orderCreated";
//    }

//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping(path = "/create")
    public String createOrder(OrderCreationDto order, RoomCreationDto room) {
        Optional<RoomReadDto> byNumber = roomService.getByNumber(room.getNumber());
        roomService.update(byNumber.get().getId(), room);
        return "redirect:/orders/" + orderService.create(order);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping(path = "/update/{id}")
    public String editOrderPage(Model model, @PathVariable Long id) {
        return orderService.getById(id).
                map(order -> {
                    model.addAttribute("order", order);
                    return "orderEdit";
                })
                .orElse("redirect:/orders");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping(path = "/update/{id}")
    public String updateOrder(@PathVariable Long id, OrderCreationDto order) {
//        Optional<RoomReadDto> byNumber = roomService.getByNumber(order.getNumberRoom());
//        roomService.update(byNumber.get().getId(), room);
        return orderService.update(id, order).map(
                        updateOrder -> "redirect:/orders/" + id
                )
                .orElse("redirect:/orders/update/{id}?error=true");
    }

    @PostMapping(path = "/{id}/delete")
    public String deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
