package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String SERVER_IP = "127.0.0.1";
        int PORT_NUMBER = 9091;

        try {
            Socket socket = new Socket(SERVER_IP, PORT_NUMBER);

            System.out.println("Connected to the Server.");
            DataInputStream in = new DataInputStream(socket.getInputStream());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner reader = new Scanner(System.in);
            Receive receive = new Receive(socket, in, out);
            receive.start();

            while (true) {
                String choice = reader.nextLine();
                if (choice.equals("r") || choice.equals("c") || choice.equals("e")) {
                    out.writeUTF(choice);
                    try {
                        int time = reader.nextInt();
                        out.writeInt(time);
                    } catch (InputMismatchException IME) {
                        System.out.println("You typed wrong time. Try again.");
                    }
                } else {
                    System.out.println("Your choice is wrong! Try Again.");
                }


                reader.nextLine();
                String clientName = reader.nextLine();
                out.writeUTF(clientName);
            }
        } catch (IOException IOE) {
            System.out.println("Failed to connect to Server.");
        }
    }
}
