package ru.bvn13.jproxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bvn13 on 20.10.2018.
 */
public class ProxyServer {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public ProxyServer(int portListen, String extHost, int extPort) {

        executor.execute(() -> {
            try (ServerSocket server = new ServerSocket(portListen)) {

                while (!server.isClosed()) {

                    Socket client = server.accept();

                    executor.execute(new ClientListener(client, extHost, extPort));

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        });

    }

}
