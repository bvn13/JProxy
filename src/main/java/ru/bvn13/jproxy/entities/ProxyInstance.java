package ru.bvn13.jproxy.entities;

import com.googlecode.jmapper.annotations.JMap;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Getter
@Setter
@Entity
public class ProxyInstance {

    //@JMap
    @Id
    @GeneratedValue
    private long id;

    @JMap
    @Column(nullable = false)
    @NotNull
    @ColumnDefault(value = "false")
    private boolean enabled;

    @JMap
    @Column(nullable = false)
    @NotNull
    private String name;

    @JMap
    @Column(nullable = false)
    @NotNull
    @ColumnDefault(value = "")
    private String localHost;

    @JMap
    @Column(nullable = false)
    @NotNull
    @ColumnDefault(value = "0")
    private int localPort;

    @JMap
    @Column(nullable = false)
    @NotNull
    @ColumnDefault(value = "")
    private String remoteHost;

    @JMap
    @Column(nullable = false)
    @NotNull
    @ColumnDefault(value = "0")
    private int remotePort;

}
