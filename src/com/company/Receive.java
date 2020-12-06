package com.company;

import java.io.DataInputStream;

public class Receive extends Thread {
    private final DataInputStream in;

    public Receive(DataInputStream in) {
        this.in = in;
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
