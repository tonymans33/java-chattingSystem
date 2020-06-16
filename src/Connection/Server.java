package Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(6000);
            System.out.println("Server is booted up and is waiting for clients to connect...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client (" + clientSocket + ") is connected to the server.");
                Thread client = new ClientConnection(clientSocket);
                client.start();
            }
        } catch (IOException e) {
            System.out.println("Problem in Connection to Server...");
        }
    }
    static class ClientConnection extends Thread {
        final private Socket clientSocket;
        public ClientConnection(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        public void run() {
            String Ans = "";
            try {

                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                output.writeUTF("connected.");
                while (true) {
                    output.writeUTF("Enter a name to know the grade or 'close' to close the connection.");

                    String request = input.readUTF();
                    System.out.println("Client [ " + clientSocket + " ]: " + request);

                    String n[]= {"tony","bavly","youssef","hussien","ahmed","mohamed"};
                    String g[]= {"50","40","30","20","10","22"};
                    for(int i=0;i<6;i++)
                    {
                        if(request.equalsIgnoreCase(n[i]))
                        {
                            Ans =g[i];
                            break;
                        }
                        else {
                            Ans = "not founded";

                        }
                    }

                    if (request.equals("close")) {
                        System.out.println("Closing connection with this client....");
                        System.out.println("Connection with this client [" + clientSocket + "] is closed.");
                        clientSocket.close();
                        break;
                    }
                    output.writeUTF("Your grade is : "+ Ans);

                }

                input.close();
                output.close();
            } catch (IOException e) {
                System.out.println("Connection with this client [" + clientSocket + "] is terminated.");
            }
        }
    }

}

