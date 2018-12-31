package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {


    private static final int PORT = 7241;
    public static boolean GO = true;

    public static void main(String[] args) throws IOException {



//        addExistingMarket();
//        addExistingReviews();


        ServerSocket serverSocket = null;
        int i = 0;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("waiting for incoming communication...");
            while (GO) {
                Socket socket = serverSocket.accept();
                if(i == 0) {
                    System.out.println("Connected!");
                    i++;
                }
                new ClientThread(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("Disconnected...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void addExistingMarket() throws IOException {
        PropertyMarket propertyMarket = new PropertyMarket("David","1587 Mill Rd.",
                "Single Family",5,64,500000);
        PropertyMarket propertyMarket1 = new PropertyMarket("Michael","2142 Ocean Parkway",
                "Duplex",8,76,620000);
        PropertyMarket propertyMarket2 = new PropertyMarket("Simon","1234 Main St.",
                "Single Family",4,82,1200000);
        PropertyMarket propertyMarket3 = new PropertyMarket("Chris","548 E 7th St.",
                "Triplex",6,120,1070000);
    }

    public static void addExistingReviews(){
        Reviews reviews = new Reviews("Emily",4,
                "This project is fast and great for looking for houses.");
        Reviews reviews1 = new Reviews("Michael",5,
                "Looking to sell my house which i posted here but i already bought one" +
                        "\nbecause the properties are amazing!");
        Reviews reviews2 = new Reviews("Susane",3,
                "Did'nt find what i was looking for but it seems like a nice\n" +
                        "place to find a property.");
        Reviews reviews3 = new Reviews("David",5,
                "This is a really great project! very easy yo use!");
    }


}
