package com.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 8080;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean isDataStreamOn = true;

    public Client (){
        try{

            openConnection();
            Scanner sc = new Scanner(System.in);
            System.out.println("Type your messages here");
            while (isDataStreamOn) {
                String text = sc.nextLine();
                if(text.equalsIgnoreCase("quit")){
                    System.out.println("Client wants to quit. Close connection and shut down");
                    closeConnection();
                    break;
                }
                else {
                    sendMessage(text);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openConnection() throws IOException{
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (isDataStreamOn){
                        String serverMessage = in.readUTF();
                        System.out.println("Server: " + serverMessage);
                        if(serverMessage.equalsIgnoreCase("quit")){
                            System.out.println("Server said goodbye. Close connection and shut down");
                            closeConnection();
                            break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void closeConnection(){
        try {
            out.writeUTF("quit");
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                isDataStreamOn = false;
                in.close();
                out.close();
                socket.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
   }

    public void sendMessage(String text){
        try {
            out.writeUTF(text);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}