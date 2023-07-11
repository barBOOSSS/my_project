package by.pleshkov.web.controller;


import by.pleshkov.database.dto.RegistrationDto;
import by.pleshkov.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.pleshkov.web.util.PagesUtil.REGISTRATION;

@Controller
@RequestMapping(REGISTRATION)
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping
    public String register(RegistrationDto registrationDto) {
        return userService.createUser(registrationDto)
                .map(user -> "redirect:/login")
                .orElse("redirect:/registration?error");
    }
}
