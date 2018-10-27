package ru.bvn13.jproxy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bvn13.jproxy.entities.dtos.ProxyInstanceDTO;
import ru.bvn13.jproxy.services.ProxyInstanceService;
import ru.bvn13.jproxy.services.ProxyManagerService;

import java.util.Optional;

/**
 * Created by bvn13 on 25.10.2018.
 */
@Controller
@RequestMapping("/proxy/{proxyId:[\\d]+}")
public class ProxyController {

    @Autowired
    private ProxyInstanceService proxyInstanceService;

    @Autowired
    private ProxyManagerService proxyManagerService;

    @GetMapping
    public String get(@PathVariable("proxyId") long proxyId, Model model) {
        Optional<ProxyInstanceDTO> proxyInstanceDTO = proxyInstanceService.findById(proxyId);
        model.addAttribute("proxyInstance", proxyInstanceDTO.orElse(new ProxyInstanceDTO()));
        return "proxyForm";
    }

    @PostMapping
    public String save(@PathVariable("proxyId") long proxyId, ProxyInstanceDTO proxyInstanceDTO) {
        if (proxyId == 0) {
            proxyInstanceService.create(proxyInstanceDTO);
        } else {
            proxyInstanceService.save(proxyInstanceDTO);
        }

        proxyManagerService.updateProxies();

        return "redirect:/proxy";
    }

    @GetMapping(value = "/toggle")
    public String toggle(@PathVariable("proxyId") long proxyId) {
        proxyInstanceService.toggleEnable(proxyId);
        proxyManagerService.updateProxies();
        return "redirect:/proxy";
    }

}
