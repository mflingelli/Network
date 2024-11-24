package de.flingelli.training.network.udp;

import de.flingelli.training.network.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UdpServerTest.class);
    private UdpServer server;
    private UdpClient client;

    @BeforeClass
    public void setup() throws SocketException, UnknownHostException {
        server = new UdpServer();
        Thread thread = new Thread(() -> server.start());
        thread.start();
        LOGGER.info("Start UDP server.");
        client = new UdpClient();
    }

    @Test
    public void sendHelloMessage() throws IOException {
        String message = client.sendMessage(NetworkConstants.HELLO_MESSAGE);
        Assert.assertEquals(NetworkConstants.HELLO_MESSAGE, message);
    }

    @Test
    public void sendEndMessage() throws IOException {
        String message = client.sendMessage(NetworkConstants.END_MESSAGE);
        Assert.assertEquals(NetworkConstants.END_MESSAGE, message);
    }

    @AfterClass
    public void tearDown() throws IOException {
        LOGGER.info("Stop UDP server.");
        client.sendMessage(NetworkConstants.END_MESSAGE);
        client.close();
    }
}
