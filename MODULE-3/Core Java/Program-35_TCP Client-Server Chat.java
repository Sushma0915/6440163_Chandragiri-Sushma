import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // For reading from console to send to client
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String clientMsg, serverMsg;

            // Simple loop for two-way communication
            while (true) {
                // Read message from client
                if ((clientMsg = in.readLine()) != null) {
                    System.out.println("Client: " + clientMsg);
                    if (clientMsg.equalsIgnoreCase("bye")) break;
                }

                // Read message from server user
                System.out.print("Server: ");
                serverMsg = consoleReader.readLine();
                out.write(serverMsg);
                out.newLine();
                out.flush();

                if (serverMsg.equalsIgnoreCase("bye")) break;
            }

            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // For reading from console to send to server
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String serverMsg, clientMsg;

            // Simple loop for two-way communication
            while (true) {
                // Read message from server
                if ((serverMsg = in.readLine()) != null) {
                    System.out.println("Server: " + serverMsg);
                    if (serverMsg.equalsIgnoreCase("bye")) break;
                }

                // Read message from client user
                System.out.print("Client: ");
                clientMsg = consoleReader.readLine();
                out.write(clientMsg);
                out.newLine();
                out.flush();

                if (clientMsg.equalsIgnoreCase("bye")) break;
            }

            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}