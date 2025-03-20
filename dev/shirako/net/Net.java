package dev.shirako.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Net {
    public static void host() {
        int port = 25565;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server Reain server on " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (Exception ex) {
            System.out.println("Error starting server: " + ex.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = input.readLine()) != null) {
                System.out.println("Received: " + message);
                output.println("Echo: " + message); // Echo message back to client
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (Exception ex) {
                System.out.println("Error closing client socket: " + ex.getMessage());
            }
        }
    }
}