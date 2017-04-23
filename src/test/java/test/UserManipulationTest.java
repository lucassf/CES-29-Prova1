package test;

import com.mycompany.librarysystem.BookDatabase;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import com.mycompany.librarysystem.User;
import com.mycompany.librarysystem.Librarian;
import com.mycompany.librarysystem.UserDatabase;
import com.mycompany.librarysystem.UserStatus;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserManipulationTest {
    
    private static User user1;
    private static User user2;
    private static Librarian librarian;
    private static UserDatabase database;
    private static BookDatabase bookdatabase;
    
    @BeforeClass
    public static void setUp(){
        user1 = new User("Kaos");
        user2 = new User("James");
        
        database = mock(UserDatabase.class);
        librarian = new Librarian(database,bookdatabase);
        
        when(database.addUser(user1)).thenReturn(true);
        when(database.addUser(user2)).thenReturn(true);
        when(database.deleteUser(user1.getId())).thenReturn(true);
        when(database.deleteUser(user2.getId())).thenReturn(true);
        when(database.changeUserInfo(user1)).thenReturn(true);
        when(database.changeUserInfo(user2)).thenReturn(true);
    }
    
    @Test
    public void addValidUser(){
        assertTrue(librarian.addUser(user1));
        assertTrue(librarian.addUser(user2));
    }
    
    @Test
    public void deleteUser(){
        assertTrue(librarian.deleteUser(user1));
        assertTrue(librarian.deleteUser(user2));
    }
    
    @Test
    public void unblockUserTest(){
        assertEquals(UserStatus.UNBLOCKED,user1.getStatus());
    }
    
    @Test
    public void TemporarilyblockUser1(){
        assertTrue(librarian.blockUserDefinedTime(user1,12));
        assertEquals(UserStatus.TIMEBLOCKED,user1.getStatus());
        assertEquals(12,user1.getBlockTime());
    }
    
    @Test
    public void FinancialblockUser2(){
        assertTrue(librarian.financialBlockUser(user2));
        assertEquals(UserStatus.FINANCIALBLOCKED,user2.getStatus());
    }
    
    @Test
    public void blockThenUnblockUser(){
        librarian.financialBlockUser(user1);
        assertEquals(UserStatus.FINANCIALBLOCKED,user1.getStatus());
        librarian.unblockUser(user1);
        assertEquals(UserStatus.UNBLOCKED,user1.getStatus());
    }
}
