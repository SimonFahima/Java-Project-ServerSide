package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private PropertyMarket propertyMarket;
    private Reviews reviewsList;

    private static final int ADD_PROPERTY = 121;
    private static final int ADD_REVIEW = 122;
    private static final int GET_PROPERTY = 123;
    private static final int GET_REVIEW = 124;
    private static final int CLOSE_SERVER = 148;
    private static boolean GO = true;


    public ClientThread(Socket socket) {
        this.socket = socket;
        propertyMarket = new PropertyMarket();
        reviewsList = new Reviews();
    }

    @Override
    public void run() {
        try{
            while(GO){
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            int action = inputStream.read();
                switch (action) {
                    case ADD_PROPERTY:
                        new PropertyMarket(inputStream);
                        break;
                    case ADD_REVIEW:
                        new Reviews(inputStream);
                        break;
                    case GET_PROPERTY:
                        propertyMarket.writeAllProperties(outputStream);
                        break;
                    case GET_REVIEW:
                        reviewsList.writeAllReviews(outputStream);
                        break;
                    case CLOSE_SERVER:
                        propertyMarket.writeAllPropertiesToFile();
                        reviewsList.writeAllReviewsToFile();
                        GO = false;
                        break;
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
