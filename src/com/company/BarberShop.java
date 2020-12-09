package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BarberShop {
    public static String[] clientsList = new String[8];
    public static List<Barber> clients = new ArrayList<>();

    public static synchronized void showUpdatedReservations() throws IOException {
        for (Barber barber : clients) {
            barber.showAvailableHours();
        }
    }

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
                Barber barber = new Barber(socket, clientID);
                clients.add(barber);
                Thread t = new Thread(barber);
                t.start();
            }
        } catch (IOException IOE) {
            System.out.println("Failed to connect with Client");
        }
    }
}