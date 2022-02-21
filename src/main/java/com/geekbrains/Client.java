package com.geekbrains;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 8080;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public Client (){
        try{
            openConnection();
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
                    while (true){
                        String serverMessage = in.readUTF();

                        if(serverMessage.equalsIgnoreCase("quit")){
                            closeConnection();
                            break;
                        }
                        System.out.println("Server: " + serverMessage);
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