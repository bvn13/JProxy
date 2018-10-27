package ru.bvn13.jproxy.entities.dtos;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Getter
@Setter
public class ProxyInstanceDTO {

    @JMap
    private long id;

    @JMap
    @NotNull
    private boolean enabled;

    @JMap
    @NotNull
    private String name;

    @JMap
    @NotNull
    private String localHost;

    @JMap
    @NotNull
    private int localPort;

    @JMap
    @NotNull
    private String remoteHost;

    @JMap
    @NotNull
    private int remotePort;

}
