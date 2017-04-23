package com.mycompany.librarysystem;

import java.util.ArrayList;

public interface BookDatabase {
    public ArrayList<Book> getBookList();
    public boolean deleteBook(int id);
    public boolean addBook(Book book);
}
