package com.company;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Reviews implements Writable{


    private String name;
    private int stars;
    private String review;
    private static List<Reviews> reviews = new ArrayList<>();
    private File file = new File("C:\\Users\\simon\\Desktop\\File\\reviews.txt");
    private OutputStream fileOutputStream;
    private InputStream fileInputStream;


    public Reviews(String name, int stars, String review){
        this.name = name;
        this.stars = stars;
        this.review = review;
        reviews.add(this);
    }

    public Reviews(){

    }
    public Reviews(InputStream inputStream) throws IOException {
        read(inputStream);
    }

    public void writeAllReviewsToFile() throws IOException {
        fileOutputStream = new FileOutputStream(file);
        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(reviews.size());
        fileOutputStream.write(buffer);
        for (Reviews reviews:reviews) {
            reviews.writeToFile((FileOutputStream) fileOutputStream);
        }
    }

    public void readAllReviewsFromFile() throws IOException {
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


    public void writeToFile(FileOutputStream fileOutputStream) throws IOException {

        fileOutputStream.write(name.getBytes().length);
        fileOutputStream.write(name.getBytes());

        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(stars);
        fileOutputStream.write(buffer);

        fileOutputStream.write(review.getBytes().length);
        fileOutputStream.write(review.getBytes());

    }

    public void readFromFile() throws IOException {

        byte[] buffer = new byte[4];
        int actuallyRead;
        String exception = "Expected 4 bytes but received ";


        //read name
        int messageLength = fileInputStream.read();
        if(messageLength == -1){
            return;
        }
        byte[] msgBytes = new byte[messageLength];
        actuallyRead = fileInputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String reviewName = new String(msgBytes);
        setName(reviewName);


        //read rating
        actuallyRead = fileInputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int stars = ByteBuffer.wrap(buffer).getInt();
        setStars(stars);


        //read review
        messageLength = fileInputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = fileInputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String review = new String(msgBytes);
        setReview(review);

        reviews.add(this);
    }





    public void writeAllReviews(OutputStream outputStream) throws IOException {
        readAllReviewsFromFile();
        outputStream.write(reviews.size());
            for (Reviews reviewsList:reviews) {
                reviewsList.write(outputStream);
            }
    }

    //write reviews to client
    public void write(OutputStream outputStream)throws IOException {
        outputStream.write(name.getBytes().length);
        outputStream.write(name.getBytes());

        byte[] buffer = new byte[4];
        ByteBuffer.wrap(buffer).putInt(stars);
        outputStream.write(buffer);

        outputStream.write(review.getBytes().length);
        outputStream.write(review.getBytes());
    }

    //read from client review
    public void read(InputStream inputStream) throws IOException {

        byte[] buffer = new byte[4];
        int actuallyRead;
        String exception = "Expected 4 bytes but received ";


    //read name
        int messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        byte[] msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String reviewName = new String(msgBytes);
        setName(reviewName);


    //read rating
        actuallyRead = inputStream.read(buffer);
        if(actuallyRead != 4){
            throw new RuntimeException(exception + actuallyRead);
        }
        int stars = ByteBuffer.wrap(buffer).getInt();
        setStars(stars);


    //read review
        messageLength = inputStream.read();
        if(messageLength == -1){
            return;
        }
        msgBytes = new byte[messageLength];
        actuallyRead = inputStream.read(msgBytes);
        if(actuallyRead != messageLength){
            return;
        }
        String review = new String(msgBytes);
        setReview(review);

        reviews.add(this);
        showReviews();
    }


    public void showReviews(){
        for (int i = 0; i < reviews.size(); i++) {
            System.out.println(reviews.get(i).toString());
        }
    }


    public String toString(){
        return "------------------- " + name +
                "\nRated: " + rating(stars) +
                "\n" + review +
                "\n---------------------------";
    }

    public String rating(int stars){
        String rating = "";
        for (int i = 0; i < stars; i++) {
            rating += " * ";
        }
        return rating;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
