package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BarberShop {
    public static void main(String[] args) {
        int PORT_NUMBER = 9091;
        System.out.println("Server is running...");
        int clientID = 0;


        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

            while (true) {
                Socket socket = serverSocket.accept();
                clientID++;
                System.out.println("Client" + clientID + " connected");
                Runnable run = new Barber(socket, clientID);
                Thread t = new Thread(run);
                t.start();
            }
        } catch (IOException IOE) {
            System.out.println("Failed to connect with Client");
        }
    }
}