package ru.bvn13.jproxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by bvn13 on 20.10.2018.
 */
public class ClientListener implements Runnable {

    private ExecutorService executor;

    private Socket internalSocket;
    private Socket externalSocket;

    private String extHost;
    private int extPort;

    private DataInputStream internalInStream;
    private DataInputStream externalInStream;
    private DataOutputStream internalOutStream;
    private DataOutputStream externalOutStream;

    AtomicBoolean isTerminated = new AtomicBoolean(false);

    private static class SenderThread implements Runnable {
        private Socket senderSocket;
        private Socket receiverSocket;
        private DataInputStream sender;
        private DataOutputStream receiver;
        private int bufferLength;
        private String name;
        private ClientListener owner;

        public SenderThread(ClientListener owner, String name, Socket senderSocket, Socket receiverSocket, DataInputStream sender, DataOutputStream receiver, int bufferLength) {
            this.owner = owner;
            this.name = name;
            this.senderSocket = senderSocket;
            this.receiverSocket = receiverSocket;
            this.sender = sender;
            this.receiver = receiver;
            this.bufferLength = bufferLength;
        }
        @Override
        public void run() {
            byte[] buffer = new byte[bufferLength];

            try {

                for (Thread.yield(); !senderSocket.isClosed() && !receiverSocket.isClosed(); Thread.yield()) {
                    System.out.println(String.format("%s waits for data...", name));
                    int bytesRead = sender.read(buffer, 0, bufferLength);
                    if (bytesRead < 0) {
                        System.out.println(String.format("%s - transmission terminated", name));
                        owner.isTerminated.set(true);
                        return;
                    }
                    System.out.println(String.format("%s read %d bytes", name, bytesRead));
                    receiver.write(buffer, 0, bytesRead);
                    receiver.flush();
                    System.out.println(String.format("%s sent %d bytes", name, bytesRead));
                }

            } catch (IOException e) {
                e.printStackTrace();
                owner.isTerminated.set(true);
            } catch (Throwable t) {
                t.printStackTrace();
                owner.isTerminated.set(true);
            }
        }
    }

    public ClientListener(Socket socket, String extHost, int extPort) {
        Objects.requireNonNull(socket, "socket required");
        Objects.requireNonNull(extHost, "host required");
        this.internalSocket = socket;
        this.extHost = extHost;
        this.extPort = extPort;
        this.executor = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {

        System.out.println("STARTING");

        try {
            this.externalSocket = new Socket(extHost, extPort);
            this.internalInStream = new DataInputStream(this.internalSocket.getInputStream());
            this.internalOutStream = new DataOutputStream(this.internalSocket.getOutputStream());
            this.externalInStream = new DataInputStream(this.externalSocket.getInputStream());
            this.externalOutStream = new DataOutputStream(this.externalSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        startInternalToExternalSender();
        startExternalToInternalSender();

        for (Thread.yield(); !internalSocket.isClosed() && !externalSocket.isClosed() && !isTerminated.get(); Thread.yield()) {
            Thread.yield();
        }

        System.out.println("STOPPING");

        executor.shutdown();

        System.out.println("STOPPED");

    }

    private void startInternalToExternalSender() {
        executor.execute(new SenderThread(this,"SENDER", internalSocket, externalSocket, internalInStream, externalOutStream, 4096));
    }

    private void startExternalToInternalSender() {
        executor.execute(new SenderThread(this,"RECEIVER", externalSocket, internalSocket, externalInStream, internalOutStream, 4096));
    }
}
