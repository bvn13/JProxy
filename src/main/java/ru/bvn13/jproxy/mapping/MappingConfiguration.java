package ru.bvn13.jproxy.mapping;

import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bvn13.jproxy.entities.ProxyInstance;
import ru.bvn13.jproxy.entities.dtos.ProxyInstanceDTO;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Configuration
public class MappingConfiguration {

    @Bean
    @Qualifier("proxyInstanceToProxyInstanceDtoMapper")
    public JMapper<ProxyInstanceDTO, ProxyInstance> proxyInstanceToProxyInstanceDtoMapper() {
        return new JMapper<>(ProxyInstanceDTO.class, ProxyInstance.class);
    }

    @Bean
    @Qualifier("proxyInstanceDtoToProxyInstanceMapper")
    public JMapper<ProxyInstance, ProxyInstanceDTO> proxyInstanceDtoToProxyInstanceMapper() {
        return new JMapper<>(ProxyInstance.class, ProxyInstanceDTO.class);
    }

}
