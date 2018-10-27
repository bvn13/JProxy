package ru.bvn13.jproxy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bvn13.jproxy.engine.ProxyServer;
import ru.bvn13.jproxy.entities.dtos.ProxyInstanceDTO;
import ru.bvn13.jproxy.exceptions.ProxyAlreadyEnabled;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bvn13 on 23.10.2018.
 */
@Service
public class ProxyManagerService {

    @Autowired
    private ProxyInstanceService proxyInstanceService;

    private Map<Long, ProxyInstanceDTO> proxySettings = new HashMap<>();
    private Map<Long, ProxyServer> proxies = new HashMap<>();

    public boolean isRunnig(long id) {
        synchronized (this) {
            if (proxySettings.containsKey(id)) {
                return proxySettings.get(id).isEnabled() && proxies.containsKey(id);
            } else {
                return false;
            }
        }
    }

    @PostConstruct
    public void init() {
        updateProxies();
    }

    public void updateProxies() {

        Map<Long, ProxyInstanceDTO> proxiesForStart = new HashMap<>();
        List<Long> proxiesForStop = new ArrayList<>();
        Map<Long, ProxyInstanceDTO> proxiesForUpdate = new HashMap<>();

        synchronized (this) {
            List<ProxyInstanceDTO> actualProxyList = proxyInstanceService.findAllDTOs();
            actualProxyList.forEach(ap -> {
                // case it is a new proxy
                if (!proxySettings.containsKey(ap.getId())) {
                    if (ap.isEnabled()) {
                        proxiesForStart.put(ap.getId(), ap);
                    }
                } else {
                    ProxyInstanceDTO currentProxy = proxySettings.get(ap.getId());
                    if (currentProxy.isEnabled() && !ap.isEnabled()) {
                        proxiesForStop.add(ap.getId());
                    } else if (currentProxy.isEnabled() != ap.isEnabled()
                            || !currentProxy.getLocalHost().equals(ap.getLocalHost())
                            || currentProxy.getLocalPort() != ap.getLocalPort()
                            || !currentProxy.getRemoteHost().equals(ap.getRemoteHost())
                            || currentProxy.getRemotePort() != ap.getRemotePort()
                    ) {
                        proxiesForUpdate.put(currentProxy.getId(), ap);
                    }
                }
            });

            proxiesForStart.forEach(this::startProxy);
            proxiesForStop.forEach(this::stopProxy);
            proxiesForUpdate.forEach(this::updateProxy);
        }

    }

    private void startProxy(long id, ProxyInstanceDTO proxyInstanceDTO) {
        if (proxySettings.containsKey(id)) {
            throw new ProxyAlreadyEnabled(id, proxyInstanceDTO.getName());
        }
        ProxyServer proxyServer = new ProxyServer(proxyInstanceDTO.getLocalHost(), proxyInstanceDTO.getLocalPort(), proxyInstanceDTO.getRemoteHost(), proxyInstanceDTO.getRemotePort());
        proxySettings.put(id, proxyInstanceDTO);
        proxies.put(id, proxyServer);
    }

    private void stopProxy(long id) {
        if (proxySettings.containsKey(id)) {
            proxies.get(id).stop();
            proxies.remove(id);
            proxySettings.remove(id);
        }
    }

    private void updateProxy(long id, ProxyInstanceDTO proxyInstanceDTO) {
        if (proxySettings.containsKey(id)) {
            proxies.get(id).stop();
            proxies.remove(id);
            proxySettings.remove(id);
        }
        startProxy(id, proxyInstanceDTO);
    }

}
