package ru.bvn13.jproxy.services;

import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.bvn13.jproxy.entities.ProxyInstance;
import ru.bvn13.jproxy.entities.dtos.ProxyInstanceDTO;
import ru.bvn13.jproxy.repositories.ProxyInstanceRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Service
public class ProxyInstanceService {

    @Autowired
    private ProxyInstanceRepository proxyInstanceRepository;

    @Autowired
    @Qualifier("proxyInstanceToProxyInstanceDtoMapper")
    private JMapper<ProxyInstanceDTO, ProxyInstance> proxyInstanceToProxyInstanceDtoMapper;

    @Autowired
    @Qualifier("proxyInstanceDtoToProxyInstanceMapper")
    private JMapper<ProxyInstance, ProxyInstanceDTO> proxyInstanceDtoToProxyInstanceMapper;

    public List<ProxyInstanceDTO> findAllDTOs() {
        return proxyInstanceRepository.findAll().stream()
                .map(p -> proxyInstanceToProxyInstanceDtoMapper.getDestination(p))
                .sorted(Comparator.comparing(ProxyInstanceDTO::getName))
                .collect(Collectors.toList());

    }

    public Optional<ProxyInstanceDTO> findById(long id) {
        return proxyInstanceRepository.findById(id)
                .flatMap(p -> Optional.of(proxyInstanceToProxyInstanceDtoMapper.getDestination(p)));
    }

    @Transactional
    public void save(ProxyInstanceDTO proxyInstanceDTO) {

        Optional<ProxyInstance> proxy = proxyInstanceRepository.getById(proxyInstanceDTO.getId());
        if (proxy.isPresent()) {
            ProxyInstance proxyInstance = proxy.get();
            proxyInstanceDtoToProxyInstanceMapper.getDestination(proxyInstance, proxyInstanceDTO);
            proxyInstanceRepository.save(proxyInstance);
        } else {
            throw new EntityNotFoundException(String.format("Proxy not found by ID: %d", proxyInstanceDTO.getId()));
        }

    }

    @Transactional
    public void create(ProxyInstanceDTO proxyInstanceDTO) {

        ProxyInstance proxyInstance = proxyInstanceDtoToProxyInstanceMapper.getDestination(proxyInstanceDTO);
        proxyInstanceRepository.save(proxyInstance);

    }

    @Transactional
    public void toggleEnable(long id) {
        Optional<ProxyInstance> proxy = proxyInstanceRepository.getById(id);
        if (proxy.isPresent()) {
            ProxyInstance proxyInstance = proxy.get();
            proxyInstance.setEnabled(!proxyInstance.isEnabled());
            proxyInstanceRepository.save(proxyInstance);
        } else {
            throw new EntityNotFoundException(String.format("Proxy not found by ID: %d", id));
        }
    }

}
