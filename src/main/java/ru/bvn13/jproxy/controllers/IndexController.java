package ru.bvn13.jproxy.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by bvn13 on 23.10.2018.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("user: "+ auth.getName());
        System.out.println("roles: "+ auth.getAuthorities());

        model.addAttribute("username", auth.getName());

        return "index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

}
