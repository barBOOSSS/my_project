package by.pleshkov.web.controller;

import by.pleshkov.web.util.PagesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.pleshkov.web.util.PagesUtil.MAIN;

@Controller
@RequestMapping(MAIN)
@RequiredArgsConstructor
public class MainController {

    @RequestMapping
    public String getMainPage(){
//        String id = req.getParameter("id");
//        if (id == null) {
//            req.getRequestDispatcher(PagesUtil.MAIN).forward(req, resp);
//        } else {
//            menu(id, req, resp);
//        }
        return "main";
    }
}
