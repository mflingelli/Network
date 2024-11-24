package de.flingelli.training.network.tcp;

import de.flingelli.training.network.NetworkConstants;

import java.io.*;
import java.net.*;

public class TcpServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        boolean running = true;
        while ((inputLine = in.readLine()) != null && running) {
            if (NetworkConstants.END_MESSAGE.equals(inputLine)) {
                out.println(NetworkConstants.BYE_MESSAGE);
                running = false;
            } else {
                out.println(NetworkConstants.ACK_PREFIX + inputLine);
            }
            out.println(inputLine);
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
