package ru.bvn13.jproxy.beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import ru.bvn13.jproxy.entities.dtos.ProxyInstanceDTO;
import ru.bvn13.jproxy.services.ProxyInstanceService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Component
@RequestScope
public class ProxyInstanceList {

    @Autowired
    private ProxyInstanceService proxyInstanceService;

    @Getter
    @Setter
    private List<ProxyInstanceDTO> proxyInstances;
//    public Set<ProxyInstanceDTO> getProxyInstances() {
//        return proxyInstanceService.findAllDTOs();
//    }

    @PostConstruct
    public void init() {
        proxyInstances = proxyInstanceService.findAllDTOs();
    }

}
