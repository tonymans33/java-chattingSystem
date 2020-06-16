package Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket clientSocket = new Socket(ip, 6000);

            System.out.println("Connecting to the server....");

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            String connectConfirm = in.readUTF();
            System.out.println("Server: " + connectConfirm);

            while (true) {

                String serverAsk = in.readUTF();
                System.out.println("Server: " + serverAsk);
                String request = scanner.nextLine();
                out.writeUTF(request);

                if (request.equals("close")) {
                    System.out.println("Closing connection to server....");
                    clientSocket.close();
                    System.out.println("connection is closed.");
                    break;
                }

                String reply = in.readUTF();
                System.out.println("Server: " + reply);
            }

            scanner.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Connection is closed...");
        }

    }
}

