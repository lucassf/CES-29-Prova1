package com.mycompany.librarysystem;

public class Librarian {
    
   private UserDatabase database;
   
   public Librarian(UserDatabase database) {
       this.database = database;
   }
   
    public boolean addUser(User user){
        return database.addUser(user);
    }
    
    public boolean deleteUser(User user){
        return database.deleteUser(user.getId());
    }
    
    public boolean blockUserDefinedTime(User user,int time){
        user.timeBlockUser(time);
        return database.changeUserInfo(user);
    }
    
    public boolean financialBlockUser(User user){
        user.financialBlockUser();
        return database.changeUserInfo(user);
    }
    
    public boolean unblockUser(User user){
        user.unblockUser();
        return database.changeUserInfo(user);
    }
}
