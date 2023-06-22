package by.pleshkov.web.controller;

import by.pleshkov.database.dto.LoginDto;
import by.pleshkov.service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import static by.pleshkov.web.util.PagesUtil.LOGIN;

@Controller
@RequestMapping(LOGIN)
@SessionAttributes({"user"})
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(LoginDto login, Model model) {
        return userService.getBy(login)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "redirect:/rooms";
                })
                .orElse("redirect:/login?error=true");
    }
}
