package de.flingelli.training.network.udp;

import de.flingelli.training.network.NetworkConstants;

import java.io.IOException;
import java.net.*;

public class UdpClient {
    private final DatagramSocket server;
    private final InetAddress address;
    private byte[] buffer;

    public UdpClient() throws SocketException, UnknownHostException {
        server = new DatagramSocket();
        address = InetAddress.getByName(NetworkConstants.LOCALHOST);
    }

    public String sendMessage(String message) throws IOException {
        buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, NetworkConstants.PORT);
        server.send(packet);
        packet = new DatagramPacket(buffer, buffer.length);
        server.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        if (!server.isClosed()) {
            server.close();
        }
    }
}
