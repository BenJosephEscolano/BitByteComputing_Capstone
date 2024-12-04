package Network;

import java.io.*;
import java.net.*;

public class ChatClient {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter server IP address: ");
            String serverIp = consoleInput.readLine();

            if (!serverIp.equals("172.31.8.170")) {
                System.out.println("Incorrect server IP. Please restart and enter the correct IP.");
                return;
            }

            // Connect to the server
            try (Socket socket = new Socket(serverIp, SERVER_PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Prompt user for their name
                System.out.print("Enter your name: ");
                String username = consoleInput.readLine();
                System.out.println("Welcome to the chat, " + username + "!");

                // Thread to listen for messages from the server
                new Thread(() -> {
                    String serverMessage;
                    try {
                        while ((serverMessage = in.readLine()) != null) {
                            System.out.println(serverMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Main thread to send messages to the server
                String userInput;
                System.out.println("Start chatting below:");
                while ((userInput = consoleInput.readLine()) != null) {
                    out.println(username + ": " + userInput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

