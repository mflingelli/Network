package de.flingelli.training.network.udp;

import de.flingelli.training.network.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

public class UdpServer extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpServer.class);
    private final DatagramSocket server;
    private final byte[] buffer = new byte[256];
    private boolean running;

    public UdpServer() throws SocketException {
        server = new DatagramSocket(NetworkConstants.PORT);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                server.receive(packet);
                InetAddress address = packet.getAddress();
                packet = new DatagramPacket(buffer, buffer.length, address, packet.getPort());
                String received = new String(packet.getData(), 0, packet.getLength());
                if (received.equals(NetworkConstants.END_MESSAGE)) {
                    running = false;
                }
                server.send(packet);
            } catch (IOException ex) {
                LOGGER.error("Error while receiving UDP packets.", ex);
            }
        }
        close();
    }

    public void close() {
        if (!server.isClosed()) {
            server.close();
        }
    }
}
