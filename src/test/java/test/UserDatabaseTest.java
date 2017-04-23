package test;

import com.mycompany.librarysystem.User;
import com.mycompany.librarysystem.UserDatabase;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDatabaseTest {
    
    private static UserDatabase database;
    private static User user;
    
    @BeforeClass
    public static void setUp(){
        database = mock(UserDatabase.class);
        user = new User("Roney");
        
        when(database.deleteUser(0)).thenReturn(false);
        when(database.deleteUser(1)).thenReturn(true);
        when(database.getUser(0)).thenThrow(new NoSuchElementException());
        when(database.getUser(1)).thenReturn(user);
        when(database.addUser(user)).thenReturn(true);
        when(database.changeUserInfo(user)).thenReturn(true);
    }
    
    @Test
    public void deleteNotResgisteredUser(){
        assertFalse(database.deleteUser(0));
    }
    
    @Test
    public void deleteResgisteredUser(){
        assertTrue(database.deleteUser(1));
    }
    
    @Test(expected = NoSuchElementException.class)
    public void getNotRegisteredUser() throws Exception{
        database.getUser(0);
    }
    
    @Test
    public void getRegisteredUser(){
        assertEquals("Roney", database.getUser(1).getName());
    }
    
    @Test
    public void addValidUser(){
        assertTrue(database.addUser(user));
    }
    
    @Test
    public void changeValidUserInfo(){
        assertTrue(database.changeUserInfo(user));
    }
}
