package jrpg_ca2;

/**
 * Admin Number: 2429634
 * Class: DIT/FT/2A/01
 * @author Junkai
 */

import java.util.ArrayList;

public class Student {
    private String adminNumber;
    private String name;
    private ArrayList<Book> books = new ArrayList<Book>();

    public Student(String adminNumber, String name) {
        this.adminNumber = adminNumber;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { 
        this.name = name;
    }

    public String getAdminNumber() { 
        return this.adminNumber;
    }

    public void setAdminNumber(String adminNumber) { 
        this.adminNumber = adminNumber;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return this.books;
    }
    
    public Book getBorrowedBookByISBN(String ISBN) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                return books.get(i);
            }
        }
        
        return null;
    }

    public void addBorrowedBook(Book book) {
        this.books.add(book);
    }

    public void returnBorrowedBook(Book book) {
        this.books.remove(book);
    }

    public int countBorrowedBooks() {
        return this.books.size();
    }
}
