package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket mySocket = new Socket("localhost", 3000);
        System.out.println("Il client si è collegato al server");
        BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire il proprio username");
        String username = scanner.nextLine();
        out.writeBytes(username + "\n");
        System.out.println(in.readLine());
        System.out.println("Inserire una nota o digitare LISTA per visualizzare la lista delle note salvate");
        System.out.println("Digitare RIMUOVI se si vuole eliminare una nota");
        System.out.println("Digitare ESCI per uscire");
        String str;
        do {
            str = scanner.nextLine();
            if (str.equals("LISTA")) {
                out.writeBytes("?" + "\n");
                String nota = "";
                System.out.println("Ecco la tua lista:");
                do {
                    nota = in.readLine();
                    if (nota.equals("@") == false) {
                        System.out.println(nota);
                    }
                } while (nota.equals("@") == false);
                System.out.println("Adesso puoi continuare ad inserire altre note");
            } else if (str.equals("ESCI")) {
                out.writeBytes("!" + "\n");
                System.out.println("Disconessione avvenuta");
                break;
            }else if (str.equals("RIMUOVI")) {
                System.out.println("Scrivere il numero della nota da eliminare");
                String indice = scanner.nextLine();
                out.writeBytes("#" + "\n");
                out.writeBytes(indice + "\n");
                System.out.println(in.readLine());
            } else {
                out.writeBytes(str + "\n");
                System.out.println(in.readLine());
            }
        } while (str != "ESCI");
        in.close();
        out.close();
        scanner.close();
        mySocket.close();
    }
}