package com.mycompany.useraccess;

import com.mycompany.librarysystem.Book;
import com.mycompany.librarysystem.User;
import java.util.Calendar;
import java.util.List;

public class UserAccess {
    private User user;
    
    public UserAccess(User user){
        this.user = user;
    }
    
    public String showBooks(){
        String ret = "";
        List<Book> borrowedBooks = user.getBorrowedbooks();
        Calendar time = Calendar.getInstance();
        for (Book book:borrowedBooks){
            String message;
            int remdays = book.getReturnDate().get(Calendar.DAY_OF_YEAR)-
                    time.get(Calendar.DAY_OF_YEAR);
            if (remdays<0)message = "Expired";
            else message = remdays+" day(s) remaining";
            ret+=book.toString()+". Status: "+message+"\n";
        }
        return ret;
    }
    
    public String getStatus(){
        return user.getStatus().toString();
    }
}
