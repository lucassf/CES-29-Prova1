package test;

import com.mycompany.librarysystem.*;
import com.mycompany.useraccess.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserSystemAccessTest {
    private static BookDatabase database;
    private static UserDatabase userdatabase;
    private static Librarian librarian;
    private static User user1;
    private static Book book1;
    private static Book book2;
    private static Book book3;
    private static UserAccess useraccess;
    
    @BeforeClass
    public static void setUp(){
        database = mock(BookDatabase.class);
        userdatabase = mock(UserDatabase.class);
    }
    
    @Before
    public void methodSetUp(){
        book1 = new Book("A lenda","Stieven Dival");
        
        user1 = new User("Karl");
        useraccess = new UserAccess(user1);
        
        ArrayList<Book> bookList = new ArrayList<>(Arrays.asList(book1,book2,book3));
        when(database.getBookList()).thenReturn(bookList);
        librarian = new Librarian(userdatabase, database);
    }
    
    @Test
    public void showBorrowedBooksBeforeDeadline(){
        librarian.lendBook(user1, book1);
        assertEquals(book1.getListId()+": A lenda; Stieven Dival. Status: 7 day(s)"
                + " remaining\n",useraccess.showBooks());
    }
    
    @Test
    public void showBorrowedBooksAfterDeadline(){
        librarian.lendBook(user1, book1);
        book1.getReturnDate().add(Calendar.DAY_OF_YEAR, -8);
        assertEquals(book1.getListId()+": A lenda; Stieven Dival. Status: Expired\n"
                ,useraccess.showBooks());
    }
    
    @Test
    public void unblockedUserSystemStatus(){
        assertEquals("UNBLOCKED",useraccess.getStatus());
    }
    
    @Test
    public void timeBlockedUserSystemStatus(){
        librarian.blockUserDefinedTime(user1, 3);
        assertEquals("TIMEBLOCKED",useraccess.getStatus());
    }
    
    @Test
    public void financialBlockedUserSystemStatus(){
        librarian.financialBlockUser(user1);
        assertEquals("FINANCIALBLOCKED",useraccess.getStatus());
    }
}
