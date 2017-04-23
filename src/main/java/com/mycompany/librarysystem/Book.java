package com.mycompany.librarysystem;

import java.util.Calendar;
import java.util.Comparator;

class BookComparator implements Comparator<Book>{

    @Override
    public int compare(Book o1, Book o2) {
        return o1.getListId()-o2.getListId();
    }
    
}

public class Book {
    
    private String title;
    private String author;
    private BookStatus status;
    private Calendar returnDate;
    private final int listId;
    private static int maxid = 0;
    
    public Book(String title,String author){
        listId = maxid;
        maxid++;
        this.title = title;
        this.author = author;
        status = BookStatus.AVAILABLE;
    }
    
    @Override
    public String toString(){
        return listId+": "+title+"; "+author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public BookStatus getStatus(){
        return status;
    }
    
    public void setStatus(BookStatus status){
        this.status = status;
    }

    public int getListId() {
        return listId;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }
    
    
    
}
