package jrpg_ca2;

/**
 * Admin Number: 2424093
 * Class: DIT/FT/2A/01
 * @author Jayden
 */

import java.util.ArrayList;

public class Book {
    private String bookTitle; 
    private String author; 
    private String ISBN; 
    private double price; 
    private String category;
    private boolean availableForLoan; 
    private ArrayList<Student> reservationList = new ArrayList<Student>();

    public Book(
        String bookTitle, 
        String author, 
        String ISBN, 
        double price, 
        String category, 
        boolean availableForLoan
    ) { 
        this.bookTitle = bookTitle; 
        this.author = author;
        this.ISBN = ISBN; 
        this.price = price; 
        this.category = category;
        this.availableForLoan = availableForLoan;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getAvailableForLoan() {
        return availableForLoan;
    }
    public void setAvailableForLoan(boolean availableForLoan) {
        this.availableForLoan = availableForLoan;
    }

    public ArrayList<Student> getReservationList() {
        return this.reservationList;
    }

    public void addToReservationList(Student student) {
        this.reservationList.add(student);
    }

    public void removeFromReservationList(Student student) {
        this.reservationList.remove(student);
    }

}
