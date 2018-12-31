package com.company;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PropertyMarket implements Writable{

//    private static final int BY_PRICE = 301;
//    private static final int BY_BEDROOMS = 302;
//    private static final int BY_SQUARE_METERS = 303 ;
    private String name;
    private String address;
    private String typeOfHouse;
    private int amountOfBedrooms;
    private int squareMeters;
    private int priceOfProperty;
    private boolean readFromFile = false;
    public static List<PropertyMarket> properties = new ArrayList<>();
    private File file = new File("C:\\Users\\simon\\Desktop\\File\\properties.txt");
    private OutputStream fileOutputStream;
    private InputStream fileInputStream;



    public PropertyMarket(String name, String address, String typeOfHouse, int amountOfBedrooms, int squareMeters, int priceOfProperty) throws IOException {
        this.name = name;
        this.address = address;
        this.typeOfHouse = typeOfHouse;
        this.amountOfBedrooms = amountOfBedrooms;
        this.squareMeters = squareMeters;
        this.priceOfProperty = priceOfProperty;
        properties.add(this);
    }

    public PropertyMarket(){

    }

    public PropertyMarket(InputStream inputStream) throws IOException {
        read(inputStream);
    }



    //writes all properties to client
    public void writeAllProperties(OutputStream  outputStream) throws IOException {
        readAllPropertiesFromFile();
        outputStream.write(properties.size());
        for (PropertyMarket propertyMarket:properties) {
            propertyMarket.write(outputStream);
        }
    }


    //write properties to client
    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(address.getBytes().length);
        outputStream.write(address.getBytes());

        outputStream.write(typeOfHouse.getBytes().length);
        outputStream.write(typeOfHouse.getBytes());

        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(amountOfBedrooms);
        outputStream.write(buffer);

        ByteBuffer.wrap(buffer).putInt(squareMeters);
        outputStream.write(buffer);

        ByteBuffer.wrap(buffer).putInt(priceOfProperty);
        outputStream.write(buffer);

        outputStream.write(name.getBytes().length);
        outputStream.write(name.getBytes());
    }


    //read property from client
    @Override
    public void read(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[4];
        int actuallyRead;
        String exception = "Expected 4 bytes but received ";


    //read type of property
        int messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        byte[] msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String propertyType = new String(msgBytes);
        setTypeOfHouse(propertyType);


    //read address
        messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String address = new String(msgBytes);
        setAddress(address);


    //read amount of bedrooms
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int bedrooms = ByteBuffer.wrap(buffer).getInt();
        setAmountOfBedrooms(bedrooms);


    //read square meters
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int sqmt = ByteBuffer.wrap(buffer).getInt();
        setSquareMeters(sqmt);


    //read price
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int price = ByteBuffer.wrap(buffer).getInt();
        setPriceOfProperty(price);


    //read name
        messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String name = new String(msgBytes);
        setName(name);

        properties.add(this);
        showList();
    }


    public void showList(){
        for(int i = 0; i <properties.size();i++){
            System.out.println(properties.get(i));
        }
    }



    @Override
    public String toString(){
        return "Type of property:                  " + typeOfHouse +
                "\nAddress:                           " + address +
                "\nAmount of bedrooms:                " + amountOfBedrooms + " br" +
                "\nSize of property in square meters: " + squareMeters + "m" +
                "\nListing price:                    $" + priceOfProperty +
                "\nListed by:                         " + name +
                "\n----------------------------------------------\n";
    }


    public void writeToFile(FileOutputStream fileOutputStream) throws IOException {
        byte[] buffer = new byte[4];

        fileOutputStream.write(typeOfHouse.getBytes().length);
        fileOutputStream.write(typeOfHouse.getBytes());

        fileOutputStream.write(address.getBytes().length);
        fileOutputStream.write(address.getBytes());


        ByteBuffer.wrap(buffer).putInt(amountOfBedrooms);
        fileOutputStream.write(buffer);

        ByteBuffer.wrap(buffer).putInt(squareMeters);
        fileOutputStream.write(buffer);

        ByteBuffer.wrap(buffer).putInt(priceOfProperty);
        fileOutputStream.write(buffer);

        fileOutputStream.write(name.getBytes().length);
        fileOutputStream.write(name.getBytes());

    }


    public void readFromFile() throws IOException {
        byte[] buffer = new byte[4];
        int actuallyRead;
        String exception = "Expected 4 bytes but received ";


        //read type of property
        int messageLength = fileInputStream.read();
        if(messageLength == -1){
            return;
        }
        byte[] msgBytes = new byte[messageLength];
        actuallyRead = fileInputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String propertyType = new String(msgBytes);
        setTypeOfHouse(propertyType);


        //read address
        messageLength = fileInputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = fileInputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String address = new String(msgBytes);
        setAddress(address);


        //read amount of bedrooms
        actuallyRead = fileInputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int bedrooms = ByteBuffer.wrap(buffer).getInt();
        setAmountOfBedrooms(bedrooms);


        //read square meters
        actuallyRead = fileInputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int sqmt = ByteBuffer.wrap(buffer).getInt();
        setSquareMeters(sqmt);


        //read price
        actuallyRead = fileInputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int price = ByteBuffer.wrap(buffer).getInt();
        setPriceOfProperty(price);


        //read name
        messageLength = fileInputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = fileInputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String name = new String(msgBytes);
        setName(name);

        properties.add(this);

    }

    public void writeAllPropertiesToFile() throws IOException {
        fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(properties.size());
        fileOutputStream.write(buffer);
        for (PropertyMarket propertyMarket:properties) {
            propertyMarket.writeToFile((FileOutputStream) fileOutputStream);
        }
    }

    public void readAllPropertiesFromFile() throws IOException {
        fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[4];
        int actuallyRead = fileInputStream.read(bytes);
        if(actuallyRead != 4){
            throw new RuntimeException("Expected 4 bytes");
        }
        int amount = ByteBuffer.wrap(bytes).getInt();

        for (int i = 0; i < amount; i++) {
                readFromFile();
        }

    }

