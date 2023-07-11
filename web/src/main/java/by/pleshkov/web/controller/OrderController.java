package by.pleshkov.web.controller;


import by.pleshkov.database.dto.OrderCreationDto;
import by.pleshkov.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.pleshkov.web.util.PagesUtil.ORDERS;

@Controller
@RequestMapping(ORDERS)
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String getOrdersPage(Model model) {
        model.addAttribute("orders", orderService.getAll());
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

    @GetMapping(path = "/create")
    public String createOrderPage() {
        return "orderCreated";
    }

    @PostMapping(path = "/create")
    public String createOrder(OrderCreationDto order) {
        return "redirect:/orders/" + orderService.create(order);
    }

    @GetMapping(path = "/update/{id}")
    public String editOrderPage(Model model, @PathVariable Long id) {
        return orderService.getById(id).
                map(order -> {
                    model.addAttribute("order", order);
                    return "orderEdit";
                })
                .orElse("redirect:/orders");
    }

    @PostMapping(path = "/update/{id}")
    public String updateOrder(@PathVariable Long id, OrderCreationDto order) {
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
