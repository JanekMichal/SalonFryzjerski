package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Barber implements Runnable {
    private final Socket socket;
    private final int clientID;
    DataOutputStream out;
    DataInputStream in;

    Barber(Socket socket, int id) throws IOException {
        this.socket = socket;
        clientID = id;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void showAvailableHours() throws IOException {
        out.writeUTF("Current available hours:");
        for (int i = 10; i < 18; i++) {
            if (BarberShop.clientsList[i - 10] == null) {
                out.writeUTF(i + ":00 - Free");
            } else {
                out.writeUTF(i + ":00 - Occupied");
            }
        }
    }

    private void showCurrentClients() {
        System.out.println("Current clients list with hours: ");
        for (int i = 10; i < 18; i++) {
            if (BarberShop.clientsList[i - 10] == null) {
                System.out.println(i + ":00 - ");
            } else {
                System.out.println(i + ":00 - " + BarberShop.clientsList[i - 10]);
            }
        }
    }

    private void reservation() throws IOException {
        out.writeUTF("Choose hour you want to visit Barber: ");
        int time = in.readInt();
        out.writeUTF("What is your name?");
        String name = in.readUTF();
        out.writeUTF("OK, your visit will be at " + time + ". See you soon " + name + "!");

        if (checkReservation(name, time)) {
            BarberShop.clientsList[time - 10] = name;
            BarberShop.showUpdatedReservations();
            showCurrentClients();
        }
    }

    public boolean checkReservation(String name, int time) throws IOException {
        if (BarberShop.clientsList[time - 10] != null) {
            out.writeUTF("This hour is already reserved.");
            return false;
        } else {
            return true;
        }
    }

    public void cancelReservation() throws IOException {
        out.writeUTF("Choose visit you want to cancel:");
        int time = in.readInt();
        if (BarberShop.clientsList[time - 10] != null) {
            BarberShop.clientsList[time - 10] = null;
            out.writeUTF("You have successfully cancelled your reservation.");
        }
        showCurrentClients();
        BarberShop.showUpdatedReservations();
    }

    @Override
    public void run() {
        try {

            showAvailableHours();
            while (true) {
                out.writeUTF("Tell me, what do you want to do, (c) cancel, (e) exit, (r) reserve?");

                String choice = in.readUTF();

                if (choice.equals("r")) {
                    reservation();
                } else if (choice.equals("c")) {
                    cancelReservation();
                } else if (choice.equals("e")) {
                    break;
                } else {
                    out.writeUTF("You typed something I cannot understand.");
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client" + clientID + " disconnected");
        }
    }
}