package by.pleshkov.web.controller;


import by.pleshkov.database.dto.RegistrationDto;
import by.pleshkov.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.pleshkov.web.util.PagesUtil.USERS;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping(path = "/{id}")
    public String getUserPage(Model model, @PathVariable Long id) {
        return userService.getById(id).
                map(user -> {
                    model.addAttribute("user", user);
                    return "user";
                })
                .orElse("redirect:/users");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/update/{id}")
    public String editUserPage(Model model, @PathVariable Long id) {
        return userService.getById(id).
                map(user -> {
                    model.addAttribute("user", user);
                    return "userEdit";
                })
                .orElse("redirect:/users");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/update/{id}")
    public String updateUser(@PathVariable Long id, RegistrationDto user) {
        return userService.update(id, user).map(
                        updateUser -> "redirect:/users/" + id
                )
                .orElse("redirect:/users/update/{id}?error=true");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