//    public void readFirstTimeFromFile() throws IOException {
//        if(readFromFile == false){
//            readAllPropertiesFromFile();
//            readFromFile = true;
//        }
//    }


//    public int compare(Object o1, Object o2, int action) {
//        switch (action){
//            case BY_PRICE:
//                int price1 = ((PropertyMarket) o1).getPriceOfProperty();
//                int price2 = ((PropertyMarket) o2).getPriceOfProperty();
//
//                if(price1 >= price2){
//                    return  price1;
//                }
//                if (price2 >= price1){
//                    return price2;
//                }
//                break;
//            case BY_BEDROOMS:
//                int bedrooms1 = ((PropertyMarket) o1).getAmountOfBedrooms();
//                int bedrooms2 = ((PropertyMarket) o2).getAmountOfBedrooms();
//
//                if(bedrooms1 >= bedrooms2){
//                    return  bedrooms1;
//                }
//                if (bedrooms2 >= bedrooms1){
//                    return bedrooms2;
//                }
//                break;
//            case BY_SQUARE_METERS:
//                int sqmt1 = ((PropertyMarket) o1).getSquareMeters();
//                int sqmt2 = ((PropertyMarket) o2).getSquareMeters();
//
//                if(sqmt1 >= sqmt2){
//                    return  sqmt1;
//                }
//                if (sqmt2 >= sqmt1){
//                    return sqmt2;
//                }
//                break;
//        }
//
//        return -1;
//    }
//
//    public void insertionSort(int action){
//           for (int i = 0; i < properties.size(); i++) {
//               PropertyMarket value = properties.get(i);
//               int j = i;
//               while (j > 0 && compare(properties.get(j - 1), value, action) > 0) {
//                   properties.set(j, properties.get(j - 1));
//                   j = j - 1;
//               }
//               properties.set(j, value);
//           }
//           System.out.println(properties);
//    }






    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeOfHouse() {
        return typeOfHouse;
    }

    public void setTypeOfHouse(String typeOfHouse) {
        this.typeOfHouse = typeOfHouse;
    }

    public int getAmountOfBedrooms() {
        return amountOfBedrooms;
    }

    public void setAmountOfBedrooms(int amountOfBedrooms) {
        this.amountOfBedrooms = amountOfBedrooms;
    }

    public int getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(int squareMeters) {
        this.squareMeters = squareMeters;
    }

    public int getPriceOfProperty() {
        return priceOfProperty;
    }

    public void setPriceOfProperty(int priceOfProperty) {
        this.priceOfProperty = priceOfProperty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}