package ru.bvn13;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import ru.bvn13.jproxy.engine.ProxyServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit test for simple JProxy.
 */
public class JProxyTest
{

    private ProxyServer proxy;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private String request = "GET / HTTP/1.1\n" +
            "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\n" +
            "Host: yandex.ru\n" +
            "Accept-Language: en-us\n" +
            "Accept-Encoding: gzip, deflate\n" +
            "Connection: Keep-Alive\n"+
            "\n";

    //@Before
    public void before() {
    }

    //does not work
    //@Test
    public void testYandex() {

        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;

        byte[] buffer = new byte[4096];

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            socket = new Socket("localhost", 8099);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            out.write(request.getBytes());
            out.flush();

            int bytesRead = 0;
            do {
                bytesRead = in.read(buffer, 0, 4096);
                for (int i = 0; i < bytesRead; i++) {
                    System.out.print(buffer[i]);
                }
                System.out.println();
            } while (bytesRead > 0);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("", e);
        }


    }
}
