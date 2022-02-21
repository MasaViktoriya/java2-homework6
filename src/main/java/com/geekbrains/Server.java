package com.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server online");
            Socket socket = serverSocket.accept();
            System.out.println("Client online");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String clientMessage = in.readUTF();
                            System.out.println("Client: " + clientMessage);
                            if (clientMessage.equalsIgnoreCase("quit")) {
                                System.out.println("Client said goodbye. Close connection");
                                try {
                                    in.close();
                                    out.close();
                                    socket.close();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            Scanner sc = new Scanner(System.in);
            System.out.println("Type your messages here");
            while (true) {

                String text = sc.nextLine();
                if (text.equalsIgnoreCase("quit")) {
                    System.out.println("Server wants to quit. Close connection and shut down");
                    try {
                        out.writeUTF("quit");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    break;
                } else {
                    try {
                        out.writeUTF(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
