package de.flingelli.training.network.tcp;

import de.flingelli.training.network.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class TcpServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServerTest.class);
    private TcpServer server;
    private TcpClient client;

    @BeforeMethod
    public void setup() throws IOException {
        server = new TcpServer();
        Thread  thread = new Thread(() -> {
            try {
                server.start(NetworkConstants.PORT);
            } catch (IOException ex) {
                LOGGER.error("TCP server couldn't be started.", ex);
            }
        });
        thread.start();
        client = new TcpClient();
        LOGGER.info("Connect to TCP server.");
        client.startConnection(NetworkConstants.LOCALHOST, NetworkConstants.PORT);
    }

    @Test
    public void sendHelloMessage() throws IOException {
        String response = client.sendMessage(NetworkConstants.HELLO_MESSAGE);
        Assert.assertTrue(response.startsWith(NetworkConstants.ACK_PREFIX));
        Assert.assertTrue(response.endsWith(NetworkConstants.HELLO_MESSAGE));
    }

    @Test
    public void sendEndMessage() throws IOException {
        String response = client.sendMessage(NetworkConstants.END_MESSAGE);
        Assert.assertEquals(response, NetworkConstants.BYE_MESSAGE);
    }

    @AfterMethod
    public void tearDown() throws IOException {
        LOGGER.info("Tear down TCP server.");
        client.stopConnection();
        server.stop();
    }
}
