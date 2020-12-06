package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static int getTime() {
        Scanner reader = new Scanner(System.in);
        int time = reader.nextInt();
        while (true) {
            if (time > 17 || time < 10) {
                System.out.println("You typed wrong time. Try again.");
                time = reader.nextInt();
            } else {
                break;
            }
        }
        return time;
    }

    public static void main(String[] args) {
        String SERVER_IP = "127.0.0.1";
        int PORT_NUMBER = 9091;

        try {
            Socket socket = new Socket(SERVER_IP, PORT_NUMBER);

            System.out.println("Connected to the Server.");
            DataInputStream in = new DataInputStream(socket.getInputStream());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner reader = new Scanner(System.in);
            Receive receive = new Receive(in);
            receive.start();

            System.out.print("Give name you want to use: ");

            String msg = reader.nextLine();
            out.writeUTF(msg);

            while (true) {
                String choice = reader.nextLine();
                switch (choice) {
                    case "r", "c" -> {
                        out.writeUTF(choice);
                        out.writeInt(getTime());
                    }
                    case "e" -> {
                        out.writeUTF(choice);
                        System.out.println("Thank you for thrusting in out cutting skills, bye!");
                    }
                    default -> System.out.println("Your choice is wrong! Try Again.");
                }
                if (choice.equals("e")) break;
            }
            receive.interrupt();
            in.close();
            out.close();
            //socket.close();
        } catch (IOException IOE) {
            System.out.println("Failed to connect to Server.");
        } finally {
            System.out.println("Disconnected.");

        }
    }

}
