package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class Recieve extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Recieve(Socket socket, DataInputStream in, DataOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(in.readUTF());
            } catch (java.io.EOFException e) {
                System.out.println("Connection has been lost");
                System.exit(3);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

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
            Recieve recieve = new Recieve(socket, in, out);
            recieve.start();


            int time = reader.nextInt();
            out.writeInt(time);
            reader.nextLine();
            String clientName = reader.nextLine();
            out.writeUTF(clientName);
//            while(true) {
//                System.out.println(in.readUTF());
//            }
//            String clientName = reader.nextLine();
//            out.writeUTF(clientName);


        } catch (IOException IOE) {
            System.out.println("Failed to connect to Server.");
        }
    }

}
