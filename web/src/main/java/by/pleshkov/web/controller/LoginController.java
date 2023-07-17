package by.pleshkov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.pleshkov.web.util.PagesUtil.LOGIN;

@Controller
@RequestMapping(LOGIN)
@RequiredArgsConstructor
public class LoginController {

    @GetMapping
    public String getLoginPage() {
        return "login";
    }
}
