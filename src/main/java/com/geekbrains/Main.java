package com.geekbrains;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        System.out.println("Type your messages here");
        Scanner sc = new Scanner(System.in);
        while (true) {
            String text = sc.nextLine();
            client.sendMessage(text);
        }
    }
}
