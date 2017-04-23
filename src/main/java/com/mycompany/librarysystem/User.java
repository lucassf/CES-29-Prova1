package com.mycompany.librarysystem;

public class User {

    
    private static int maxid = 0;
    private String name;
    private final int id;
    private UserStatus status;
    private int blockTime;
    
    public User(String name){
        this.name = name;
        id = maxid;
        maxid++;
        status = UserStatus.UNBLOCKED;
        blockTime = 0;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }
    
    public void timeBlockUser(int time){
        blockTime = time;
        status = UserStatus.TIMEBLOCKED;
    }
    
    public void financialBlockUser(){
        status = UserStatus.FINANCIALBLOCKED;
    }
    
    public void unblockUser(){
        blockTime = 0;
        status = UserStatus.UNBLOCKED;
    }
    
    public int getBlockTime(){
        return blockTime;
    }
}
