package com.example.rec;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable{
    @NonNull
    @PrimaryKey()
    private String Username;

    @NonNull @ColumnInfo(name = "Password")
    private String Password;

    @NonNull @ColumnInfo(name = "Name")
    private String Name;

    @NonNull @ColumnInfo(name = "DOB")
    private String DOB;

    @NonNull @ColumnInfo(name = "Address")
    private String Address;

    @NonNull @ColumnInfo(name = "Contact")
    private String Contact;

    public String getUsername(){
        return Username;
    }

    public String getPassword(){
        return Password;
    }

    public String getName(){
        return Name;
    }

    public String getDOB(){
        return DOB;
    }

    public String getAddress(){
        return Address;
    }

    public String getContact(){
        return Contact;
    }

    public void setUsername(String U){
        Username = U;
    }

    public void setPassword(String P){
        Password = P;
    }

    public void setName(String N){
        Name = N;
    }

    public void setDOB(String D){
        DOB = D;
    }

    public void setAddress(String A){
        Address = A;
    }

    public void setContact(String C){
        Contact = C;
    }
}
