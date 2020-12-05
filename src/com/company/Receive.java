package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Receive extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Receive(Socket socket, DataInputStream in, DataOutputStream out) {
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
