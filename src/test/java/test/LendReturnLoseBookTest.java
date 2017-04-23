package test;

import com.mycompany.librarysystem.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LendReturnLoseBookTest {
    
    private static BookDatabase database;
    private static UserDatabase userdatabase;
    private static Librarian librarian;
    private static User user1;
    private static User user2;
    private static Book book1;
    private static Book book2;
    private static Book book3;
    
    @BeforeClass
    public static void setUp(){
        database = mock(BookDatabase.class);
        userdatabase = mock(UserDatabase.class);
        
        book1 = new Book("A lenda","Stieven Dival");
        book2 = new Book("Ata","Bolsonitro");
        book3 = new Book("Theory of swamps","Sereni");
        
        when(database.deleteBook(book1.getListId())).thenReturn(true);
        when(database.deleteBook(book2.getListId())).thenReturn(true);
        when(database.deleteBook(book3.getListId())).thenReturn(true);
    }
    
    @Before
    public void methodSetUp(){
        book1.setStatus(BookStatus.AVAILABLE);
        book2.setStatus(BookStatus.AVAILABLE);
        book3.setStatus(BookStatus.AVAILABLE);
        
        user1 = new User("Karl");
        
        ArrayList<Book> bookList = new ArrayList<>(Arrays.asList(book1,book2,book3));
        when(database.getBookList()).thenReturn(bookList);
        librarian = new Librarian(userdatabase, database);
    }
    
    @Test
    public void lendAvailableBookToUnblockedUser(){
        assertTrue(librarian.lendBook(user1, book1));
        String message1 = book2.getListId()+": Ata; Bolsonitro\n"+
                book3.getListId()+": Theory of swamps; Sereni\n";
        String message2 = book1.getListId()+": A lenda; Stieven Dival\n";
        
        assertEquals(message1, librarian.showAvailableBookList());
        assertEquals(message2, librarian.showBorrowedBookList());
    }
    
    @Test
    public void lendAvailableBookToBlockedUser(){
        librarian.blockUserDefinedTime(user1, 5);
        assertFalse(librarian.lendBook(user1, book1));
        
        librarian.financialBlockUser(user1);
        assertFalse(librarian.lendBook(user1, book1));
        
        assertEquals("", librarian.showBorrowedBookList());
    }
    
    @Test
    public void returnBorrowedBookWithNoPenalti(){
        librarian.lendBook(user1, book1);
        
        assertTrue(librarian.returnBook(user1, book1));
        assertEquals(UserStatus.UNBLOCKED, user1.getStatus());
        
        String message = book1.getListId()+": A lenda; Stieven Dival\n"+
                book2.getListId()+": Ata; Bolsonitro\n"+
                book3.getListId()+": Theory of swamps; Sereni\n";
        
        assertEquals(message, librarian.showAvailableBookList());
    }
    
    @Test
    public void returnBorrowedBookWithPenalti(){
        librarian.lendBook(user1, book1);
        book1.getReturnDate().add(Calendar.DAY_OF_YEAR, -8);
        
        assertTrue(librarian.returnBook(user1, book1));
        assertEquals(UserStatus.TIMEBLOCKED, user1.getStatus());
        assertEquals(1,user1.getBlockTime());
    }
    
    @Test
    public void userLosesBook(){
        librarian.lendBook(user1, book1);
        
        assertTrue(librarian.lostBook(user1, book1));
        assertEquals(UserStatus.FINANCIALBLOCKED, user1.getStatus());
    }
}
