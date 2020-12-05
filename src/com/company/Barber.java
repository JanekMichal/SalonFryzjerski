package com.company;

import java.io.*;
import java.net.Socket;

public class Barber implements Runnable {
    private final Socket socket;
    private final int clientID;
    DataOutputStream out;
    String[] clientsList = new String[8];

    Barber(Socket socket, int id) throws IOException {
        this.socket = socket;
        clientID = id;
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    private synchronized void showAvailableHours() throws IOException {
        out.writeUTF("Choose hour you want to visit Barber: ");
        clientsList[1] = "Maciek Ważniak";
        for (int i = 10; i < 18; i++) {
            if (clientsList[i - 10] == null) {
                out.writeUTF(i + ":00 - Free");
            } else {
                out.writeUTF(i + ":00 - Occupied");
            }
        }
    }
    private void change(String name, int time) throws IOException {
        clientsList[time - 10] = name;
        showAvailableHours();
    }
    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());

            showAvailableHours();
            int time = in.readInt();


            out.writeUTF("What is your name?");
            String clientName = in.readUTF();
            change(clientName, time);
            out.writeUTF("OK, your visit will be at " + time + ". See you soon " + clientName);

            in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            System.out.println("Client" + clientID + " disconnected");
        }
    }
}