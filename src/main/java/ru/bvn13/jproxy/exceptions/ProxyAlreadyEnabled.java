package ru.bvn13.jproxy.exceptions;

/**
 * Created by bvn13 on 27.10.2018.
 */
public class ProxyAlreadyEnabled extends RuntimeException {

    private long id;
    private String name;

    public ProxyAlreadyEnabled(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Proxy already enabled: %d, %s", id, name);
    }

}
