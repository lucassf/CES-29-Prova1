package test;

import com.mycompany.librarysystem.Book;
import com.mycompany.librarysystem.BookDatabase;
import com.mycompany.librarysystem.Librarian;
import com.mycompany.librarysystem.UserDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookManipulationTest {
    
    private static BookDatabase database;
    private static UserDatabase userdatabase;
    private static Librarian librarian;
    private static Book book1;
    private static Book book2;
    private static Book book3;
    
    @BeforeClass
    public static void setUp(){
        database = mock(BookDatabase.class);
        book1 = new Book("A lenda","Stieven Dival");
        book2 = new Book("Ata","Bolsonitro");
        book3 = new Book("Theory of swamps","Sereni");
        
        when(database.addBook(book3)).thenReturn(true);
        when(database.deleteBook(book1.getListId())).thenReturn(true);
        when(database.deleteBook(book2.getListId())).thenReturn(true);
        when(database.deleteBook(book3.getListId())).thenReturn(false);
    }
    
    @Before
    public void methodSetUp(){
        ArrayList<Book> bookList = new ArrayList<>(Arrays.asList(book1,book2));
        when(database.getBookList()).thenReturn(bookList);
        librarian = new Librarian(userdatabase, database);
    }
    
    @Test
    public void showAvailableBook(){
        String message = book1.getListId()+": A lenda; Stieven Dival\n"+
                book2.getListId()+": Ata; Bolsonitro\n";
        assertEquals(message,librarian.showAvailableBookList());
    }
    
    @Test
    public void addNewBookThenShowBooks(){
        assertTrue(librarian.addBook(book3));
        String message = book1.getListId()+": A lenda; Stieven Dival\n"+
                book2.getListId()+": Ata; Bolsonitro\n"+
                book3.getListId()+": Theory of swamps; Sereni\n";
        assertEquals(message,librarian.showAvailableBookList());
    }
    
    @Test
    public void removeExistingBookThenShowBooks(){
        assertTrue(librarian.deleteBook(book1));
        String message = book2.getListId()+": Ata; Bolsonitro\n";
        assertEquals(message,librarian.showAvailableBookList());
    }
    
    @Test
    public void removeNonExistingBookThenShowBooks(){
        assertFalse(librarian.deleteBook(book3));
        String message = book1.getListId()+": A lenda; Stieven Dival\n"+
                book2.getListId()+": Ata; Bolsonitro\n";
        assertEquals(message,librarian.showAvailableBookList());
    }
    
    @Test
    public void removeThenAddExistingBookThenShowBooks(){
        assertTrue(librarian.deleteBook(book1));
        assertTrue(librarian.addBook(book3));
        String message = book2.getListId()+": Ata; Bolsonitro\n"+
                book3.getListId()+": Theory of swamps; Sereni\n";
        assertEquals(message,librarian.showAvailableBookList());
    }
}
