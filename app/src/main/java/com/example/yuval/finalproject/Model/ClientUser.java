package com.example.yuval.finalproject.Model;

/**
 * Created by Yuval on 10/06/2017.
 */

public class ClientUser {
    private String userId;



    private String lName;
    private String fName;
    private String email;
    private String password;

    public ClientUser(){
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}


}
