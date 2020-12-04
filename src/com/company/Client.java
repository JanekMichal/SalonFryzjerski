package com.company;

import java.io.*;
import java.net.Socket;
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

            while(true) {
                System.out.println(in.readUTF());
            }
//            String clientName = reader.nextLine();
//            out.writeUTF(clientName);



        } catch (IOException IOE) {
            System.out.println("Failed to connect to Server.");
        }
    }

}
