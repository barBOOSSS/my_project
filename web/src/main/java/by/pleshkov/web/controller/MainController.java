package by.pleshkov.web.controller;


import by.pleshkov.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.pleshkov.web.util.PagesUtil.MAIN;

@Controller
@RequestMapping(MAIN)
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping
    public String getMainPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", userService.getByEmail(user.getUsername()));
        return "main";
    }

}

