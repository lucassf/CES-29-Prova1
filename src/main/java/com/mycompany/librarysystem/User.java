package com.mycompany.librarysystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

    private final ArrayList<Book> borrowedbooks;
    private static int maxid = 0;
    private String name;
    private final int id;
    private UserStatus status;
    private int blockTime;
    
    public User(String name){
        this.name = name;
        id = maxid;
        maxid++;
        status = UserStatus.UNBLOCKED;
        blockTime = 0;
        borrowedbooks = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }
    
    void timeBlockUser(int time){
        blockTime = time;
        status = UserStatus.TIMEBLOCKED;
    }
    
    void financialBlockUser(){
        status = UserStatus.FINANCIALBLOCKED;
    }
    
    void unblockUser(){
        blockTime = 0;
        status = UserStatus.UNBLOCKED;
    }
    
    public int getBlockTime(){
        return blockTime;
    }
    
    boolean hasBorrowedBook(Book book){
        return borrowedbooks.contains(book);
    }
    
    void borrowBook(Book book){
        borrowedbooks.add(book);
    }
    
    Book returnBook(Book book){
        if (hasBorrowedBook(book)){
            borrowedbooks.remove(book);
            return book;
        }
        return null;
    }

    public List<Book> getBorrowedbooks() {
        return Collections.unmodifiableList(borrowedbooks);
    }
}
