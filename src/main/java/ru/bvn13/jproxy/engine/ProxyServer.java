package ru.bvn13.jproxy.engine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by bvn13 on 20.10.2018.
 */
public class ProxyServer {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    List<ClientListener> clients = Collections.synchronizedList(new ArrayList<>());

    public ProxyServer(String localHost, int portListen, String extHost, int extPort) {

        executor.execute(() -> {
            try (ServerSocket server = new ServerSocket(portListen, 0, InetAddress.getByName(localHost))) {

                while (!server.isClosed()) {
                    Socket client = server.accept();
                    ClientListener clientListener = new ClientListener(client, extHost, extPort);
                    executor.execute(clientListener);
                    clients.add(clientListener);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        });

    }

    public void stop() {
        clients.forEach(ClientListener::stopIt);
        executor.shutdown();
    }


}
