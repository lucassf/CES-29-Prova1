package com.mycompany.librarysystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Librarian {
    
   private final UserDatabase userDatabase;
   private final BookDatabase database;
   private final ArrayList<Book> availableBooks;
   private final ArrayList<Book> borrowedBooks;
   
   public Librarian(UserDatabase database,BookDatabase bookdatabase) {
       this.userDatabase = database;
       this.database = bookdatabase;
       if (bookdatabase!=null)availableBooks = bookdatabase.getBookList();
       else availableBooks = new ArrayList<>();
       borrowedBooks = new ArrayList<>();
   }
   
    public boolean addUser(User user){
        return userDatabase.addUser(user);
    }

    public boolean deleteUser(User user){
        return userDatabase.deleteUser(user.getId());
    }
    
    public boolean addBook(Book book){
        availableBooks.add(book);
        return database.addBook(book);
    }
        
    public boolean deleteBook(Book book){
        if (availableBooks.contains(book)){
            availableBooks.remove(book);
        }
        return database.deleteBook(book.getListId());
    }
    
    public boolean blockUserDefinedTime(User user,int time){
        user.timeBlockUser(time);
        return userDatabase.changeUserInfo(user);
    }
    
    public boolean financialBlockUser(User user){
        user.financialBlockUser();
        sendInfoToFinancialSector();
        return userDatabase.changeUserInfo(user);
    }
    
    public boolean unblockUser(User user){
        user.unblockUser();
        return userDatabase.changeUserInfo(user);
    }
    
    public boolean lendBook(User user,Book book){
        boolean possible = user.getStatus().equals(UserStatus.UNBLOCKED)&&
                book.getStatus().equals(BookStatus.AVAILABLE);
        if (possible){
            setReturnDate(book);
            book.setStatus(BookStatus.BORROWED);
            borrowedBooks.add(book);
            availableBooks.remove(book);
            user.borrowBook(book);
            userDatabase.changeUserInfo(user);
        }
        return possible;
    }
    
    public boolean returnBook(User user,Book book){
        boolean possible = user.hasBorrowedBook(book);
        if (possible){
            book.setStatus(BookStatus.AVAILABLE);
            int time = verifyReturnDate(book);
            if (time>0){
                blockUserDefinedTime(user, time);
            }
            availableBooks.add(book);
            borrowedBooks.remove(book);
            
            userDatabase.changeUserInfo(user);
        }
        return possible;
    }
    
    public boolean lostBook(User user,Book book){
        boolean possible = user.hasBorrowedBook(book);
        if (possible){
            financialBlockUser(user);
            book.setStatus(BookStatus.LOST);
            userDatabase.changeUserInfo(user);
            borrowedBooks.remove(book);
            deleteBook(book);
        }
        return possible;
    }
    
    public String showAvailableBookList(){
        String ret="";
        availableBooks.sort(new BookComparator());
        for(Book book:availableBooks){
            ret+=book.toString()+"\n";
        }
        
        return ret;
    }
    
    public String showBorrowedBookList(){
        String ret="";
        borrowedBooks.sort(new BookComparator());
        for(Book book:borrowedBooks){
            ret+=book.toString()+"\n";
        }
        return ret;
    }
    
    private int verifyReturnDate(Book book){
        Calendar calendar = Calendar.getInstance();
        int time = calendar.get(Calendar.DAY_OF_YEAR)-
                book.getReturnDate().get(Calendar.DAY_OF_YEAR);
        if (time<=0)return 0;
        return time;
    }
    
    private void setReturnDate(Book book){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,7);//7 days to return the book
        book.setReturnDate(calendar);
    }
    
    private void sendInfoToFinancialSector(){
        //Sends the info of a pending payment to the financial Sector
    }
}
