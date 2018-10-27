package ru.bvn13.jproxy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bvn13.jproxy.entities.dtos.ProxyInstanceDTO;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Controller
@RequestMapping("/proxy")
public class ProxyListController {

    @GetMapping
    public String get(Model model) {
        model.addAttribute("proxyInstance", new ProxyInstanceDTO());
        return "proxylist";
    }

}
