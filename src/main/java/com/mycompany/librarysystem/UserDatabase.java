package com.mycompany.librarysystem;

public interface UserDatabase {
    public User getUser(int id);
    public boolean deleteUser(int id);
    public boolean addUser(User user);
    public boolean changeUserInfo(User user);
}
