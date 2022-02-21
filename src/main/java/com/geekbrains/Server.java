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
                                try {
                                    in.close();
                                    out.close();
                                    socket.close();
                                    serverSocket.close();
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

                try {
                    out.writeUTF(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
